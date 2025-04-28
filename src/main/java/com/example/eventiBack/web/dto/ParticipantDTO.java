package com.example.eventiBack.web.dto;


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

}
