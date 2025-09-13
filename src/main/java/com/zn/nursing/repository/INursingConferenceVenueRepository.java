package com.zn.nursing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zn.nursing.entity.NursingConferenceVenue;

public interface INursingConferenceVenueRepository extends JpaRepository<NursingConferenceVenue, Long> {
    Optional<NursingConferenceVenue> findFirstByOrderByIdAsc();
}
