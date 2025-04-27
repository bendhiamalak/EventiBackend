package com.example.eventiBack.mappers;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.eventiBack.dao.entities.Evenement;
import com.example.eventiBack.web.dto.EvenementDTO;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EvenementMapper {

    private final ParticipantMapper participantMapper;

    public EvenementDTO toDTO(Evenement evenement) {
        if (evenement == null) {
            return null;
        }
        return new EvenementDTO(
                evenement.getId(),
                evenement.getTitle(),
                evenement.getDescription(),
                evenement.getDate(),
                evenement.getLieu(),
                evenement.getCapacite(),
                evenement.getCategorie(),
                evenement.getPrix(),
                evenement.getImage(),
                evenement.getParticipants() != null
                        ? evenement.getParticipants().stream()
                        .map(participantMapper::toDTO)
                        .collect(Collectors.toList())
                        : null
        );
    }

    public Evenement toEntity(EvenementDTO dto) {
        if (dto == null) {
            return null;
        }
        return Evenement.builder()
                .id(dto.id())
                .title(dto.title())
                .description(dto.description())
                .date(dto.date())
                .lieu(dto.lieu())
                .capacite(dto.capacite())
                .categorie(dto.categorie())
                .prix(dto.prix())
                .image(dto.image())
                .build();
    }
}