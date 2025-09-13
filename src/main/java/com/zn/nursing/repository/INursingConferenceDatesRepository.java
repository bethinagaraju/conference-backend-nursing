package com.zn.nursing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zn.nursing.entity.NursingConferenceDates;

public interface INursingConferenceDatesRepository extends JpaRepository<NursingConferenceDates, Long> {
    Optional<NursingConferenceDates> findFirstByOrderByIdAsc();
}
