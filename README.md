##Lab 16: Spring Auth

This application allows user to 
* see all the users in one page in url /allUsers
* follow other users 
* visit a url /feed to view all of the posts from the users that they follow.

#### Resources
* https://www.logicbig.com/tutorials/spring-framework/spring-boot/custom-thymeleaf-error-page.html
#### Class and Methods 
```
Config:
UserDetailsServiceImpl
WebSecurityConfig

Controllers:
ApplicationUserController
Home Controller
PostController

Models:
ApplicationUser
ApplicationUserRepository
Post
PostRepository

Templates:
Fragments
    fragments.html
login.html
myprofile.html
oneUser.html
root.html
signup.html
oneUser.html
posts.html
feed.html
```

