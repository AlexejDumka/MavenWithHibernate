package com.example.services;
import com.example.entity.Job;
import com.example.entity.Application;
import org.hibernate.*;
import org.hibernate.query.Query;
import javax.persistence.LockModeType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static javax.persistence.LockModeType.PESSIMISTIC_WRITE;

public class JobService {
    private final SessionFactory sessionFactory;

    public JobService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public List<Object[]> getJobAndApplicationDetails() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT j.title, a.status FROM Job j FULL JOIN j.applications a";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setCacheable(true);
            return query.list();
        }
    }

    public List<Object[]> getJobApplications() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT j.title, a.status FROM Job j INNER JOIN j.applications a";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setCacheable(true);
            return query.list();
        }
    }

    public List<Job> getJobsWithApplications() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Job j WHERE EXISTS (SELECT 1 FROM Application a WHERE a.job = j)";
            Query<Job> query = session.createQuery(hql, Job.class);
            query.setCacheable(true);
            return query.list();
        }
    }

    public List<Job> getJobsWithMoreThanXApplications(int count) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Job j WHERE (SELECT COUNT(a) FROM Application a WHERE a.job = j) > :count";
            Query<Job> query = session.createQuery(hql, Job.class);
            query.setParameter("count", count);
            return query.list();
        }
    }

    public List<Job> getAllJobs() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Job";
            Query<Job> query = session.createQuery(hql, Job.class);
            return query.list();
        }
    }

    public List<Object[]> getJobTitlesAndDescriptions() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT j.title, j.description FROM Job j";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            return query.list();
        }
    }

    public List<Application> getApplicationsByStatus(String status) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Application a WHERE a.status = :status";
            Query<Application> query = session.createQuery(hql, Application.class);
            query.setParameter("status", status);
            return query.list();
        }
    }

    public Long countApplicationsForJob(Long jobId) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(a) FROM Application a WHERE a.job.id = :jobId";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("jobId", jobId);
            return query.uniqueResult();
        }
    }

    public Double getAverageApplicationsPerJob() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT AVG(size(j.applications)) FROM Job j";
            Query<Double> query = session.createQuery(hql, Double.class);
            return query.uniqueResult();
        }
    }
    public Long getJobIdUseCriteriaBuilder() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Job> cr = cb.createQuery(Job.class);
            Root<Job> root = cr.from(Job.class);
            cr.select(cb.construct(Job.class, root.get("id"), root.get("title")));
            return session.createQuery(cr).uniqueResult().getId();
        }
    }
    // Optimistic Locking Example
    public void updateEntityWithOptimisticLock(Long id, String newTitle) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Job entity = session.get(Job.class, id);
            entity.setTitle(newTitle);
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(" Transaction failed. Error: " + e.getMessage());
        }
    }

    // Pessimistic Locking Example
    public void updateEntityWithPessimisticLock(Long id, String newDescription) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Job entity = session.find(Job.class, id, LockModeType.PESSIMISTIC_WRITE);
            entity.setDescription(newDescription);

            session.update(entity);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException(" Transaction failed. Error: " + e.getMessage());
        }
    }
}