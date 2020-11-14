package tech.kristoffer.webshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.kristoffer.webshop.models.Authority;
import tech.kristoffer.webshop.models.CreateUserRequest;
import tech.kristoffer.webshop.models.Product;
import tech.kristoffer.webshop.models.User;
import tech.kristoffer.webshop.repositories.AuthorityRepository;
import tech.kristoffer.webshop.repositories.ProductRepository;
import tech.kristoffer.webshop.repositories.UserRepository;

import javax.validation.Valid;

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
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest newUser){
        System.out.println(newUser.getUsername());

        if( userRepository.findUserByUsername(newUser.getUsername()) != null){
            return ResponseEntity.unprocessableEntity().build();
        }
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setPassword(newUser.getPassword());
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
