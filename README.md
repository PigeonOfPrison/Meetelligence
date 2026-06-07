# MEETELLIGENCE

## Overview
Meetelligence is a SpringBoot backend application for managing meetings, tracking action-items and 
generating ai-powered meeting analysis from their transcripts.

The goal of this project was to create a self-sufficient meeting processing microservice that can be easily integrated
in any given backend application, since meeting transcripts and action-item recording is the norm.

The system allows users to create meetings, store transcripts, generate AI summaries and action items, 
track overdue tasks, and receive reminder notifications through Discord.


## Features

1. **Meeting Management :**
    - Create, Update and retrieve Meetings
    - Store meeting participants
    - Attach transcript segments to the meeting


2. **AI Meeting Analysis :**
    - generate meeting summaries using AI (Groq in this case)
    - extract action items and key decisions from transcripts
    - Cache meeting analysis to avoid redundant API calls and waste of tokens
   

3. **Action-Item Tracking :**
    - Create and update action items
    - Mark action items as : PENDING, IN_PROGRESS or COMPLETE
    - Detect overdue tasks
   

4. **Reminder System :**
    - Scheduled jobs for overdue action Items
    - Sends notifications using Discord webhooks
    - Prevent duplicate reminders within configurable time window


5. **Authentication :**
   - JWT based authentication
   - User registration and login
   - Protected endpoints using Spring Security
   - Unprotected endpoints for health, asd swagger-ui


6. **API Documentation :**
    - Swagger/OpenAPI  integration
    - Interactive endpoint testing using Swagger-ui

## Tech-Stack Used:

- JDK 21
- SpringBoot 4.0.6
- PostgreSQL 17
- JUnit 5
- Mockito


## Architecture:

The application follows a standard layer architecture as follows : 

` Controller -> Service -> Repository -> Database`

Business logic is handled in the service classes while repositories handle persistence.
The controller layer... is just there. I thought about merging the controller and the service layer,
but this way is much cleaner overall.

## Running the project

### Method 1:

1. Clone the repository
2. Configure application.properties

Values required in application.properties : 

- Database URL
- Database username/password
- JWT secret
- JWT expiration
- Groq API key
- Discord webhook URL

You can also create and use system-variables instead for more security

3. Run the application. It will start on http://localhost:8080

# Running the Project

## Method 1: Local Setup

1. Clone the repository.

2. Configure `application.properties`.

Required values:

* Database URL
* Database username/password
* JWT secret
* JWT expiration
* Groq API key
* Discord webhook URL

You may also use environment variables instead of storing sensitive values directly in the configuration file.

3. Run the application.

The API will start on:

```text
http://localhost:8080
```

---

## Method 2: Docker

### Build the application

```bash
mvn clean package
```

### Build the Docker image

```bash
docker build -t meetelligence .
```

### Run the container

```bash
docker run -p 8080:8080 \
-e DB_URL=<database-url> \
-e DB_USERNAME=<username> \
-e DB_PASSWORD=<password> \
-e JWT_SECRET=<secret> \
-e GROQ_API_KEY=<groq-api-key> \
-e DISCORD_WEBHOOK_URL=<webhook-url> \
meetelligence
```

The API will be available at:

```text
http://localhost:8080
```


## Swagger

Swagger UI will be available at : http://localhost:8080/swagger-ui/index.html

Swagger ui can be used to test all the endpoints directly from the browser

For protected endpoints:

1. Register a user
2. Login
3. Copy the returned JWT 
4. Click "Authorize" in Swagger 
5. Paste the token


## Testing

The project also contains a number of unit tests in the test folder. 
Testing is primarily done for the important services using JUnit and Mockito :

- ActionItemService
- JwtService
- AuthService
- AnalysisService

Tests can be executed using mvn test


## Future Plans

A few improvement that I have currently planned for the future versions are :

- Introduction of request DTOs for greater api security
- Option to refresh tokens
- Improve more upon the AI prompt engineering
- Extract the action-items from the AI response (requires better prompts)
- Add role based authorizations

## Further Notes:

As this was supposed to be the MVP, the overall focus was more on implementing the core functionalities
as well as clean and maintainable code. This factor will be quite visible in the design decisions.

