package com.example.eventiBack.web.controllers;

import com.example.eventiBack.business.services.EvenementService;
import com.example.eventiBack.business.services.FilesStorageService;
import com.example.eventiBack.web.dto.EvenementDTO;
import com.example.eventiBack.web.dto.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api")
@CrossOrigin("http://localhost:4200")
public class FilesStorageController {

    @Autowired
    private FilesStorageService storageService;
    
    @Autowired
    private EvenementService evenementService;

    @PatchMapping("/storage/upload/{id}")
    public ResponseEntity<EvenementDTO> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        
        try {
            // 1. Sauvegarder le fichier
            String filename = storageService.save(file);
            
            // 2. Mettre à jour l'événement avec le chemin de l'image
            EvenementDTO updated = evenementService.updateEventImage(id, filename);
            
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/uploads/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        String message = "";
        try {
            Resource file = storageService.load(filename);
            return ResponseEntity.ok()
            .body(file);
        } catch (Exception e) {
            message = "Could not get the file: " + filename;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }
} 