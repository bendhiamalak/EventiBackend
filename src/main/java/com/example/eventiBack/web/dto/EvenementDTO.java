package com.example.eventiBack.web.dto;

import java.util.Date;
import java.util.List;

import com.example.eventiBack.dao.entities.Categorie;

import lombok.Builder;

@Builder
public record EvenementDTO(
    Long id,
    String title,
    String description,
    Date date,
    String lieu,
    Integer capacite,
    Categorie categorie,
    Double prix,
    String image,
    List<ParticipantDTO> participants
) {

}
