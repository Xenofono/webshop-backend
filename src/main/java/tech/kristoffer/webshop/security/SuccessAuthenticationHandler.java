package tech.kristoffer.webshop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class SuccessAuthenticationHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        HttpSession session = httpServletRequest.getSession();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(user != null){
            session.setAttribute("username", user.getUsername());
            if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
                httpServletResponse.sendRedirect("/admin");
            }
            else{
//                Cookie cookie = new Cookie("JSESSIONID", "");
//                httpServletResponse.addCookie(cookie);
                httpServletResponse.sendRedirect("/login?error");
            }
        }
        else{
            httpServletResponse.sendRedirect("/login?error");
        }
    }


}
