package com.example.eventiBack.dao.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventiBack.dao.entities.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
} 