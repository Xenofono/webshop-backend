package tech.kristoffer.webshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.kristoffer.webshop.models.requests.CreateUserRequest;
import tech.kristoffer.webshop.models.Product;
import tech.kristoffer.webshop.models.requests.FilterProductRequest;
import tech.kristoffer.webshop.repositories.ProductRepository;
import tech.kristoffer.webshop.service.ProductService;
import tech.kristoffer.webshop.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("shop")
public class ShopController {

    private ProductService productService;
    private UserService userService;

    public ShopController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping
    public Iterable<Product> getAllProducts(){
        return productService.findAll();
    }

    @PostMapping("filter")
    public Iterable<Product> getFilteredProducts(@RequestBody FilterProductRequest request){
        return productService.findByNameContaining(request);
    }


    @PostMapping("signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest newUser){
        System.out.println(newUser.getUsername());
        userService.signupUser(newUser);
        return ResponseEntity.ok().build();
    }




}
