package com.example.eventiBack.business.serviceImpl;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.eventiBack.business.services.FilesStorageService;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {


    private final Path root = Paths.get("uploads");

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

 
    @Override
    public String save(MultipartFile file) {
        String uniqueFilename = "";
        try {
            uniqueFilename = generateUniqueFilename(file);
            Files.copy(file.getInputStream(), this.root.resolve(uniqueFilename));
            Thread.sleep(2000);
            return uniqueFilename;
        } catch (FileAlreadyExistsException e) {
            throw new RuntimeException("A file with the same name already exists.");
        } catch (IOException e) {
            throw new RuntimeException("Failed to save the file: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to save the file: " + e.getMessage());
        }
    }

  
    private String generateUniqueFilename(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        } 
        return UUID.randomUUID().toString() + extension;
    }

    
    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            URI uri = file.toUri();
            if (uri != null) {
                Resource resource = new UrlResource(uri);
                if (resource.exists() || resource.isReadable()) {
                    return resource;
                } else {
                    throw new IOException("Could not read the file!");
                }
            } else {
                throw new RuntimeException("URI for the file is null!");
            }
        } catch (IOException e) {  
            throw new RuntimeException("Error reading the file: " + e.getMessage());
        }
    }

    
    @Override
    public void delete(String filename) {
        try {
            Path file = root.resolve(filename);
            Files.delete(file);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete the file: " + e.getMessage());
        }
    }
}
