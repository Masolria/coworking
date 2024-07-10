package com.masolria.servlet.Filter;

import com.masolria.dto.UserDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;
@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    private static final Set<String> PUBLIC_PATH = Set.of("/login", "/register");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uri = ((HttpServletRequest) request).getRequestURI();
        System.out.println(uri);
        if (isPublicPath(uri) || isUserLoggedIn(request)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Access denied.");
        }

    }
    private boolean isUserLoggedIn(ServletRequest request) {
        UserDto userDto = (UserDto) ((HttpServletRequest) request).getSession().getAttribute("user");
        return userDto != null;
    }
    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(uri::startsWith);
    }
}
