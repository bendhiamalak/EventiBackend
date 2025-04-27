package com.example.eventiBack.web.controllers;

import com.example.eventiBack.business.services.FilesStorageService;
import com.example.eventiBack.web.dto.ResponseMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/storage")
@CrossOrigin("http://localhost:4200")
public class FilesStorageController {

    @Autowired
    private FilesStorageService storageService;

    @PatchMapping("/upload/{id}")
    public ResponseEntity<ResponseMessage> uploadFile(@PathVariable Long id,
                                                    @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            String filename = storageService.save(file);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + ". Error: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        String message = "";
        try {
            Resource file = storageService.load(filename);
            return ResponseEntity.ok().body(file);
        } catch (Exception e) {
            message = "Could not get the file: " + filename;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
    }
} 