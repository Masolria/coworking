package com.masolria.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masolria.dto.BookingDto;
import com.masolria.entity.SpaceType;
import com.masolria.service.BookingService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet("/booking-by-user")
public class BookingByUserServlet extends HttpServlet {

    BookingService bookingService;
    ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        bookingService = (BookingService) getServletContext().getAttribute("bookingService");
        objectMapper = (ObjectMapper) getServletContext().getAttribute("objectMapper");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (InputStream inputStream = req.getInputStream()) {
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            Long id = jsonNode.get("id").asLong();
            List<BookingDto> list = bookingService.getByUserId(id);
            String jsonResp = objectMapper.writeValueAsString(list);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(jsonResp);
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
