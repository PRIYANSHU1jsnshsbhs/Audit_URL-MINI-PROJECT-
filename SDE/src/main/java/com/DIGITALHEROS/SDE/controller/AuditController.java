package com.DIGITALHEROS.SDE.controller;

import com.DIGITALHEROS.SDE.model.AuditResult;
import com.DIGITALHEROS.SDE.service.AuditService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Ensures your React frontend can call this
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @GetMapping("/audit")
    public ResponseEntity<AuditResult> audit(@RequestParam String url) {
        // Basic sanitization and validation
        if (url == null || url.trim().isEmpty() || (!url.startsWith("http://") && !url.startsWith("https://"))) {
            return ResponseEntity.badRequest().body(new AuditResult(400, 0, "Invalid URL format. Must start with http:// or https://"));
        }

        AuditResult result = auditService.auditUrl(url);
        
        if (result.getError() != null) {
            return ResponseEntity.badRequest().body(result);
        }
        
        return ResponseEntity.ok(result);
    }
}
