package tech.kristoffer.webshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.kristoffer.webshop.models.Authority;
import tech.kristoffer.webshop.models.Product;
import tech.kristoffer.webshop.models.User;
import tech.kristoffer.webshop.repositories.AuthorityRepository;
import tech.kristoffer.webshop.repositories.ProductRepository;
import tech.kristoffer.webshop.repositories.UserRepository;

@RestController
@RequestMapping("shop")
public class ShopController {

    private ProductRepository productRepository;
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public ShopController(ProductRepository productRepository, UserRepository userRepository, AuthorityRepository authorityRepository, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
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
    public ResponseEntity<?> createUser(@RequestBody User user){
        System.out.println(user.getUsername());

        if(user.getUsername() == null || user.getPassword() == null || userRepository.findUserByUsername(user.getUsername()) != null){
            return ResponseEntity.unprocessableEntity().build();
        }
        user.setAuthority("ROLE_USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Authority authority = new Authority();
        authority.setUsername(user.getUsername());
        authority.setAuthority(user.getAuthority());

        userRepository.save(user);
        authorityRepository.save(authority);
        return ResponseEntity.ok(user);

    }
}
