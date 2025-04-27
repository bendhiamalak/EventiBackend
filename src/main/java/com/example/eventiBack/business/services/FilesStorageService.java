package com.example.eventiBack.business.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    public void init();

    public String save(MultipartFile file);

    public Resource load(String filename);

    public void delete(String filename);
}
