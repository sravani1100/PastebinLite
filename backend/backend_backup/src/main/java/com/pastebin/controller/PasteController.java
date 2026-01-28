package com.pastebin.controller;

import com.pastebin.dto.CreatePasteRequest;
import com.pastebin.dto.PasteResponse;
import com.pastebin.service.PasteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pastes")
@RequiredArgsConstructor
public class PasteController {

    private final PasteService pasteService;

    @PostMapping
    public ResponseEntity<PasteResponse> createPaste(
            @Valid @RequestBody CreatePasteRequest request,
            HttpServletRequest httpRequest) {

        String baseUrl = getBaseUrl(httpRequest);
        PasteResponse response = pasteService.createPaste(request, baseUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PasteResponse> getPaste(
            @PathVariable String id,
            HttpServletRequest httpRequest) {

        String baseUrl = getBaseUrl(httpRequest);
        PasteResponse response = pasteService.getPaste(id, baseUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{id}/raw", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getRawPaste(@PathVariable String id) {
        String content = pasteService.getRawPaste(id);
        return ResponseEntity.ok(content);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePaste(@PathVariable String id) {
        pasteService.deletePaste(id);
        return ResponseEntity.ok(Map.of("message", "Paste deleted successfully"));
    }

    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if ((scheme.equals("http") && serverPort != 80) ||
                (scheme.equals("https") && serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath).append("/api/pastes");
        return url.toString();
    }
}
