package com.DIGITALHEROS.SDE.service;

import com.DIGITALHEROS.SDE.model.AuditResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class AuditService {

    public AuditResult auditUrl(String url) {
        long startTime = System.currentTimeMillis();
        
        try {
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
            
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            long responseTimeMs = System.currentTimeMillis() - startTime;
            int status = response.statusCode();
            
            // Check for valid HTML content-type
            String contentType = response.headers().firstValue("content-type").orElse("");
            if (!contentType.toLowerCase().contains("text/html")) {
                return new AuditResult(400, responseTimeMs, "Error: URL does not return an HTML response.");
            }

            // Handle non-200 responses safely
            if (status < 200 || status >= 300) {
                return new AuditResult(status, responseTimeMs, "Error: Received HTTP " + status);
            }

            // Parse HTML with Jsoup
            Document doc = Jsoup.parse(response.body());
            
            String title = doc.title();
            
            Element metaTag = doc.select("meta[name=description]").first();
            String metaDesc = (metaTag != null) ? metaTag.attr("content") : null;
            
            int h1Count = doc.select("h1").size();
            int missingAltCount = doc.select("img:not([alt]), img[alt=\"\"]").size();
            
            String text = doc.body().text();
            int wordCount = text.isEmpty() ? 0 : text.split("\\s+").length;
            
            return new AuditResult(status, responseTimeMs, title, metaDesc, h1Count, missingAltCount, wordCount);
            
        } catch (Exception e) {
            long responseTimeMs = System.currentTimeMillis() - startTime;
            // Catch timeouts and invalid URIs
            return new AuditResult(400, responseTimeMs, "Error processing request: " + e.getMessage());
        }
    }
}
