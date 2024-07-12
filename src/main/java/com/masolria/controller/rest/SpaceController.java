package com.masolria.controller.rest;

import com.masolria.dto.SpaceDto;
import com.masolria.service.SpaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/space")
public class SpaceController {
    SpaceService spaceService;

    @GetMapping("/by-id")
    ResponseEntity<SpaceDto> byId(@RequestBody Long id) {
        SpaceDto dto = spaceService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete")
    ResponseEntity<String> delete(@RequestBody Long id) {
        spaceService.delete(id);
        return ResponseEntity.ok("Space entry deleted successfully.");
    }

    @PostMapping("/update")
    ResponseEntity<String> update(@RequestBody SpaceDto spaceDto){
        spaceService.update(spaceDto);
        return ResponseEntity.ok("Space entry updated successfully.");
    }

    @GetMapping("/all")
    ResponseEntity<List<SpaceDto>> getAll() {
        List<SpaceDto> list = spaceService.getAll();
        return ResponseEntity.ok(list);
    }
    @PutMapping("/add")
    ResponseEntity<String> save(@RequestBody SpaceDto spaceDto){
       SpaceDto dto = spaceService.save(spaceDto);
        return ResponseEntity.ok(String.format("Space entry added successfully.Assigned id is %d.",dto.id()));
    }

}