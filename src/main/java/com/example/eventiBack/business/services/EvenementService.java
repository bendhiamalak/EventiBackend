package com.example.eventiBack.business.services;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.example.eventiBack.dao.entities.Evenement;

import com.example.eventiBack.dao.repositories.EvenementRepository;
import com.example.eventiBack.mappers.EvenementMapper;
import com.example.eventiBack.web.dto.EvenementDTO;



@Service
@RequiredArgsConstructor
public class EvenementService {
    private final EvenementRepository evenementRepository;
    private final EvenementMapper evenementMapper;

    public EvenementDTO getEvenementById(Long id) {
        return evenementRepository.findById(id)
                .map(evenementMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Evenement not found with id: " + id));
    }

    public List<EvenementDTO> getAllEvenements() {
    return evenementRepository.findAll()
            .stream()
            .map(evenementMapper::toDTO)
            .collect(Collectors.toList());
}

    public EvenementDTO createEvenement(EvenementDTO evenementDTO) {
        Evenement evenement = evenementMapper.toEntity(evenementDTO);
        Evenement savedEvenement = evenementRepository.save(evenement);
        return evenementMapper.toDTO(savedEvenement);
    }

    public EvenementDTO updateEvenement(Long id, EvenementDTO evenementDTO) {
        if (!evenementRepository.existsById(id)) {
            throw new EntityNotFoundException("Evenement not found with id: " + id);
        }
        Evenement evenementToUpdate = evenementMapper.toEntity(
                new EvenementDTO(
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
                )
        );
        Evenement updatedEvenement = evenementRepository.save(evenementToUpdate);
        return evenementMapper.toDTO(updatedEvenement);
    }

    public void deleteEvenement(Long id) {
        if (!evenementRepository.existsById(id)) {
            throw new EntityNotFoundException("Evenement not found with id: " + id);
        }
        evenementRepository.deleteById(id);
    }
} 