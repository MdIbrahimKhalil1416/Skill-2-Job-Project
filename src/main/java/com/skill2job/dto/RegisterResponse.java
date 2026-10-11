package com.skill2job.dto;

/**
 * Returned by /api/auth/register. No JWT yet: the account is unverified until the OTP is confirmed.
 * emailSent=false means the account exists but the code email could not be sent right now - the user can
 * request it again immediately from the verification page.
 */
public record RegisterResponse(String userId, String email, String message, long otpExpiresInSeconds, boolean emailSent) {
}
