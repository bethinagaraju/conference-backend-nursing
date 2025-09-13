package com.zn.nursing.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zn.nursing.entity.NursingConferenceVenue;
import com.zn.nursing.repository.INursingConferenceVenueRepository;

@Service
public class NursingConferenceVenueService {

    @Autowired
    private INursingConferenceVenueRepository repository;

    public NursingConferenceVenue getVenue() {
        Optional<NursingConferenceVenue> venue = repository.findFirstByOrderByIdAsc();
        return venue.orElse(null);
    }

    public NursingConferenceVenue saveOrUpdateVenue(NursingConferenceVenue venue) {
        Optional<NursingConferenceVenue> existing = repository.findFirstByOrderByIdAsc();
        if (existing.isPresent()) {
            NursingConferenceVenue existingVenue = existing.get();
            existingVenue.setConferenceVenue(venue.getConferenceVenue());
            existingVenue.setAccommodationVenue(venue.getAccommodationVenue());
            return repository.save(existingVenue);
        } else {
            return repository.save(venue);
        }
    }
}
