package com.masolria.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masolria.exception.EntityNotFoundException;
import com.masolria.exception.OccupiedConflictException;
import com.masolria.service.BookingService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/booking-release")
public class BookingReleaseServlet extends HttpServlet {
    BookingService bookingService;
    ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = getServletContext();
        bookingService = (BookingService) sc.getAttribute("bookingService");
        objectMapper = (ObjectMapper) sc.getAttribute("objectMapper");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (InputStream inputStream = req.getInputStream()) {
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            Long id = jsonNode.get("id").asLong();
            bookingService.release(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Booking updated successfully");
        } catch (OccupiedConflictException e) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.getWriter().write("Booking slot already released");
        } catch (EntityNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Entry with given id not found");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
