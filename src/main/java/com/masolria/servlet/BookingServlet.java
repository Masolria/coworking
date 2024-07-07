package com.masolria.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masolria.dto.BookingDto;
import com.masolria.exception.EntityNotFoundException;
import com.masolria.exception.ValidationException;
import com.masolria.service.BookingService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (InputStream inputStream = req.getInputStream()) {
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            Long id = jsonNode.get("id").asLong();
            bookingService.delete(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Booking deleted successfully");
        } catch (EntityNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Entry with given id not found");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (InputStream inputStream = req.getInputStream()) {
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            Long id = jsonNode.get("id").asLong();
            bookingService.findById(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Booking deleted successfully");
        } catch (EntityNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Entry with given id not found");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
    //TODO exception handler
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (InputStream inputStream = req.getInputStream()) {
            BookingDto dto = objectMapper.readValue(inputStream, BookingDto.class);
            bookingService.save(dto);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Booking saved successfully");
        } catch (ValidationException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Input data is invalid");
        } catch (EntityNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Entry with given id not found");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        try (InputStream inputStream = req.getInputStream()) {
            BookingDto dto = objectMapper.readValue(inputStream, BookingDto.class);
            bookingService.update(dto);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Booking updated successfully");
        }catch(ValidationException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Input data is invalid");
        } catch (EntityNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("Entry with given id not found");
        } catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
