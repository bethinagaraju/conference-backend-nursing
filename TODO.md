# TODO: Implement OTP-based Login for Nursing Website

## Overview
Implement an OTP-based authentication system for the nursing website. Users will provide their email, receive an OTP via email, and verify it to get a JWT token.

## Steps to Complete

### 1. Configure Email Settings ✅
- Update `application.properties` with Hostinger SMTP settings for nursing email (rigelinternational1@gmail.com)
- Ensure Spring Mail dependencies are in `pom.xml`

### 2. Create OTP Entity ✅
- Create `NursingOtp` entity in `src/main/java/com/zn/nursing/entity/`
- Fields: id, email, otp, expiryTime, createdAt, isUsed

### 3. Create OTP Repository ✅
- Create `INursingOtpRepository` interface in `src/main/java/com/zn/nursing/repository/`
- Extend JpaRepository with custom methods for finding by email and otp

### 4. Create DTOs ✅
- Create `NursingOtpRequestDTO` (email)
- Create `NursingOtpVerifyRequestDTO` (email, otp)
- Create `NursingLoginResponseDTO` (success, token, message)

### 5. Create OTP Service ✅
- Create `NursingOtpService` in `src/main/java/com/zn/nursing/service/`
- Methods: generateOtp, sendOtpEmail, verifyOtp
- Integrate with existing JwtUtil for token generation

### 6. Create Nursing Auth Controller ✅
- Create `NursingAuthController` in `src/main/java/com/zn/controller/`
- Endpoints:
  - POST `/nursing/auth/send-otp` - Send OTP to email
  - POST `/nursing/auth/verify-otp` - Verify OTP and return JWT

### 7. Test the Implementation
- Test sending OTP (user will test in Postman)
- Test verifying OTP and receiving JWT
- Verify JWT can be used for protected endpoints

## Dependencies
- Spring Boot Mail Starter (check pom.xml)
- JJWT for JWT (already present)

## Email Configuration Details
- Host: smtp.hostinger.com
- Port: 465 (SSL)
- Username: rigelinternational1@gmail.com
- Password: [To be provided/confirmed]
