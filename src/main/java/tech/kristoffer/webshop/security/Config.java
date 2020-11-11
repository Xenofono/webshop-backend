package tech.kristoffer.webshop.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {

    private PasswordEncoder passwordEncoder;

    public Config(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("kristoffer")
                .password(passwordEncoder.encode("pass")).roles("ADMIN");

        auth.inMemoryAuthentication().withUser("alina")
                .password(passwordEncoder.encode("pass")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .defaultSuccessUrl("/admin", true)
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .and()
                .csrf().disable();


        http.authorizeRequests(authorize ->
                authorize.mvcMatchers("/admin/**").hasRole("ADMIN")
                        .mvcMatchers("/shop/**").hasRole("USER")
                        .anyRequest().denyAll());
    }
}
