package com.pastebin.repository;

import com.pastebin.entity.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface PasteRepository extends JpaRepository<Paste, String> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Paste p WHERE p.expiresAt IS NOT NULL AND p.expiresAt < :now")
    int deleteExpiredPastes(LocalDateTime now);

    @Modifying
    @Transactional
    @Query("DELETE FROM Paste p WHERE p.maxViews IS NOT NULL AND p.currentViews >= p.maxViews")
    int deleteExceededViewsPastes();
}
