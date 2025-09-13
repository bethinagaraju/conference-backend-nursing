package com.zn.nursing.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zn.nursing.entity.NursingConferenceDates;
import com.zn.nursing.repository.INursingConferenceDatesRepository;

@Service
public class NursingConferenceDatesService {

    @Autowired
    private INursingConferenceDatesRepository repository;

    public NursingConferenceDates getDates() {
        Optional<NursingConferenceDates> dates = repository.findFirstByOrderByIdAsc();
        return dates.orElse(null);
    }

    public NursingConferenceDates saveOrUpdateDates(NursingConferenceDates dates) {
        Optional<NursingConferenceDates> existing = repository.findFirstByOrderByIdAsc();
        if (existing.isPresent()) {
            NursingConferenceDates existingDates = existing.get();
            existingDates.setEarlyBird(dates.getEarlyBird());
            existingDates.setAbstractDeadline(dates.getAbstractDeadline());
            existingDates.setStandardDeadline(dates.getStandardDeadline());
            existingDates.setConference(dates.getConference());
            existingDates.setRegistrationDeadline(dates.getRegistrationDeadline());
            return repository.save(existingDates);
        } else {
            return repository.save(dates);
        }
    }
}
