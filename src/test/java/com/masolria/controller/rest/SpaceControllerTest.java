package com.masolria.controller.rest;

import com.masolria.dto.IdRequest;
import com.masolria.dto.SpaceDto;
import com.masolria.entity.SpaceType;
import com.masolria.service.SpaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class SpaceControllerTest {
    @Mock
    SpaceService spaceService;
    @InjectMocks
    SpaceController spaceController;
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(spaceController).build();

    }

    @Test
    @DisplayName("The test verifies when the existing identifier is received, the status is OK.")
    void byId() throws Exception {
        IdRequest req = new IdRequest(4L);
        String json = mapper.writeValueAsString(req);
        SpaceDto spaceDto = new SpaceDto(3L,
                "location", SpaceType.CONFERENCE_HALL.name());
        when(spaceService.findById(req.getId())).thenReturn(spaceDto);
        mockMvc.perform(get("/space/by-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isOk());
        verify(spaceService, times(1)).findById(req.getId());
    }

    @Test
    @DisplayName("The test verifies correct deletion and status is OK.")
    void deleteSpace() throws Exception {
        IdRequest req = new IdRequest(4L);
        String json = mapper.writeValueAsString(req);
        doNothing().when(spaceService).delete(req.getId());
        mockMvc.perform(delete("/space/delete")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(spaceService, times(1)).delete(req.getId());
    }

    @Test
    @DisplayName("The test verifies when correct dto received response status is OK.")
    void update() throws Exception {
        SpaceDto spaceDto = new SpaceDto(3L,
                "location", SpaceType.CONFERENCE_HALL.name());
        when(spaceService.update(spaceDto)).thenReturn(spaceDto);
        String json = mapper.writeValueAsString(spaceDto);
        mockMvc.perform(post("/space/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isOk());
        verify(spaceService, times(1)).update(spaceDto);
    }

    @Test
    @DisplayName("The test checks when the status is Ok and the service returns a list of dto.")
    void getAll() throws Exception {
        List<SpaceDto> dtoList = Collections.emptyList();
        when(spaceService.getAll()).thenReturn(dtoList);
        mockMvc.perform(get("/space/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(spaceService, times(1)).getAll();
    }

    @Test
    @DisplayName("The test verifies correct saving by put req and status is OK.")
    void save() throws Exception {
        SpaceDto spaceDto = new SpaceDto(3L,
                "location", SpaceType.CONFERENCE_HALL.name());
        String json = mapper.writeValueAsString(spaceDto);
        when(spaceService.save(spaceDto)).thenReturn(spaceDto);
        mockMvc.perform(put("/space/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andExpect(status().isOk());
        verify(spaceService, times(1)).save(spaceDto);
    }
}