package com.zn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zn.nursing.entity.NursingConferenceVenue;
import com.zn.nursing.service.NursingConferenceVenueService;

@RestController
@RequestMapping("/api/conference-venue")
public class ConferenceVenueController {

    @Autowired
    private NursingConferenceVenueService service;

    @GetMapping
    public ResponseEntity<NursingConferenceVenue> getVenue() {
        NursingConferenceVenue venue = service.getVenue();
        if (venue != null) {
            return ResponseEntity.ok(venue);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<NursingConferenceVenue> createVenue(@RequestBody NursingConferenceVenue venue) {
        NursingConferenceVenue saved = service.saveOrUpdateVenue(venue);
        return ResponseEntity.ok(saved);
    }

    @PutMapping
    public ResponseEntity<NursingConferenceVenue> updateVenue(@RequestBody NursingConferenceVenue venue) {
        NursingConferenceVenue updated = service.saveOrUpdateVenue(venue);
        return ResponseEntity.ok(updated);
    }
}
