package com.zn.dto;

import lombok.Data;

@Data
public class NursingLoginResponseDTO {
    private boolean success;
    private String token;
    private String message;
}
