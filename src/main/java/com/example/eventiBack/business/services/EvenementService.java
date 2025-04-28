package com.example.eventiBack.business.services;

import com.example.eventiBack.dao.entities.Evenement;
import com.example.eventiBack.dao.repositories.EvenementRepository;
import com.example.eventiBack.exceptions.EvenementNotFoundException;
import com.example.eventiBack.mappers.EvenementMapper;
import com.example.eventiBack.mappers.ParticipantMapper;
import com.example.eventiBack.web.dto.EvenementDTO;
import com.example.eventiBack.web.dto.ParticipantDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EvenementService {
    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private EvenementMapper evenementMapper;

    @Autowired
    private ParticipantMapper participantMapper;

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

    /*public EvenementDTO createEvenement(EvenementDTO evenementDTO) {
        // S'assurer que la liste des participants est initialisée
        EvenementDTO dtoWithParticipants = new EvenementDTO(
            evenementDTO.id(),
            evenementDTO.title(),
            evenementDTO.description(),
            evenementDTO.date(),
            evenementDTO.lieu(),
            evenementDTO.capacite(),
            evenementDTO.categorie(),
            evenementDTO.prix(),
            evenementDTO.image(),
            evenementDTO.participants() != null ? evenementDTO.participants() : List.of()
        );
        
        Evenement evenement = evenementMapper.toEntity(dtoWithParticipants);
        Evenement savedEvenement = evenementRepository.save(evenement);
        return evenementMapper.toDTO(savedEvenement);
    }*/
    public EvenementDTO createEvenement(EvenementDTO evenementDTO) {
        Evenement evenement = evenementMapper.toEntity(evenementDTO);
        evenement.setId(null); // Garantir que c'est une nouvelle entité
        evenement.setImage(null); // Image sera mise à jour plus tard
        
        Evenement saved = evenementRepository.save(evenement);
        return evenementMapper.toDTO(saved);
    }
    
    public EvenementDTO updateEventImage(Long eventId, String imagePath) {
        Evenement event = evenementRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Événement non trouvé"));
        
        event.setImage(imagePath);
        Evenement updated = evenementRepository.save(event);
        return evenementMapper.toDTO(updated);
    }

    public EvenementDTO updateEvenement(Long id, EvenementDTO evenementDTO) throws EvenementNotFoundException {
        Evenement existingEvenement = evenementRepository.findById(id)
                .orElseThrow(() -> new EvenementNotFoundException("Evenement not found with id: " + id));

        // Préserver les participants existants si non fournis
        List<ParticipantDTO> participants = evenementDTO.participants() != null 
            ? evenementDTO.participants() 
            : existingEvenement.getParticipants().stream()
                .map(participantMapper::toDTO)
                .collect(Collectors.toList());

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
            participants
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