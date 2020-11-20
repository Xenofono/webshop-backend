package tech.kristoffer.webshop.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.kristoffer.webshop.constants.SecurityConstants;
import tech.kristoffer.webshop.exceptions.UserExistsException;
import tech.kristoffer.webshop.models.*;
import tech.kristoffer.webshop.models.requests.AddToCartRequest;
import tech.kristoffer.webshop.repositories.AuthorityRepository;
import tech.kristoffer.webshop.repositories.ProductRepository;
import tech.kristoffer.webshop.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;
    private ProductRepository productRepository;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findUserByUsernameContaining(String username) {
        return userRepository.findUserByUsernameContaining(username);
    }

    public List<User> findAllAdmins() {
        return userRepository.findUserByAuthorityAuthority("ROLE_ADMIN");
    }

    public void saveNewAdmin(FormModel formModel) {
        User user = new User();
        user.setUsername(formModel.getFirstInput());
        user.setPassword(passwordEncoder.encode(formModel.getSecondInput()));

        Authority authority = new Authority();
        authority.setUsername(user.getUsername());
        authority.setAuthority("ROLE_ADMIN");
        user.setAuthority(authority);

        userRepository.save(user);
    }

    public User signupUser(CreateUserRequest newUser) {

        if(userRepository.findUserByUsername(newUser.getUsername()) != null){
            throw new UserExistsException(newUser.getUsername());
        }

        Authority authority = new Authority();
        authority.setUsername(newUser.getUsername());
        authority.setAuthority("ROLE_USER");

        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setAuthority(authority);
        userRepository.save(user);
        return user;
    }

    public void addToCart(AddToCartRequest addToCartRequest, HttpServletRequest request) {
        User user = getUserFromRequest(request)
                .orElseThrow(() -> new RuntimeException("Kunde inte hitta användare"));
        Product productToAdd = productRepository.findById(addToCartRequest.getId())
                .orElseThrow(() -> new RuntimeException("Kunde inte hitta produkt"));

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(productToAdd);
        newCartItem.setQuantity(addToCartRequest.getQuantity());
        double sum = productToAdd.getPrice() * addToCartRequest.getQuantity();
        newCartItem.setSum(sum);

        user.addCartItem(newCartItem);
        userRepository.save(user);



    }

    private Optional<User> getUserFromRequest(HttpServletRequest request){
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        System.out.println("USER FRÅN REQUEST I SHOPCONTROLLER");
        System.out.println(request.getHeader("authorization"));
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return Optional.empty();
        }
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        DecodedJWT decodedJwt =  JWT.require(Algorithm.HMAC256("apa näbbdjur oneplus stor fisk".getBytes()))
                .build()
                .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""));

        String subject = decodedJwt.getSubject();
        return Optional.ofNullable(userRepository.findUserByUsername(subject));

    }

//    public Set<ShopOrder> findUserByUsernameContaining(String name){
//        List<User> users =  userRepository.findUserByUsernameContaining(name);
//        return !users.isEmpty() ? user.getOrders() : new HashSet<>();
//    }
}
