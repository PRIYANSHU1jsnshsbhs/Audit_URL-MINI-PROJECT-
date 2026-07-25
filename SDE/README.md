# Page Pulse

A small web tool that audits any URL, providing metrics like HTTP status, response time, page title, meta description, H1 count, missing alt text on images, and an approximate word count.

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Node.js (v18+)
- npm or yarn

### Running the Backend (Spring Boot)
1. Open a terminal in the root directory.
2. Run the application using Gradle wrapper:
   ```bash
   ./gradlew bootRun
   ```
   (On Windows, use `gradlew.bat bootRun`)
3. The server will start on `http://localhost:8080`.

### Running the Frontend (React + Vite)
1. Open a new terminal and navigate to the `frontend` folder:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```
4. The frontend will be available (usually) at `http://localhost:5173`.

## API Contract

**Endpoint:** `GET /api/audit`

**Query Parameters:**
- `url` (string, required): The URL to audit (must start with `http://` or `https://`).

**Success Response (200 OK):**
```json
{
  "httpStatus": 200,
  "responseTimeMs": 145,
  "pageTitle": "Example Domain",
  "metaDescription": null,
  "h1Count": 1,
  "imagesMissingAltCount": 0,
  "wordCount": 24,
  "error": null
}
```

**Error Response (400 Bad Request):**
```json
{
  "httpStatus": 400,
  "responseTimeMs": 12,
  "pageTitle": null,
  "metaDescription": null,
  "h1Count": 0,
  "imagesMissingAltCount": 0,
  "wordCount": 0,
  "error": "Invalid URL format. Must start with http:// or https://"
}
```

## 3 Design Decisions & Reasoning

1. **Monorepo-Style Folder Structure for simplicity:**
   - *Reasoning:* Rather than having two separate git repositories for this small test project, keeping the `frontend` React app within the same directory as the Spring Boot backend allows for a single `git clone` and easier evaluation by reviewers.

2. **Using Jsoup for HTML Parsing on the Backend:**
   - *Reasoning:* Jsoup is incredibly robust and forgiving when parsing real-world, often malformed HTML. It also provides a very familiar jQuery-like selector syntax (`doc.select("img:not([alt])")`), making the code highly readable and concise compared to writing manual regex or using a generic XML parser.

3. **Separation of Concerns in the Backend (`Controller` vs `Service`):**
   - *Reasoning:* Even for a small task, keeping the HTTP validation and routing logic in `AuditController` and the actual fetching/parsing logic in `AuditService` ensures the code remains testable and scalable. It allows us to easily write unit tests for the `AuditService` logic without needing to mock the entire Spring Web MVC context.
