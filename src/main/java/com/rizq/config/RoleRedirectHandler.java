package com.rizq.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

/** Sends each user to the dashboard that matches their role. */
@Component
public class RoleRedirectHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String target = "/donor/dashboard";
        if (roles.contains("ROLE_ADMIN")) {
            target = "/admin/dashboard";
        } else if (roles.contains("ROLE_NGO")) {
            target = "/ngo/dashboard";
        }
        response.sendRedirect(request.getContextPath() + target);
    }
}
