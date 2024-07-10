package com.masolria.servlet;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masolria.dto.SpaceDto;
import com.masolria.exception.EntityDeletionException;
import com.masolria.exception.EntityNotFoundException;
import com.masolria.exception.ValidationException;
import com.masolria.service.SpaceService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet("/space")
public class SpaceServlet extends HttpServlet {
    private SpaceService spaceService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = getServletContext();
        spaceService = (SpaceService) sc.getAttribute("spaceService");
        objectMapper = (ObjectMapper) sc.getAttribute("objectMapper");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (InputStream inputStream = req.getInputStream()) {
            ObjectMapper mapper = new ObjectMapper();

            SpaceDto spaceDto = mapper.readValue(inputStream, SpaceDto.class);
            spaceService.save(spaceDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Space entry successfully saved.");
        } catch (ValidationException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Input is invalid");
        }catch (RuntimeException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try(InputStream inputStream = req.getInputStream()){
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            Long id = jsonNode.get("id").asLong();
            spaceService.delete(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Space entry successfully deleted");
        }catch (EntityDeletionException e){
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Cannot delete space entry with given id.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try(InputStream inputStream = req.getInputStream()){
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            Long id = jsonNode.get("id").asLong();
            SpaceDto spaceDto = spaceService.findById(id);
            String json = objectMapper.writeValueAsString(spaceDto);
            resp.getWriter().write(json);
            resp.setStatus(HttpServletResponse.SC_OK);
        }catch (EntityNotFoundException e){
            resp.getWriter().write("Entity with given id doesn't exist.");
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}