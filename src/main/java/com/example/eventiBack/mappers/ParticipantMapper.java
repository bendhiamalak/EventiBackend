package com.example.eventiBack.mappers;

import org.springframework.stereotype.Component;

import com.example.eventiBack.dao.entities.Participant;
import com.example.eventiBack.web.dto.ParticipantDTO;

@Component
public class ParticipantMapper {

    public ParticipantDTO toDTO(Participant participant) {
        if (participant == null) {
            return null;
        }
        return new ParticipantDTO(
                participant.getId(),
                participant.getName(),
                participant.getSurname(),
                participant.getEmail(),
                participant.getPhone(),
                participant.getPaymentMethod()
        );
    }

    public Participant toEntity(ParticipantDTO dto) {
        if (dto == null) {
            return null;
        }
        return Participant.builder()
                .id(dto.id())
                .name(dto.name())
                .surname(dto.surname())
                .email(dto.email())
                .phone(dto.phone())
                .paymentMethod(dto.paymentMethod())
                .build();
    }
}