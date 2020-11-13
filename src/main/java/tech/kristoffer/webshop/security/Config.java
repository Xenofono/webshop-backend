package tech.kristoffer.webshop.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@EnableWebSecurity
public class Config {

    private PasswordEncoder passwordEncoder;
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    public Config(PasswordEncoder passwordEncoder, AuthenticationSuccessHandler authenticationSuccessHandler) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("kristoffer").password(passwordEncoder.encode("pass")).authorities("ROLE_ADMIN")
                .and().withUser("alina").password(passwordEncoder.encode("pass"))
                .authorities("ROLE_USER");
    }


    @Configuration
    @Order(1)
    public static class ShopConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.antMatcher("/shop/**")
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/shop/signup").permitAll()
                    .antMatchers("/shop/**").hasAnyAuthority("ROLE_USER")
                    .anyRequest().authenticated()
                    .and()
                    .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                    .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().csrf().disable();

//            http.cors().and().authorizeRequests()
//                    .antMatchers(HttpMethod.POST, "/shop/signup").permitAll()
//                    .antMatchers("/shop/**").hasAnyAuthority("ROLE_USER")
//                    .anyRequest().authenticated()
//                    .and()
//                    .addFilter(new JWTAuthenticationFilter(authenticationManager()))
//                    .addFilter(new JWTAuthorizationFilter(authenticationManager()))
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                    .and().csrf().disable();


        }
    }

    @Configuration
    @Order(2)
    public static class AdminConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        AuthenticationSuccessHandler authenticationSuccessHandler;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable().authorizeRequests(authorize ->
                    authorize.antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                            .antMatchers("/login").permitAll())
                    .formLogin()
                    .permitAll()
                    .successHandler(authenticationSuccessHandler)
                    .and()
                    .logout()
                    .deleteCookies("auth_code", "JSESSIONID")
                    .invalidateHttpSession(true);


        }
    }

//    @Bean
//    public CorsConfigurationSource corsConfiguration(){
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return source;
//    }
}
