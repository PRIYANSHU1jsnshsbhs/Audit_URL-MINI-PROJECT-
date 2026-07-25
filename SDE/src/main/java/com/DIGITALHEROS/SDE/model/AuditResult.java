package com.DIGITALHEROS.SDE.model;

public class AuditResult {
    private int httpStatus;
    private long responseTimeMs;
    private String pageTitle;
    private String metaDescription;
    private int h1Count;
    private int imagesMissingAltCount;
    private int wordCount;
    private String error;

    // Constructors
    public AuditResult() {}

    // Success Constructor
    public AuditResult(int httpStatus, long responseTimeMs, String pageTitle, String metaDescription, int h1Count, int imagesMissingAltCount, int wordCount) {
        this.httpStatus = httpStatus;
        this.responseTimeMs = responseTimeMs;
        this.pageTitle = pageTitle;
        this.metaDescription = metaDescription;
        this.h1Count = h1Count;
        this.imagesMissingAltCount = imagesMissingAltCount;
        this.wordCount = wordCount;
    }

    // Error Constructor
    public AuditResult(int httpStatus, long responseTimeMs, String error) {
        this.httpStatus = httpStatus;
        this.responseTimeMs = responseTimeMs;
        this.error = error;
    }

    // Getters and Setters
    public int getHttpStatus() { return httpStatus; }
    public void setHttpStatus(int httpStatus) { this.httpStatus = httpStatus; }
    public long getResponseTimeMs() { return responseTimeMs; }
    public void setResponseTimeMs(long responseTimeMs) { this.responseTimeMs = responseTimeMs; }
    public String getPageTitle() { return pageTitle; }
    public void setPageTitle(String pageTitle) { this.pageTitle = pageTitle; }
    public String getMetaDescription() { return metaDescription; }
    public void setMetaDescription(String metaDescription) { this.metaDescription = metaDescription; }
    public int getH1Count() { return h1Count; }
    public void setH1Count(int h1Count) { this.h1Count = h1Count; }
    public int getImagesMissingAltCount() { return imagesMissingAltCount; }
    public void setImagesMissingAltCount(int imagesMissingAltCount) { this.imagesMissingAltCount = imagesMissingAltCount; }
    public int getWordCount() { return wordCount; }
    public void setWordCount(int wordCount) { this.wordCount = wordCount; }
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}
