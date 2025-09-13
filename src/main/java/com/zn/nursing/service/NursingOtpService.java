package com.zn.nursing.service;

import com.zn.nursing.entity.NursingOtp;
import com.zn.nursing.repository.INursingOtpRepository;
import com.zn.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class NursingOtpService {

    @Autowired
    private INursingOtpRepository otpRepository;

    @org.springframework.beans.factory.annotation.Qualifier("nursingMailSender")
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JwtUtil jwtUtil;

    private static final int OTP_EXPIRY_MINUTES = 5;

    public void sendOtp(String email) {
        // Generate 6-digit OTP
        String otp = generateOtp();

        // Create OTP entity
        NursingOtp nursingOtp = new NursingOtp();
        nursingOtp.setEmail(email);
        nursingOtp.setOtp(otp);
        nursingOtp.setExpiryTime(LocalDateTime.now().plusMinutes(OTP_EXPIRY_MINUTES));
        nursingOtp.setCreatedAt(LocalDateTime.now());
        nursingOtp.setIsUsed(false);

        // Save to DB
        otpRepository.save(nursingOtp);

        // Send email
        sendOtpEmail(email, otp);
    }

    public String verifyOtp(String email, String otp) {
        Optional<NursingOtp> otpEntity = otpRepository.findByEmailAndOtpAndIsUsedFalse(email, otp);

        if (otpEntity.isPresent()) {
            NursingOtp nursingOtp = otpEntity.get();

            if (nursingOtp.getExpiryTime().isAfter(LocalDateTime.now())) {
                // Mark as used
                nursingOtp.setIsUsed(true);
                otpRepository.save(nursingOtp);

                // Generate JWT token
                return jwtUtil.generateToken(email, "NURSING_USER");
            } else {
                throw new RuntimeException("OTP has expired");
            }
        } else {
            throw new RuntimeException("Invalid OTP");
        }
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("secretary@nursingmeet2026.com");
        message.setTo(to);
        message.setSubject("Your OTP for Nursing Login");
        message.setText("Your OTP is: " + otp + ". It will expire in 5 minutes.");
        mailSender.send(message);
    }
}
