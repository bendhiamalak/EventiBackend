package com.example.eventiBack.business.services;

import com.example.eventiBack.dao.entities.Evenement;
import com.example.eventiBack.dao.entities.Participant;

import com.example.eventiBack.dao.repositories.EvenementRepository;
import com.example.eventiBack.dao.repositories.ParticipantRepository;

import com.example.eventiBack.exceptions.DuplicateParticipantException;
import com.example.eventiBack.exceptions.EvenementNotFoundException;
import com.example.eventiBack.mappers.ParticipantMapper;
import com.example.eventiBack.web.dto.ParticipantDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private ParticipantMapper participantMapper;

    @Transactional
    public ParticipantDTO addParticipant(Long evenementId, ParticipantDTO participantDTO) 
            throws EvenementNotFoundException, DuplicateParticipantException {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new EvenementNotFoundException("Evenement not found with id: " + evenementId));

        // Vérifier si l'événement a encore de la capacité
        if (evenement.getParticipants().size() >= evenement.getCapacite()) {
            throw new DuplicateParticipantException("Event capacity is full");
        }

        // Vérifier si le participant existe déjà
        if (participantRepository.existsByEmail(participantDTO.email())) {
            throw new DuplicateParticipantException("Participant with email " + participantDTO.email() + " already exists");
        }

        Participant participant = participantMapper.toEntity(participantDTO);
        participant.setEvenement(evenement);
        
        // Sauvegarder le participant
        Participant savedParticipant = participantRepository.save(participant);
        
        // Mettre à jour l'événement
        evenement.getParticipants().add(savedParticipant);
        // Ne pas réduire la capacité mais garder le compte des participants
        evenementRepository.save(evenement);
        
        return participantMapper.toDTO(savedParticipant);
    }


    @Transactional
    public void deleteParticipant(Long evenementId, Long participantId) 
            throws EvenementNotFoundException {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new EvenementNotFoundException("Evenement not found with id: " + evenementId));

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new EvenementNotFoundException("Participant not found with id: " + participantId));

        if (!participant.getEvenement().getId().equals(evenementId)) {
            throw new EvenementNotFoundException("Participant does not belong to this event");
        }

        evenement.getParticipants().remove(participant);
        evenement.setCapacite(evenement.getCapacite() + 1);
        participantRepository.delete(participant);
    }
} 