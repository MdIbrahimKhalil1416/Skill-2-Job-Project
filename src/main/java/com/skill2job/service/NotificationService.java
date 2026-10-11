package com.skill2job.service;

import com.skill2job.dto.NotificationResponse;
import com.skill2job.entity.Notification;
import com.skill2job.exception.ApiException;
import com.skill2job.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/** Simple in-app notifications (bell icon in the navbar). */
@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public void notify(String userId, String message) {
        repository.save(new Notification(userId, message));
    }

    public List<NotificationResponse> list(String userId) {
        return repository.findTop30ByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(NotificationResponse::from).toList();
    }

    public long unreadCount(String userId) {
        return repository.countByUserIdAndReadFalse(userId);
    }

    public void markRead(String userId, String id) {
        Notification n = repository.findById(id)
                .filter(x -> x.getUserId().equals(userId))
                .orElseThrow(() -> ApiException.notFound("Notification not found."));
        n.setRead(true);
        repository.save(n);
    }

    public void markAllRead(String userId) {
        List<Notification> unread = repository.findByUserIdAndReadFalse(userId);
        unread.forEach(n -> n.setRead(true));
        repository.saveAll(unread);
    }
}
