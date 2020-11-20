package tech.kristoffer.webshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.kristoffer.webshop.models.requests.AddToCartRequest;
import tech.kristoffer.webshop.models.responses.UserResponse;
import tech.kristoffer.webshop.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("shop/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUser(HttpServletRequest request){
        UserResponse response = userService.getUser(request);
        return ResponseEntity.ok(response);
    }
}
