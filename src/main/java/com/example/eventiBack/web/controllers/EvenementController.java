package com.example.eventiBack.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eventiBack.business.services.EvenementService;
import com.example.eventiBack.web.dto.EvenementDTO;

import lombok.AllArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/evenements")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class EvenementController {
    
    private final EvenementService evenementService;

    @GetMapping
    public ResponseEntity<List<EvenementDTO>> getAllEvenements() {
        List<EvenementDTO> evenements = evenementService.getAllEvenements();
        return ResponseEntity.ok(evenements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvenementDTO> getEvenementById(@PathVariable Long id) {
        return ResponseEntity.ok(evenementService.getEvenementById(id));
    }

    @PostMapping
    public ResponseEntity<EvenementDTO> createEvenement(@RequestBody EvenementDTO evenementDTO) {
        return ResponseEntity.ok(evenementService.createEvenement(evenementDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvenementDTO> updateEvenement(
            @PathVariable Long id,
            @RequestBody EvenementDTO evenementDTO) {
        return ResponseEntity.ok(evenementService.updateEvenement(id, evenementDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvenement(@PathVariable Long id) {
        evenementService.deleteEvenement(id);
        return ResponseEntity.ok().build();
    }
}