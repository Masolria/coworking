package com.masolria.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.masolria.dto.AuthenticationEntry;
import com.masolria.dto.UserDto;
import com.masolria.service.AuthService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServletTest {
    @Mock
    AuthService authService;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ServletConfig servletConfig;

    @Mock
    private ServletContext servletContext;

    LoginServlet loginServlet;

    @BeforeEach
     void setUp() throws ServletException {
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttribute("authService")).thenReturn(authService);
        when(servletContext.getAttribute("objectMapper")).thenReturn(objectMapper);
        loginServlet = new LoginServlet();
        loginServlet.init(servletConfig);
    }
    @Test
    @DisplayName("The test verifies the correct login behavior with a response of 200.")
    void doPost() throws IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        UserDto userDto = new UserDto(3L,"test@example.com");
        AuthenticationEntry entry = new AuthenticationEntry("test@example.com","password123");
        String json ="{\"email\": \"test@example.com\", \"password\": \"password123\"}";
        HttpSession session = mock(HttpSession.class);
        ServletInputStream streamMock = mock(ServletInputStream.class);
        when(authService.authorize(entry)).thenReturn(userDto);
        when(req.getSession()).thenReturn(session);
        doNothing().when(session).setAttribute("user",userDto);
        when(req.getInputStream()).thenReturn(streamMock);
        when(objectMapper.readValue(streamMock, AuthenticationEntry.class)).thenReturn(entry);
        when(objectMapper.writeValueAsString(userDto)).thenReturn(json);
        PrintWriter writer = mock(PrintWriter.class);
        when(resp.getWriter()).thenReturn(writer);
        loginServlet.doPost(req,resp);
        verify(session).setAttribute("user",userDto);
        verify(resp).setStatus(HttpServletResponse.SC_OK);
        verify(resp.getWriter()).write("You are authorized successfully.");
        verify(resp.getWriter()).write(json);
    }
}