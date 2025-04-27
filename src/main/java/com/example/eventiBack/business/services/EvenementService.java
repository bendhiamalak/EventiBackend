package com.example.eventiBack.business.services;

import com.example.eventiBack.dao.entities.Evenement;

import com.example.eventiBack.dao.repositories.EvenementRepository;

import com.example.eventiBack.exceptions.EvenementNotFoundException;
import com.example.eventiBack.mappers.EvenementMapper;
import com.example.eventiBack.web.dto.EvenementDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvenementService {
    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private EvenementMapper evenementMapper;

    public List<EvenementDTO> getAllEvenements() {
        return evenementRepository.findAll().stream()
                .map(evenementMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EvenementDTO getEvenementById(Long id) throws EvenementNotFoundException {
        return evenementRepository.findById(id)
                .map(evenementMapper::toDTO)
                .orElseThrow(() -> new EvenementNotFoundException("Evenement not found with id: " + id));
    }

    public EvenementDTO createEvenement(EvenementDTO evenementDTO) {
        Evenement evenement = evenementMapper.toEntity(evenementDTO);
        Evenement savedEvenement = evenementRepository.save(evenement);
        return evenementMapper.toDTO(savedEvenement);
    }

    public EvenementDTO updateEvenement(Long id, EvenementDTO evenementDTO) throws EvenementNotFoundException {
        if (!evenementRepository.existsById(id)) {
            throw new EvenementNotFoundException("Evenement not found with id: " + id);
        }
        EvenementDTO updatedDTO = new EvenementDTO(
            id,                       
            evenementDTO.title(),
            evenementDTO.description(),
            evenementDTO.date(),
            evenementDTO.lieu(),
            evenementDTO.capacite(),
            evenementDTO.categorie(),
            evenementDTO.prix(),
            evenementDTO.image(),
            evenementDTO.participants()
        );
        Evenement evenement = evenementMapper.toEntity(updatedDTO);
        Evenement updatedEvenement = evenementRepository.save(evenement);
        return evenementMapper.toDTO(updatedEvenement);
    }

    
    public void deleteEvenement(Long id) throws EvenementNotFoundException {
        if (!evenementRepository.existsById(id)) {
            throw new EvenementNotFoundException("Evenement not found with id: " + id);
        }
        evenementRepository.deleteById(id);
    }
} 