package tech.kristoffer.webshop.controllers;

import org.springframework.web.bind.annotation.*;
import tech.kristoffer.webshop.models.Product;
import tech.kristoffer.webshop.models.User;
import tech.kristoffer.webshop.repositories.ProductRepository;

@RestController
@RequestMapping("shop")
public class ShopController {

    private ProductRepository productRepository;

    public ShopController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }



    @GetMapping
    public Iterable<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @GetMapping("/test")
    public String test (){
        return "Nu funkar den rackaren";
    }

    @PostMapping("signup")
    public User createUser(@RequestBody User user){
        System.out.println("tjohej");

        User newUser = new User();
        newUser.setUsername("kalle");
        return newUser;
    }
}
