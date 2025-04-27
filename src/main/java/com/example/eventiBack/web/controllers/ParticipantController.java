package com.example.eventiBack.web.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eventiBack.business.services.ParticipantService;
import com.example.eventiBack.web.dto.ParticipantDTO;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/evenements/{evenementId}/participants")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ParticipantController {
  
    private final ParticipantService participantService;

    @PostMapping
    public ResponseEntity<ParticipantDTO> addParticipant(
            @PathVariable Long evenementId,
            @RequestBody ParticipantDTO participantDTO) {
        return ResponseEntity.ok(participantService.addParticipant(evenementId, participantDTO));
    }

    @DeleteMapping("/{participantId}")
    public ResponseEntity<Void> deleteParticipant(
            @PathVariable Long evenementId,
            @PathVariable Long participantId) {
        participantService.deleteParticipant(evenementId, participantId);
        return ResponseEntity.ok().build();
    }
}
 