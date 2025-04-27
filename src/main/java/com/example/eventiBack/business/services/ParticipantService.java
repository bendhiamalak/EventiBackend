package com.example.eventiBack.business.services;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.eventiBack.dao.entities.Evenement;
import com.example.eventiBack.dao.entities.Participant;
import com.example.eventiBack.dao.repositories.EvenementRepository;
import com.example.eventiBack.dao.repositories.ParticipantRepository;
import com.example.eventiBack.mappers.ParticipantMapper;
import com.example.eventiBack.web.dto.ParticipantDTO;

@Service
public class ParticipantService {
    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private EvenementRepository evenementRepository;

    @Autowired
    private ParticipantMapper participantMapper;

    @Transactional
    public ParticipantDTO addParticipant(Long evenementId, ParticipantDTO participantDTO) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new EntityNotFoundException("Evenement not found with id: " + evenementId));

        if (evenement.getParticipants().size() >= evenement.getCapacite()) {
            throw new IllegalStateException("Event capacity is full");
        }

        Participant participant = participantMapper.toEntity(participantDTO);
        participant.setEvenement(evenement);
        evenement.getParticipants().add(participant);
        evenement.setCapacite(evenement.getCapacite() - 1);

        Participant savedParticipant = participantRepository.save(participant);
        return participantMapper.toDTO(savedParticipant);
    }

    @Transactional
    public void deleteParticipant(Long evenementId, Long participantId) {
        Evenement evenement = evenementRepository.findById(evenementId)
                .orElseThrow(() -> new EntityNotFoundException("Evenement not found with id: " + evenementId));

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new EntityNotFoundException("Participant not found with id: " + participantId));

        if (!participant.getEvenement().getId().equals(evenementId)) {
            throw new IllegalArgumentException("Participant does not belong to this event");
        }

        evenement.getParticipants().remove(participant);
        evenement.setCapacite(evenement.getCapacite() + 1);
        participantRepository.delete(participant);
    }
} 