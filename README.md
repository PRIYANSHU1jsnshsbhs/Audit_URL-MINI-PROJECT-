# Page Pulse - Hacker Terminal Edition 💻
*(Audit URL Mini-Project for Digital Heroes)*

A full-stack web application that performs basic SEO and content audits on any given URL. This project features a robust Spring Boot backend for HTML parsing and a custom, interactive retro "Hacker Terminal" React frontend.

## 🚀 Features
- **Retro Terminal Interface**: A fully interactive, auto-scrolling terminal UI with an ASCII art header, built with React and Tailwind CSS.
- **Interactive Commands**: Supports terminal commands like `help`, `clear`, and `audit <url>`.
- **Live HTML Auditing**: Securely fetches and parses live websites to extract critical metrics.
- **Audit Metrics Provided**:
  - HTTP Status & Response Time
  - Page Title & Meta Description
  - Total `<h1>` Tag Count
  - Images missing `alt` attributes
  - Approximate Word Count

## 🛠️ Tech Stack
**Frontend:**
- React (via Vite)
- Tailwind CSS

**Backend:**
- Java 17
- Spring Boot 3.x
- Maven
- Jsoup (for HTML DOM parsing)

---

## 💻 Local Development Setup

### Prerequisites
- Node.js (v18+)
- Java JDK (17+)
- Maven

### 1. Start the Backend
The backend runs on `http://localhost:8081` by default.
```bash
cd SDE
mvn spring-boot:run
```

### 2. Start the Frontend
The frontend runs on `http://localhost:5173` by default.
```bash
cd frontend
npm install
npm run dev
```
Open your browser and navigate to `http://localhost:5173` to interact with the terminal!

---

## 🌍 Deployment

### Backend (Render)
This repository includes a `Dockerfile` in the `SDE` folder. It is designed to be easily deployed on [Render.com](https://render.com) as a Web Service.
- **Environment**: Docker
- **Root Directory**: `SDE`

### Frontend (Vercel)
The frontend is optimized for deployment on [Vercel.com](https://vercel.com).
- Ensure you set the `VITE_API_URL` environment variable in Vercel to point to your live Render backend URL so the terminal knows where to send audit requests!

---
*Built for the Digital Heroes SDE Training Task.*
