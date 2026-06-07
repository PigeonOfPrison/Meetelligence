# Testing

## Overview

Testing was performed using a combination of automated unit tests and manual API testing through Swagger and Postman.

The primary goal was to verify core business logic, authentication flows, AI analysis workflows, and reminder functionality.

---

## Unit Tests

### ActionItemServiceTest

Scenarios covered:

* Retrieving overdue action items
* Updating action item status
* Handling updates for non-existent action items

---

### JwtServiceTest

Scenarios covered:

* JWT generation
* Username extraction from token
* Token validation
* Invalid user validation
* Claim extraction

---

### AuthServiceTest

Scenarios covered:

* User registration
* User login
* Password validation
* JWT generation after successful authentication

---

### AnalysisServiceTest

Scenarios covered:

* Returning cached analysis when available
* Generating new analysis when no cached result exists
* Saving generated analysis
* Preventing unnecessary AI API calls

---

## Manual Testing

### Authentication

Verified:

* User registration
* User login
* Access to protected endpoints
* Rejection of unauthorized requests

---

### Meeting Management

Verified:

* Meeting creation
* Meeting retrieval
* Pagination support
* Transcript storage

---

### AI Analysis

Verified:

* Transcript submission
* AI-generated analysis
* Database caching of generated analysis
* Reuse of cached analysis

---

### Action Item Management

Verified:

* Action item creation
* Status updates
* Overdue action item detection

---

### Reminder Workflow

Verified:

* Scheduled reminder execution
* Overdue action item detection
* Discord webhook integration
* Duplicate reminder prevention

---

### Swagger Documentation

Verified:

* Endpoint visibility
* Request/response schemas
* JWT authentication through Swagger UI

---

## Edge Cases Considered

* Invalid JWT tokens
* Missing authentication headers
* Invalid meeting IDs
* Invalid action item IDs
* Empty analysis cache
* Duplicate reminder attempts
* Missing transcript data

---

## Known Limitations

* Integration tests were not implemented.
* AI response quality depends on transcript quality and model behavior.
* Automated testing currently focuses on service-layer business logic rather than full end-to-end workflows.

---

## Running Tests

Execute all tests using:

```bash
mvn test
```
