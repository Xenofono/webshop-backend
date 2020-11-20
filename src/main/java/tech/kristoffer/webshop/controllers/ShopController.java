package tech.kristoffer.webshop.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tech.kristoffer.webshop.constants.SecurityConstants;
import tech.kristoffer.webshop.models.Authority;
import tech.kristoffer.webshop.models.CreateUserRequest;
import tech.kristoffer.webshop.models.Product;
import tech.kristoffer.webshop.models.User;
import tech.kristoffer.webshop.models.requests.AddToCartRequest;
import tech.kristoffer.webshop.repositories.AuthorityRepository;
import tech.kristoffer.webshop.repositories.ProductRepository;
import tech.kristoffer.webshop.repositories.UserRepository;
import tech.kristoffer.webshop.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("shop")
public class ShopController {

    private ProductRepository productRepository;
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    public ShopController(ProductRepository productRepository, PasswordEncoder passwordEncoder, UserService userService) {
        this.productRepository = productRepository;
        this.passwordEncoder = passwordEncoder;
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

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody AddToCartRequest addToCartRequest, HttpServletRequest request){
        userService.addToCart(addToCartRequest, request);
        return ResponseEntity.ok("Produkt tillagd");

    }


}
