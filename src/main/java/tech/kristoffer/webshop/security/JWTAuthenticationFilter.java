package tech.kristoffer.webshop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tech.kristoffer.webshop.models.ShopUserDetails;
import tech.kristoffer.webshop.models.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager ) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/shop/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("TJOHEJ");
        try{
            User user = new ObjectMapper()
                    .readValue(request.getInputStream(), User.class);

            ShopUserDetails creds = new ShopUserDetails(user);

            System.out.println("user: " + creds.getUsername());
            System.out.println("password: " + creds.getPassword());


            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    creds.getUsername(),
                    creds.getPassword(),
                    new ArrayList<>()
            ));
        }catch(IOException e){
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDetails user = (UserDetails) authResult.getPrincipal();
        String username = user.getUsername();
//        user.getAuthorities().forEach(auth -> System.out.println(auth.getAuthority()));

        Date expiresAt = new Date(System.currentTimeMillis() + 300_000);
        Algorithm secret = Algorithm.HMAC256("apa näbbdjur oneplus stor fisk".getBytes());

        //splittar ROLE_ADMIN till {ROLE, ADMIN}
//        String[] role = user.getAuthorities().iterator().next().getAuthority().split("_");

        String jwt = JWT.create()
                .withSubject(username)
//                .withClaim(role[0], role[1])
                .withExpiresAt(expiresAt)
                .sign(secret);

        response.getWriter().write(jwt);
        response.getWriter().flush();
    }
}
