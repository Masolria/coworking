package com.masolria.controller.rest;

import com.masolria.annotation.AuthRequired;
import com.masolria.dto.IdRequest;
import com.masolria.dto.SpaceDto;
import com.masolria.service.SpaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping(value="/space", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController()
@RequiredArgsConstructor
@AuthRequired
public class SpaceController {
    private final SpaceService spaceService;

    @GetMapping("/by-id")
    ResponseEntity<SpaceDto> byId(@RequestBody IdRequest req) {
        SpaceDto dto = spaceService.findById(req.getId());
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/delete")
    ResponseEntity<String> delete(@RequestBody IdRequest req) {
        spaceService.delete(req.getId());
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
    //
    @PutMapping("/add")
    ResponseEntity<String> save(@RequestBody SpaceDto spaceDto){
       SpaceDto dto = spaceService.save(spaceDto);
        return ResponseEntity.ok(String.format("Space entry added successfully.Assigned id is %d.",dto.getId()));
    }
}