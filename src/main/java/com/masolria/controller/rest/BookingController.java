package com.masolria.controller.rest;

import com.masolria.dto.BookingDto;
import com.masolria.dto.SpaceTypeRequest;
import com.masolria.service.BookingService;
import com.masolria.util.DateTimeParseUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    BookingService bookingService;

    @PostMapping(value = "/by-space-type",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookingDto>> bySpaceType(@RequestBody SpaceTypeRequest spaceType) {
        List<BookingDto> dtoList = bookingService.getByType(spaceType.get());
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
    public BookingDto get(@PathVariable(name="id")Long id){
        return bookingService.findById(id);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody Long id){
        bookingService.delete(id);
       return ResponseEntity.ok("Booking deleted successfully");
    }

    @PutMapping("/update")
    ResponseEntity<String> update(@RequestBody BookingDto bookingDto){
        bookingService.update(bookingDto);
        return ResponseEntity.ok("Booking updated successfully");
    }
}