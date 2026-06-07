# Design Decisions

## Database Choice

### Decision

I chose PostgreSQL as the primary database for this project.

### Alternatives Considered

* MySQL
* MongoDB
* SQLite

### Reasoning

The data model contains clear relationships between meetings, transcripts, action items, reminder history, and analysis results. A relational database felt like the most natural fit.

PostgreSQL is mature, widely used, and works well with Spring Data JPA.

### Trade-offs

A document database such as MongoDB may provide more flexibility for storing AI-generated content, but would require additional work to model relationships and maintain consistency.

---

## Authentication Strategy

### Decision

I implemented JWT-based authentication using Spring Security.

### Alternatives Considered

* Session-based authentication
* OAuth providers

### Reasoning

JWT authentication is stateless and easy to integrate with REST APIs. It also scales well because the server does not need to maintain session state.

### Trade-offs

JWT introduces token management complexity and requires proper handling of expiration and validation.

---

## AI Provider Selection

### Decision

I used the Groq API for meeting analysis.

### Alternatives Considered

* OpenAI
* Gemini
* Claude
* OpenRouter

### Reasoning

Groq provides an OpenAI-compatible API and was straightforward to integrate into a Spring Boot application. It also offers fast response times and a generous free tier for experimentation.

### Trade-offs

AI responses can still vary depending on prompt quality. Additional prompt engineering and output validation would be beneficial in a production environment.

---

## Analysis Caching Strategy

### Decision

Generated meeting analyses are stored in the database after the first request.

### Alternatives Considered

* Regenerate analysis on every request
* Use an external cache such as Redis

### Reasoning

Meeting transcripts are not expected to change frequently. Storing generated analyses avoids repeated API calls, reduces cost, and improves response times.

### Trade-offs

If transcripts are modified after analysis generation, the stored analysis may become outdated unless a refresh mechanism is implemented.

---

## Reminder Integration

### Decision

I chose Discord Webhooks for reminder delivery.

### Alternatives Considered

* Telegram Bot API
* Slack Webhooks
* Email providers

### Reasoning

Discord webhooks require minimal setup and can receive notifications without managing bot commands or additional infrastructure.

### Trade-offs

Discord is suitable for demonstration purposes, but production systems would likely support multiple notification channels and user-specific preferences.

---

## Project Structure

### Decision

The project follows a layered architecture:

Controller → Service → Repository → Database

### Reasoning

This structure keeps responsibilities separated and makes the application easier to maintain and test.

Business logic is concentrated inside service classes while repositories handle persistence concerns.

### Trade-offs

Additional abstraction layers can introduce more files and boilerplate, but the separation becomes valuable as the project grows.

---

## Testing Strategy

### Decision

Unit tests were written for the most important business logic components.

### Covered Areas

* Authentication
* JWT token generation and validation
* Action item management
* Meeting analysis workflow

### Reasoning

The focus was on testing business logic and service behavior while keeping the test suite lightweight and easy to maintain.

### Trade-offs

Integration testing was not implemented due to time constraints and project scope.
