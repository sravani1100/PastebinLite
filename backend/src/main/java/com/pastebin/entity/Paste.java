package com.pastebin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "pastes", indexes = {
        @Index(name = "idx_expires_at", columnList = "expiresAt")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paste {

    @Id
    @Column(length = 8)
    private String id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    private Integer maxViews;

    @Builder.Default
    private Integer currentViews = 0;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean hasExceededMaxViews() {
        return maxViews != null && currentViews >= maxViews;
    }

    public void incrementViews() {
        this.currentViews = (this.currentViews == null ? 0 : this.currentViews) + 1;
    }
}
