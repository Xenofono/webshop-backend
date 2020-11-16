package tech.kristoffer.webshop.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.kristoffer.webshop.models.Authority;
import tech.kristoffer.webshop.models.FormModel;
import tech.kristoffer.webshop.models.ShopOrder;
import tech.kristoffer.webshop.models.User;
import tech.kristoffer.webshop.repositories.AuthorityRepository;
import tech.kristoffer.webshop.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findUserByUsernameContaining(String username) {
        return userRepository.findUserByUsernameContaining(username);
    }

    public List<User> findAllAdmins() {
        return userRepository.findUserByAuthority("ROLE_ADMIN");
    }

    public void saveNewAdmin(FormModel formModel) {
        User user = new User();
        user.setUsername(formModel.getFirstInput());
        user.setPassword(passwordEncoder.encode(formModel.getSecondInput()));
        userRepository.save(user);
        Authority authority = new Authority();
        authority.setAuthority("ROLE_ADMIN");
        authority.setUsername(user.getUsername());
        authorityRepository.save(authority);
    }

//    public Set<ShopOrder> findUserByUsernameContaining(String name){
//        List<User> users =  userRepository.findUserByUsernameContaining(name);
//        return !users.isEmpty() ? user.getOrders() : new HashSet<>();
//    }
}
