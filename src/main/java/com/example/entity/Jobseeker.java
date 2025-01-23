package com.example.entity;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jobseekers")
public class Jobseeker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String email;

    @OneToMany(mappedBy = "jobseeker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications;

    @OneToOne(mappedBy = "jobseeker", cascade = CascadeType.ALL, orphanRemoval = true)
    private Resume resume;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }
}
