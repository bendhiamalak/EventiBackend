package com.example.eventiBack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.eventiBack.business.services.FilesStorageService;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class EventiBackApplication implements CommandLineRunner {

	@Autowired
	FilesStorageService filesStorageService;

	public static void main(String[] args) {
		SpringApplication.run(EventiBackApplication.class, args);
	}

	@Override
	public void run(String... arg) throws Exception {
		log.info("Storage initialisation");
		filesStorageService.init();
	}
}
