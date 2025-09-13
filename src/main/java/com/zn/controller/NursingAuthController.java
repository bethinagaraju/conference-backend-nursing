package com.zn.controller;

import com.zn.dto.NursingLoginResponseDTO;
import com.zn.dto.NursingOtpRequestDTO;
import com.zn.dto.NursingOtpVerifyRequestDTO;
import com.zn.nursing.service.NursingOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nursing/auth")
public class NursingAuthController {

    @Autowired
    private NursingOtpService otpService;

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody NursingOtpRequestDTO request) {
        try {
            otpService.sendOtp(request.getEmail());
            return ResponseEntity.ok("OTP sent to your email");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send OTP: " + e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<NursingLoginResponseDTO> verifyOtp(@RequestBody NursingOtpVerifyRequestDTO request) {
        NursingLoginResponseDTO response = new NursingLoginResponseDTO();
        try {
            String token = otpService.verifyOtp(request.getEmail(), request.getOtp());
            response.setSuccess(true);
            response.setToken(token);
            response.setMessage("Login successful");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
