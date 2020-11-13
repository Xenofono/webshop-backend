package tech.kristoffer.webshop.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.kristoffer.webshop.models.User;

@RestController
public class AuthController {

    @PostMapping("auth/signup")
    public User createUser(@RequestBody User user){
        System.out.println("tjohej");

        User newUser = new User();
        newUser.setUsername("kalle");
        return newUser;
    }
}
