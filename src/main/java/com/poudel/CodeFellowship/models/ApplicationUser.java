package com.poudel.CodeFellowship.models;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String username;
    String password;
    String firstName;
    String lastName;
    Date dateOfBirth;
    String bio;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "appUser")
    List<Post> posts;

    @ManyToMany
    @JoinTable(
            name="user_follows",
            joinColumns = {@JoinColumn(name = "primaryUser")},
            inverseJoinColumns = { @JoinColumn(name = "followedUser")}
    )
    Set<ApplicationUser> userThatIFollow;

    @ManyToMany (mappedBy = "userThatIFollow")
    Set<ApplicationUser> userThatFollowMe;

    public ApplicationUser(String username, String password, String firstName,
                           String lastName, Date dateOfBirth, String bio) {
            this.username = username;
            this.password = password;
            this.firstName = firstName;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
            this.bio = bio;
    }

    public ApplicationUser(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void addFollows(ApplicationUser followedUser ) {
        userThatIFollow.add(followedUser);
    }

    public Set<ApplicationUser> getFollowedUser() {
        return this.userThatIFollow;
    }
}
