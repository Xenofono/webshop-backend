package tech.kristoffer.webshop.service;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.kristoffer.webshop.exceptions.UserExistsException;
import tech.kristoffer.webshop.models.*;
import tech.kristoffer.webshop.models.requests.CreateUserRequest;
import tech.kristoffer.webshop.models.responses.OrdersResponse;
import tech.kristoffer.webshop.models.responses.UserResponse;
import tech.kristoffer.webshop.repositories.AuthorityRepository;
import tech.kristoffer.webshop.repositories.ProductRepository;
import tech.kristoffer.webshop.repositories.UserRepository;
import tech.kristoffer.webshop.utilities.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;
    private ProductRepository productRepository;
    private JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, ProductRepository productRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
        this.jwtUtil = jwtUtil;
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

    public void signupUser(CreateUserRequest newUser) {

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
    }

    public UserResponse getUser(HttpServletRequest request) {
        User user = jwtUtil.getUserFromRequest(request);

        UserResponse response = new UserResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }


    public OrdersResponse getUserOrders(HttpServletRequest request) {
        User user = jwtUtil.getUserFromRequest(request);

        OrdersResponse response = new OrdersResponse();
        BeanUtils.copyProperties(user, response);
        return response;
    }



//    public Set<ShopOrder> findUserByUsernameContaining(String name){
//        List<User> users =  userRepository.findUserByUsernameContaining(name);
//        return !users.isEmpty() ? user.getOrders() : new HashSet<>();
//    }
}
