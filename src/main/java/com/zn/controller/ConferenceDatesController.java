package com.zn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zn.nursing.entity.NursingConferenceDates;
import com.zn.nursing.service.NursingConferenceDatesService;

@RestController
@RequestMapping("/api/conference-dates")
public class ConferenceDatesController {

    @Autowired
    private NursingConferenceDatesService service;

    @GetMapping
    public ResponseEntity<NursingConferenceDates> getDates() {
        NursingConferenceDates dates = service.getDates();
        if (dates != null) {
            return ResponseEntity.ok(dates);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<NursingConferenceDates> createDates(@RequestBody NursingConferenceDates dates) {
        NursingConferenceDates saved = service.saveOrUpdateDates(dates);
        return ResponseEntity.ok(saved);
    }

    @PutMapping
    public ResponseEntity<NursingConferenceDates> updateDates(@RequestBody NursingConferenceDates dates) {
        NursingConferenceDates updated = service.saveOrUpdateDates(dates);
        return ResponseEntity.ok(updated);
    }
}
