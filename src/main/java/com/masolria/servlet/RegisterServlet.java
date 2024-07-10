package com.masolria.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masolria.dto.AuthenticationEntry;
import com.masolria.dto.UserDto;
import com.masolria.exception.AlreadyRegisteredException;
import com.masolria.service.AuthService;
import com.masolria.util.UserStoreUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    AuthService authService;
    ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
        authService = (AuthService) getServletContext().getAttribute("authService");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (InputStream inputStream = req.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();

            AuthenticationEntry auth = mapper.readValue(inputStream, AuthenticationEntry.class);
            UserDto userDto = authService.register(auth);
            UserStoreUtil.setUserAuthorized(userDto);
            req.getSession().setAttribute("user", userDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("You are registered successfully.");
        }catch (AlreadyRegisteredException e){
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            resp.getWriter().write(e.getMessage());
        }catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}