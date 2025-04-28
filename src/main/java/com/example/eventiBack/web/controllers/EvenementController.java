package com.example.eventiBack.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eventiBack.business.services.EvenementService;
import com.example.eventiBack.exceptions.EvenementNotFoundException;
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
    public ResponseEntity<EvenementDTO> getEvenementById(@PathVariable Long id) throws EvenementNotFoundException {
        return ResponseEntity.ok(evenementService.getEvenementById(id));
    }

    // Création d'événement
    @PostMapping
    public ResponseEntity<EvenementDTO> createEvenement(@RequestBody EvenementDTO evenementDTO) {
        try {
            // Forcer l'ID à null pour une nouvelle création
            EvenementDTO dtoWithNullId = new EvenementDTO(
                null, // ID forcé à null
                evenementDTO.title(),
                evenementDTO.description(),
                evenementDTO.date(),
                evenementDTO.lieu(),
                evenementDTO.capacite(),
                evenementDTO.categorie(),
                evenementDTO.prix(),
                null, // Image initialisée à null
                evenementDTO.participants()
            );
            
            EvenementDTO created = evenementService.createEvenement(dtoWithNullId);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvenementDTO> updateEvenement (
            @PathVariable Long id,
            @RequestBody EvenementDTO evenementDTO) throws EvenementNotFoundException {
                System.out.print("from put in event"+evenementDTO.participants());
        return ResponseEntity.ok(evenementService.updateEvenement(id, evenementDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvenement(@PathVariable Long id)  throws EvenementNotFoundException {
        evenementService.deleteEvenement(id);
        return ResponseEntity.ok().build();
    }
}