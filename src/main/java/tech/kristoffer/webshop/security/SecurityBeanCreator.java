package tech.kristoffer.webshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Service
public class SecurityBeanCreator {

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource){
//        final String getUsers = "SELECT username, password, enabled FROM user WHERE username = ?";
//        final String getAuthorities = "SELECT username, authority FROM authority WHERE username = ?";
//        var userManager = new JdbcUserDetailsManager(dataSource);
//
//        userManager.setUsersByUsernameQuery(getUsers);
//        userManager.setAuthoritiesByUsernameQuery(getAuthorities);
//        return userManager;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }


}
