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
import java.util.List;

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


    @PostMapping("/login")
    public RedirectView enterPassword(String username, String password) {
        //If username and password match with what it is in database, redirect to my profile
        ApplicationUser thisUser = applicationUserRepository.findByUsernameAndPassword(username,
                passwordEncoder.encode(password));
        if(thisUser == null) {
            return new RedirectView("/signup");
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(thisUser, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/myprofile");
    }

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "signup";
    }

    @GetMapping("/users/{id}")
    public String getOneApplicationUser(@PathVariable long id, Principal p, Model m) {
        m.addAttribute("oneUser", applicationUserRepository.findById(id).get());
        m.addAttribute("user", p);
        return "oneUser";
    }

    @GetMapping("/myprofile")
    public String getToMyProfile(Principal p, Model m) {
        m.addAttribute("thisuser", applicationUserRepository.findByUsername(p.getName()));
        m.addAttribute("user", p);
        return "myprofile";
    }

    //Display all the users including yourself
    @GetMapping("/allUsers")
    public String getAllUsers(Principal p, Model model) {
        List<ApplicationUser> allUsers = applicationUserRepository.findAll();
        model.addAttribute("allUsers", allUsers );
        model.addAttribute("user", p);
        return "allUsers";
    }

    @GetMapping("users/follows")
    public String addFollows(Model model) {
        model.addAttribute("users", applicationUserRepository.findAll());
        return "addFollows";
    }


    @PostMapping("/users/follow/{followPath}")
    public RedirectView addFollowedUsers(@PathVariable long followPath,  Principal p) {
        ApplicationUser followingUser = applicationUserRepository.findByUsername(p.getName());
        followingUser.addFollows(applicationUserRepository.findById(followPath).get());
        applicationUserRepository.save(followingUser);
        return new RedirectView("/feed");
    }

    @GetMapping("/feed")
    public  String getFeed(Principal p, Model model) {
        ApplicationUser principleUser = applicationUserRepository.findByUsername(p.getName());
        System.out.println("TEST TEST TEST " + principleUser.getFollowedUser());
        model.addAttribute("userThatIFollow", principleUser.getFollowedUser());
        model.addAttribute("user", p);
        return "feed";
    }

}
