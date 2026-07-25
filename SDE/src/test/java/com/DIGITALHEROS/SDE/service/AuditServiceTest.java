package com.DIGITALHEROS.SDE.service;

import com.DIGITALHEROS.SDE.model.AuditResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuditServiceTest {

    private final AuditService auditService = new AuditService();

    @Test
    void testAuditUrl_HappyPath() {
        // Using a reliable domain for the happy path
        AuditResult result = auditService.auditUrl("https://example.com");
        
        assertNull(result.getError());
        assertEquals(200, result.getHttpStatus());
        assertNotNull(result.getPageTitle());
        assertTrue(result.getPageTitle().contains("Example Domain"));
        assertTrue(result.getResponseTimeMs() > 0);
    }

    @Test
    void testAuditUrl_Failure_InvalidUrl() {
        // Invalid URL format will be caught by the service's exception handler
        AuditResult result = auditService.auditUrl("invalid-url-format");
        
        assertNotNull(result.getError());
        assertEquals(400, result.getHttpStatus());
        assertTrue(result.getError().contains("Error processing request"));
    }

    @Test
    void testAuditUrl_Failure_NonHtmlResponse() {
        // Providing a URL that returns JSON instead of HTML
        AuditResult result = auditService.auditUrl("https://jsonplaceholder.typicode.com/todos/1");
        
        assertNotNull(result.getError());
        assertEquals(400, result.getHttpStatus());
        assertTrue(result.getError().contains("does not return an HTML response"));
    }
}
