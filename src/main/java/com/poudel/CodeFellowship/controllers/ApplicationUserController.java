package com.poudel.CodeFellowship.controllers;

import com.poudel.CodeFellowship.models.ApplicationUser;
import com.poudel.CodeFellowship.models.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;

@Controller
public class ApplicationUserController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/users")
    public RedirectView createUser(String username, String password, String firstName, String lastName,
                                   Date dateOfBirth, String bio) {
        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password),
                firstName, lastName, dateOfBirth, bio);
        applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new RedirectView("/myprofile");
    }

    @GetMapping ("/login")
    public String getLoginPage() {
        return "login";

    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

    @GetMapping("/users/{id}")
    public String getOneApplicationUser(@PathVariable long id, Model m) {
        ApplicationUser a  = applicationUserRepository.findById(id).get();
        m.addAttribute("user", a);
        return "oneUser";
    }

    @GetMapping("/myprofile")
    public String getToMyProfile(Principal p, Model m) {
        m.addAttribute("user", p);
        return "myprofile";
    }

}
