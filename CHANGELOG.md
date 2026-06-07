# Changelog

## v1.0.0

### Authentication

* Implemented JWT-based authentication using Spring Security
* Added user registration endpoint
* Added user login endpoint
* Secured protected APIs using JWT filters

### Meeting Management

* Implemented meeting creation and retrieval APIs
* Added transcript storage and association with meetings
* Added pagination support for meeting listings

### AI Analysis

* Integrated Groq API for meeting analysis
* Added meeting summary generation
* Added action item extraction
* Added decision extraction
* Added follow-up suggestion generation
* Implemented analysis caching using the database

### Action Item Management

* Added action item creation
* Added action item status updates
* Added overdue action item detection

### Reminder System

* Implemented scheduled reminder job
* Added reminder history tracking
* Integrated Discord Webhooks for reminder notifications
* Added duplicate reminder prevention logic

### Documentation

* Added Swagger/OpenAPI documentation
* Added README
* Added DECISIONS document
* Added AI_APPROACH document
* Added TESTING document

### Testing

* Added unit tests for:

    * ActionItemService
    * JwtService
    * AuthService
    * AnalysisService

### Deployment

* Added Docker support
* Added environment variable configuration support
