package com.example.entity;

import javax.persistence.*;

@Entity
@Table(name = "jobs")
@SecondaryTable(name = "job_details", pkJoinColumns = @PrimaryKeyJoinColumn(name = "job_id"))
public class JobDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(table = "job_details")
    private String description;

    @Column(table = "job_details")
    private Double salary;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}