package com.skill2job.dto;

import com.skill2job.entity.Notification;

import java.time.Instant;

public record NotificationResponse(String id, String message, boolean read, Instant createdAt) {
    public static NotificationResponse from(Notification n) {
        return new NotificationResponse(n.getId(), n.getMessage(), n.isRead(), n.getCreatedAt());
    }
}
