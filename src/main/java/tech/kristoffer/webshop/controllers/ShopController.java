package tech.kristoffer.webshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.kristoffer.webshop.models.requests.CreateUserRequest;
import tech.kristoffer.webshop.models.Product;
import tech.kristoffer.webshop.repositories.ProductRepository;
import tech.kristoffer.webshop.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("shop")
public class ShopController {

    private ProductRepository productRepository;
    private UserService userService;

    public ShopController(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @GetMapping
    public Iterable<Product> getAllProducts(){
        return productRepository.findAll();
    }


    @PostMapping("signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest newUser){
        System.out.println(newUser.getUsername());
        userService.signupUser(newUser);
        return ResponseEntity.ok().build();
    }




}
