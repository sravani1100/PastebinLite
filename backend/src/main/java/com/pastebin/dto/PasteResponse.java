package com.pastebin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasteResponse {

    private String id;
    private String content;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Integer maxViews;
    private Integer currentViews;
}
