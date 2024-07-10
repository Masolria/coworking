package com.masolria.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masolria.dto.AuthenticationEntry;
import com.masolria.dto.UserDto;
import com.masolria.exception.EntityNotFoundException;
import com.masolria.service.AuthService;
import com.masolria.util.UserStoreUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    AuthService authService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        authService = (AuthService) sc.getAttribute("authService");
        objectMapper = (ObjectMapper) sc.getAttribute("objectMapper");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (InputStream inputStream = req.getInputStream()) {
            AuthenticationEntry auth = objectMapper.readValue(inputStream, AuthenticationEntry.class);
            UserDto userDto = authService.authorize(auth);
            req.getSession().setAttribute("user", userDto);
            UserStoreUtil.setUserAuthorized(userDto);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("You are authorized successfully.");
            resp.getWriter().write(objectMapper.writeValueAsString(userDto));
        } catch (EntityNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("user with given email doesn't exist");
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("password is incorrect");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}