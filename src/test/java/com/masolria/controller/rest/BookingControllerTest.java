package com.masolria.controller.rest;

import com.masolria.dto.BookingDto;
import com.masolria.dto.IdRequest;
import com.masolria.entity.SpaceType;
import com.masolria.service.BookingService;
import com.masolria.util.DateTimeParseUtil;
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

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {
    @Mock
    BookingService bookingService;
    @InjectMocks
    BookingController bookingController;
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();

    }

    @Test
    @DisplayName("")
    void bySpaceType() throws Exception {
        List<BookingDto> list = Collections.emptyList();
        when(bookingService.getByType(SpaceType.WORKING_SPACE)).thenReturn(list);
        String spaceType = "WORKING_SPACE";
        mockMvc.perform(post("/booking/by-space-type")
                .content(spaceType)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(bookingService, times(1)).getByType(SpaceType.WORKING_SPACE);
    }

    @Test
    void byUserId() throws Exception {
        IdRequest req = new IdRequest(3L);
        List<BookingDto> list = Collections.emptyList();
        String jsonReq = mapper.writeValueAsString(req);
        System.out.println(jsonReq);
        when(bookingService.getByUserId(req.getId())).thenReturn(list);
        mockMvc.perform(post("/booking/by-user-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonReq)).andExpect(status().isOk());
        verify(bookingService, times(1)).getByUserId(req.getId());
    }

    @Test
    void byDate() throws Exception {
        String date = "2024 03 12";
        mockMvc.perform(post("/booking/by-date")
                .content(date)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(bookingService, times(1))
                .getByDate(DateTimeParseUtil.parseToLDate(date));
    }

    @Test
    @DisplayName("Test verifies ")
    void freeSlots() throws Exception {
        List<BookingDto> freeList = Collections.emptyList();
        when(bookingService.getFreeSlots()).thenReturn(freeList);
        mockMvc.perform(get("/booking/free-slots")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(bookingService, times(1)).getFreeSlots();
    }

    @Test
    void reserve() throws Exception {
        IdRequest req = new IdRequest(3L);
        doNothing().when(bookingService).reserve(req.getId());
        String jsonReq = mapper.writeValueAsString(req);
        mockMvc.perform(patch("/booking/reserve")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonReq)).andExpect(status().isOk());
        verify(bookingService, times(1)).reserve(req.getId());
    }

    @Test
    void release() throws Exception {
        Long id = 4L;
        doNothing().when(bookingService).release(id);
        String jsonReq = mapper.writeValueAsString(id);
        mockMvc.perform(patch("/booking/release")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonReq))
                .andExpect(status().isOk());
        verify(bookingService, times(1)).release(id);
    }

    @Test

    void getBooking() throws Exception {
        BookingDto dto = new BookingDto(1L, false, LocalDateTime.now(),
                LocalDateTime.now(), 3L, 4L);
        when(bookingService.findById(1L)).thenReturn(dto);
        IdRequest req = new IdRequest(1L);
        String json = mapper.writeValueAsString(req);
        mockMvc.perform(get("/booking/by-id")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
                //TODO
        verify(bookingService, times(1)).findById(1L);
    }

    @Test
    void deleteBooking() throws Exception {
        Long id = 3L;
        doNothing().when(bookingService).delete(id);
        IdRequest req = new IdRequest(3L);
        String json = mapper.writeValueAsString(req);
        mockMvc.perform(delete("/booking/delete")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
        verify(bookingService, times(1)).delete(id);
    }

    @Test
    void update() throws Exception {
        BookingDto dto = new BookingDto(1L, false, LocalDateTime.now(),
                LocalDateTime.now(), 3L, 4L);
        String jsonReq ="{  \n" +
                        "    \"id\":100049,\n" +
                        "    \"isBooked\":\"false\",\n" +
                        "    \"timeStart\":\"2024-07-15 18:12:28.764188\",\n" +
                        "    \"timeEnd\":\"2024-07-15 18:12:28.764188\",\n" +
                        "    \"spaceId\":\"100002\",\n" +
                        "    \"forUserId\":100000\n" +
                        "}";
        when(bookingService.update(any())).thenReturn(dto);
        mockMvc.perform(put("/booking/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReq))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking updated successfully"));
        verify(bookingService,times(1)).update(any());
    }
}