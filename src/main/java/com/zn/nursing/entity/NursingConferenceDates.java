package com.zn.nursing.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "nursing_conference_dates")
@Data
public class NursingConferenceDates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String earlyBird;
    private String abstractDeadline;
    private String standardDeadline;
    private String conference;
    private String registrationDeadline;
}
