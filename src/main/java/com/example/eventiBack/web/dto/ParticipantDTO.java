package com.example.eventiBack.web.dto;

import com.example.eventiBack.dao.entities.Participant;
import com.example.eventiBack.dao.entities.PaymentMethod;

import lombok.Builder;

@Builder
public record ParticipantDTO(
    Long id,
    String name,
    String surname,
    String email,
    String phone,
    PaymentMethod paymentMethod
) {
   public ParticipantDTO toDTO(Participant participant) {
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
