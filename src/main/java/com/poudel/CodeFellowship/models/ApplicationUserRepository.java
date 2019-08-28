package com.poudel.CodeFellowship.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    public ApplicationUser findByUsername(String username);
    public ApplicationUser findByUsernameAndPassword(String username, String password);
}
