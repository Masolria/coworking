package com.masolria.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masolria.dto.BookingDto;
import com.masolria.entity.SpaceType;
import com.masolria.exception.EntityDeletionException;
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

@WebServlet("/booking-by-space-type")
public class BookingBySpaceTypeServlet extends HttpServlet {
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
            SpaceType spaceType = SpaceType.valueOf(jsonNode.get("spaceType").asText());
            List<BookingDto> list = bookingService.getByType(spaceType);
            String jsonResp = objectMapper.writeValueAsString(list);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(jsonResp);
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("The space type is spelled incorrectly");
        }catch (RuntimeException e){
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
