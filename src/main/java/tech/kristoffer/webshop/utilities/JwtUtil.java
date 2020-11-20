package tech.kristoffer.webshop.utilities;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tech.kristoffer.webshop.constants.SecurityConstants;
import tech.kristoffer.webshop.models.User;
import tech.kristoffer.webshop.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class JwtUtil {

    private UserRepository userRepository;

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserFromRequest(HttpServletRequest request){
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        System.out.println("USER FRÅN REQUEST I SHOPCONTROLLER");
        System.out.println(request.getHeader("authorization"));
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            throw new BadCredentialsException("Saknar korrekt JWT token");
        }
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        DecodedJWT decodedJwt =  JWT.require(Algorithm.HMAC256("apa näbbdjur oneplus stor fisk".getBytes()))
                .build()
                .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""));

        String subject = decodedJwt.getSubject();
        return Optional.ofNullable(userRepository.findUserByUsername(subject)).orElseThrow(() -> new UsernameNotFoundException(subject));

    }
}
