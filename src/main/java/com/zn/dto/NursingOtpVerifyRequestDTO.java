package com.zn.dto;

import lombok.Data;

@Data
public class NursingOtpVerifyRequestDTO {
    private String email;
    private String otp;
}
