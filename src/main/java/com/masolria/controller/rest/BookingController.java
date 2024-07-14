package com.masolria.controller.rest;

import com.masolria.dto.BookingDto;
import com.masolria.entity.SpaceType;
import com.masolria.service.BookingService;
import com.masolria.util.DateTimeParseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/booking", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingController {

    private final  BookingService bookingService;

    @PostMapping(value = "/by-space-type")
    public ResponseEntity<List<BookingDto>> bySpaceType(@RequestBody String spaceType) {
        List<BookingDto> dtoList = bookingService.getByType(SpaceType.valueOf(spaceType));
        return ResponseEntity.ok(dtoList);
    }
    @PostMapping(value = "/by-user-id")//produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookingDto>> byUserId(@RequestBody Long id) {
        List<BookingDto> dtoList = bookingService.getByUserId(id);
        return ResponseEntity.ok(dtoList);
    }
    @PostMapping(value = "/by-date")//produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookingDto>> byDate(@RequestBody String date) {
        LocalDate localDate = DateTimeParseUtil.parseToLDate(date);
        List<BookingDto> dtoList = bookingService.getByDate(localDate);
        return ResponseEntity.ok(dtoList);
    }
    @GetMapping("/free-slots")
    public ResponseEntity<List<BookingDto>> freeSlots(){
        List<BookingDto> freeSlots = bookingService.getFreeSlots();
        return ResponseEntity.ok(freeSlots);
    }
    @PatchMapping("/reserve")
    public ResponseEntity<String> reserve(@RequestBody Long id){
        bookingService.reserve(id);
        return ResponseEntity.ok("The slot was successfully reserved.");
    }
    @PatchMapping("/release")
    public ResponseEntity<String> release(@RequestBody Long id){
        bookingService.release(id);
        return ResponseEntity.ok("Booking updated successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingDto> get(@PathVariable(name="id")Long id){
       BookingDto dto = bookingService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id){
        bookingService.delete(id);
       return ResponseEntity.ok("Booking deleted successfully");
    }

    @PutMapping("/update")
    ResponseEntity<String> update(@RequestBody BookingDto bookingDto){
        bookingService.update(bookingDto);
        return ResponseEntity.ok("Booking updated successfully");
    }
}