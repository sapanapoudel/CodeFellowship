package com.poudel.CodeFellowship.controllers;

import com.poudel.CodeFellowship.models.ApplicationUser;
import com.poudel.CodeFellowship.models.ApplicationUserRepository;
import com.poudel.CodeFellowship.models.Post;
import com.poudel.CodeFellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import java.security.Principal;


@Controller
public class PostController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/posts")
    public String getAllPosts(){
        return "posts";
    }

    @PostMapping("/posts")
    public RedirectView createPosts(String body, Principal p) {
        ApplicationUser loggeduser = applicationUserRepository.findByUsername(p.getName());
        Post post = new Post(body, loggeduser);
        postRepository.save(post);
        return new RedirectView("/myprofile");
    }

}
