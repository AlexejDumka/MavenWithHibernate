package com.example;
import com.example.entity.Job;
import com.example.entity.Jobseeker;
import com.example.entity.Resume;
import com.example.entity.Skill;
import com.example.entity.Application;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import javax.validation.*;
import java.util.Set;

public class ApplicationRunner {
    public static void main(String[] args) {
        // Initialize Hibernate SessionFactory
        // Use hibernate.cfg.xml for configuration
        // Start a session
        try (SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml") // Use hibernate.cfg.xml for configuration
                .addAnnotatedClass(Job.class)
                .addAnnotatedClass(Jobseeker.class)
                .addAnnotatedClass(Resume.class)
                .addAnnotatedClass(Skill.class)
                .addAnnotatedClass(Application.class)
                .buildSessionFactory(); Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Create example data
            Jobseeker jobseeker = new Jobseeker();
            jobseeker.setName("Jan Cerny");
            jobseeker.setEmail("jan.cerny@example.com");

            Resume resume = new Resume();
            resume.setContent("Full-stack Java Developer. Very passionate about software development.");
            resume.setJobseeker(jobseeker);
            jobseeker.setResume(resume);

            ValidatorFactory factory = Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory();
            Validator validator = factory.getValidator();
            Job job = new Job();
            job.setTitle("Full-stack Java Developer");
            job.setDescription("Develop and maintain enterprise applications.");
            job.setLocation("Prague, Czech Republic");

            Set<ConstraintViolation<Job>> violations = validator.validate(job);
            if (!violations.isEmpty())
                for (ConstraintViolation<Job> violation : violations) {
                    System.out.println(violation.getMessage());
                }
            Skill skill = new Skill();
            skill.setName("Java");
            Application application = new Application();
            application.setJob(job);
            application.setJobseeker(jobseeker);
            application.setStatus("Pending");
            session.persist(jobseeker);
            session.persist(job);
            session.persist(skill);
            session.persist(application);
            session.getTransaction().commit();
            System.out.println("Data saved successfully!");
        } catch (ConstraintViolationException e) {
            Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
            for (ConstraintViolation<?> violation : constraintViolations) {
                System.out.println("Property: " + violation.getPropertyPath() +
                        ", Invalid Value: " + violation.getInvalidValue() +
                        ", Error Message: " + violation.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
