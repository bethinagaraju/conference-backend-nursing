package com.zn.nursing.repository;

import com.zn.nursing.entity.NursingOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface INursingOtpRepository extends JpaRepository<NursingOtp, Long> {
    Optional<NursingOtp> findByEmailAndOtpAndIsUsedFalse(String email, String otp);
    void deleteByExpiryTimeBefore(LocalDateTime now);
}
