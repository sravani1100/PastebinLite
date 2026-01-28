package com.pastebin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePasteRequest {

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 10485760, message = "Content cannot exceed 10MB")
    private String content;

    @Positive(message = "TTL must be a positive number")
    private Long ttlSeconds;

    @Positive(message = "Max views must be a positive number")
    private Integer maxViews;
}
