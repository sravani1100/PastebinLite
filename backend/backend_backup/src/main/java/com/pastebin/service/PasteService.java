package com.pastebin.service;

import com.pastebin.dto.CreatePasteRequest;
import com.pastebin.dto.PasteResponse;
import com.pastebin.entity.Paste;
import com.pastebin.exception.PasteNotFoundException;
import com.pastebin.repository.PasteRepository;
import com.pastebin.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasteService {

    private final PasteRepository pasteRepository;
    private final IdGenerator idGenerator;

    @Transactional
    public PasteResponse createPaste(CreatePasteRequest request, String baseUrl) {
        String id = generateUniqueId();

        LocalDateTime expiresAt = null;
        if (request.getTtlSeconds() != null && request.getTtlSeconds() > 0) {
            expiresAt = LocalDateTime.now().plusSeconds(request.getTtlSeconds());
        }

        Paste paste = Paste.builder()
                .id(id)
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .expiresAt(expiresAt)
                .maxViews(request.getMaxViews())
                .currentViews(0)
                .build();

        pasteRepository.save(paste);
        log.info("Created paste with id: {}", id);

        return mapToResponse(paste, baseUrl);
    }

    @Transactional
    public PasteResponse getPaste(String id, String baseUrl) {
        Paste paste = pasteRepository.findById(id)
                .orElseThrow(() -> new PasteNotFoundException(id));

        // Check if expired
        if (paste.isExpired()) {
            pasteRepository.delete(paste);
            throw new PasteNotFoundException(id);
        }

        // Check if exceeded max views
        if (paste.hasExceededMaxViews()) {
            pasteRepository.delete(paste);
            throw new PasteNotFoundException(id);
        }

        // Increment view count
        paste.incrementViews();
        pasteRepository.save(paste);

        // Check again after increment (for maxViews enforcement)
        if (paste.hasExceededMaxViews()) {
            pasteRepository.delete(paste);
        }

        return mapToResponse(paste, baseUrl);
    }

    @Transactional(readOnly = true)
    public String getRawPaste(String id) {
        Paste paste = pasteRepository.findById(id)
                .orElseThrow(() -> new PasteNotFoundException(id));

        if (paste.isExpired() || paste.hasExceededMaxViews()) {
            throw new PasteNotFoundException(id);
        }

        return paste.getContent();
    }

    @Transactional
    public void deletePaste(String id) {
        if (!pasteRepository.existsById(id)) {
            throw new PasteNotFoundException(id);
        }
        pasteRepository.deleteById(id);
        log.info("Deleted paste with id: {}", id);
    }

    @Scheduled(fixedRate = 60000) // Run every minute
    @Transactional
    public void cleanupExpiredPastes() {
        int deletedExpired = pasteRepository.deleteExpiredPastes(LocalDateTime.now());
        int deletedExceeded = pasteRepository.deleteExceededViewsPastes();
        if (deletedExpired > 0 || deletedExceeded > 0) {
            log.info("Cleaned up {} expired and {} exceeded view pastes", deletedExpired, deletedExceeded);
        }
    }

    private String generateUniqueId() {
        String id;
        int attempts = 0;
        do {
            id = idGenerator.generateId();
            attempts++;
            if (attempts > 10) {
                throw new RuntimeException("Failed to generate unique ID after 10 attempts");
            }
        } while (pasteRepository.existsById(id));
        return id;
    }

    private PasteResponse mapToResponse(Paste paste, String baseUrl) {
        return PasteResponse.builder()
                .id(paste.getId())
                .content(paste.getContent())
                .url(baseUrl + "/" + paste.getId())
                .createdAt(paste.getCreatedAt())
                .expiresAt(paste.getExpiresAt())
                .maxViews(paste.getMaxViews())
                .currentViews(paste.getCurrentViews())
                .build();
    }
}
