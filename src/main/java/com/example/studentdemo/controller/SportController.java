package com.example.studentdemo.controller;

import com.example.studentdemo.entity.Sport;
import com.example.studentdemo.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sports")
public class SportController {
    
    @Autowired
    private SportService sportService;
    
    // GET - Get all sports
    @GetMapping
    public ResponseEntity<List<Sport>> getAllSports() {
        List<Sport> sports = sportService.getAllSports();
        return ResponseEntity.ok(sports);
    }
    
    // GET - Get sport by id
    @GetMapping("/{id}")
    public ResponseEntity<Sport> getSportById(@PathVariable Long id) {
        Optional<Sport> sport = sportService.getSportById(id);
        return sport.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // POST - Create a new sport
    @PostMapping
    public ResponseEntity<?> createSport(@RequestBody Sport sport) {
        try {
            Sport createdSport = sportService.createSport(sport);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSport);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    // PUT - Update an existing sport
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSport(@PathVariable Long id, @RequestBody Sport sport) {
        try {
            Sport updatedSport = sportService.updateSport(id, sport);
            return ResponseEntity.ok(updatedSport);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    // DELETE - Delete a sport
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable Long id) {
        try {
            sportService.deleteSport(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}


