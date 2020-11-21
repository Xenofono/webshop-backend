package tech.kristoffer.webshop.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import tech.kristoffer.webshop.constants.SecurityConstants;
import tech.kristoffer.webshop.models.Authority;
import tech.kristoffer.webshop.repositories.AuthorityRepository;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private AuthorityRepository authorityRepository;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, AuthorityRepository authorityRepository) {
        super(authenticationManager);
        this.authorityRepository = authorityRepository;
    }




    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        System.out.println("TEST");
        System.out.println(request.getHeader("authorization"));
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {
            DecodedJWT decodedJwt =  JWT.require(Algorithm.HMAC256("apa näbbdjur oneplus stor fisk".getBytes()))
                    .build()
                    .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""));

            String user = decodedJwt.getSubject();

            Authority authority = authorityRepository.findByUsername(user);


            if (user != null ) {
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority.getAuthority()));
                return new UsernamePasswordAuthenticationToken(user, null, authorities);
            }

            return null;

        }

        return null;
    }
}
