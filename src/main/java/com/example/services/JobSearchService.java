package com.example.services;

import com.example.entity.Job;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class JobSearchService {
    private final Session session;

    public JobSearchService(Session session) {
        this.session = session;
    }

    public List<Job> filterJobsByLocation(String location) {
        String hql = "FROM Job j WHERE j.location = :location";
        Query<Job> query = session.createQuery(hql, Job.class);
        query.setParameter("location", location);
        return query.list();
    }

    public List<Object[]> facetJobsBySkill() {
        String hql = "SELECT s.name, COUNT(j) FROM Job j JOIN j.skills s GROUP BY s.name";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        return query.list();
    }

    public List<Job> spatialSearch(double lat, double lon, double radius) {
        String hql = "FROM Job j WHERE distance(j.latitude, j.longitude, :lat, :lon) <= :radius";
        Query<Job> query = session.createQuery(hql, Job.class);
        query.setParameter("lat", lat);
        query.setParameter("lon", lon);
        query.setParameter("radius", radius);
        return query.list();
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        // Calculate Haversine distance
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
