package com.poudel.CodeFellowship.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String body;
    Timestamp createdAt;

    @ManyToOne
    ApplicationUser appUser;

    public Post(String body, Timestamp createdAt, ApplicationUser appUser){
        this.body = body;
        this.createdAt = createdAt;
        this.appUser = appUser;
    }

    public Post(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public ApplicationUser getAppUser() {
        return appUser;
    }

    public void setAppUser(ApplicationUser appUser) {
        this.appUser = appUser;
    }

    public String toString () {
        return String.format("%s at %s has posted %s", this.appUser, this.createdAt, this.body);
    }
}
