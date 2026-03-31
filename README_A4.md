# Assignment 4 – EventHub AI API

## Overview

This project extends the EventHub API by integrating AI-powered features using Google's Gemini models. The API provides intelligent content generation, event planning assistance, and semantic FAQ search using embeddings and retrieval-augmented generation (RAG).

---

## Features

### AI Chat

* Generate general-purpose responses based on a user prompt
* Endpoint: `POST /api/ai/chat`

### Event Description Generation

* Generates marketing-quality event descriptions
* Endpoint: `POST /api/ai/event-description`

### Structured Event Description

* Returns structured event data including title, highlights, and audience
* Endpoint: `POST /api/ai/event-description/structured`

### Event Tag Suggestions

* Generates relevant tags for an event
* Endpoint: `POST /api/ai/event-tags`

### Event Schedule Generation

* Creates a multi-session agenda for events
* Endpoint: `POST /api/ai/event-schedule`

---

## FAQ System (RAG)

### Load FAQ Data

* Loads predefined FAQ entries into the vector store
* Endpoint: `POST /api/ai/faq/load`

### Semantic Search

* Finds relevant FAQ entries using embeddings
* Endpoint: `POST /api/ai/faq/search`

### AI-Powered FAQ Answers

* Uses retrieved FAQ entries to generate answers
* Endpoint: `POST /api/ai/faq/ask`

---

## Health Check

* Verifies AI service availability
* Endpoint: `GET /api/ai/health`

---

## AI Models Used

* **Chat / Generation Model:** `gemini-2.5-flash-lite`
* **Embedding Model:** `gemini-embedding-001`

---

## Setup Instructions

### 1. Set Environment Variable

You must provide a Gemini API key using an environment variable:

#### Windows (PowerShell)

```powershell
setx GEMINI_API_KEY "your_api_key_here"
```

#### Mac/Linux

```bash
export GEMINI_API_KEY="your_api_key_here"
```

Restart the application after setting the variable.

---

### 2. Run the Application

```bash
mvn spring-boot:run
```

---

### 3. Access API

* Base URL: `http://localhost:8080`
* Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## Testing

### Swagger

All endpoints can be tested using Swagger UI.

### Postman

A Postman collection is included:

* `Assignment 4 - eventhub-api.postman_collection.json`

---

## Notes

* If the API key is missing or quota is exceeded, the AI endpoints will return fallback responses.
* The FAQ system uses vector embeddings for semantic similarity matching.

---

## Author

Cantor
SAIT – Software Development
