# API Service [![Java CI with Maven](https://github.com/daily-coding-problem/api-service/actions/workflows/maven.yml/badge.svg)](https://github.com/daily-coding-problem/api-service/actions/workflows/maven.yml)

![Docker](https://img.shields.io/badge/-Docker-2496ED?style=flat-square&logo=Docker&logoColor=white)
![Linux](https://img.shields.io/badge/-Linux-FCC624?style=flat-square&logo=linux&logoColor=black)
![Java](https://img.shields.io/badge/-Java-007396?style=flat-square&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/-Spring-6DB33F?style=flat-square&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-336791?style=flat-square&logo=postgresql&logoColor=white)

API Service a modular service that obtains LeetCode problems and study plans from the database.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [License](#license)

## Features

- **API Endpoints**: Provides endpoints to obtain LeetCode problems and study plans.
- **Database Integration**: Connects to a PostgreSQL database to obtain data.
- **Docker Support**: Can be run in a Docker container.
- **Logging**: Logs information to the console.
- **Testing**: Includes unit tests for the service.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Docker and Docker Compose installed on your machine.
- Java 17 or higher installed on your machine.
- Maven installed on your machine.
- [PostgreSQL database](https://github.com/daily-coding-problem/database).

## Installation

**Clone the Repository**

```sh
git clone https://github.com/daily-coding-problem/api-service.git
cd api-service
```

**Install Dependencies**

```sh
mvn -ntp dependency:go-offline
```

**Setup Docker**

If you would like to use Docker, ensure Docker and Docker Compose are installed on your machine. If not, follow the installation guides for [Docker](https://docs.docker.com/get-docker/) and [Docker Compose](https://docs.docker.com/compose/install/).

**Build Docker Images**

```sh
docker compose build api-service
```

**Create the Network**

```sh
docker network create dcp
```

## Configuration

**Environment Variables**

Create a `.env` file in the project root with the content found in the [`.env.example`](/.env.example) file.

## Usage

Start the service with Docker:

```sh
docker compose up -d api-service && docker compose logs -f api-service
```

Or without Docker:

```sh
mvn spring-boot:run
```

## Running Tests

Run the tests with the following command:

```sh
mvn test -Dspring.profiles.active=test
```

## Project Structure

This project follows a standard Maven-based structure for a Spring Boot application. Below is an overview of the project's directory structure and a brief description of each directory's purpose:

```
api-service
├── .github/
│   └── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── dcp
│   │   │           └── api_service
│   │   │               ├── ApiServiceApplication.java
│   │   │               └── v1
│   │   │                   ├── controller
│   │   │                   │   ├── ProblemController.java
│   │   │                   │   └── StudyPlanController.java
│   │   │                   ├── entity
│   │   │                   │   ├── Problem.java
│   │   │                   │   ├── StudyPlan.java
│   │   │                   │   ├── StudyPlanProblem.java
│   │   │                   │   └── StudyPlanProblemId.java
│   │   │                   ├── repository
│   │   │                   │   ├── ProblemRepository.java
│   │   │                   │   ├── StudyPlanProblemRepository.java
│   │   │                   │   └── StudyPlanRepository.java
│   │   │                   └── service
│   │   │                       ├── ProblemService.java
│   │   │                       └── StudyPlanService.java
│   │   └── resources
│   │       ├── application.yml
│   ├── test
│   │   ├── java
│   │   │   └── com
│   │   │       └── dcp
│   │   │           └── api_service
│   │   │               └── v1
│   │   │                   ├── controller
│   │   │                   │   ├── ProblemControllerTest.java
│   │   │                   │   └── StudyPlanControllerTest.java
│   │   │                   ├── service
│   │   │                   │   ├── ProblemServiceTest.java
│   │   │                   │   └── StudyPlanServiceTest.java
│   └── Dockerfile
├── .gitignore
├── pom.xml
├── compose.yml
├── Dockerfile
├── .env
```

### Directory and File Descriptions

- **src/main/java**: Contains the main Java source files.
	- **com.dcp.api_service**: The base package for the project.
      - **v1**: Contains the version 1 of the API.
          - **ApiServiceApplication.java**: The entry point of the Spring Boot application.
          - **controller**: Contains the REST controllers that handle HTTP requests.
              - **ProblemController.java**: Manages requests related to problems.
              - **StudyPlanController.java**: Manages requests related to study plans.
          - **entity**: Contains the JPA entities that represent the database tables.
              - **Problem.java**: Entity representing a problem.
              - **StudyPlan.java**: Entity representing a study plan.
              - **StudyPlanProblem.java**: Entity representing the relationship between study plans and problems.
              - **StudyPlanProblemId.java**: Composite key for `StudyPlanProblem`.
          - **repository**: Contains the Spring Data JPA repositories for database operations.
              - **ProblemRepository.java**: Repository interface for problems.
              - **StudyPlanProblemRepository.java**: Repository interface for study plan problems.
              - **StudyPlanRepository.java**: Repository interface for study plans.
          - **service**: Contains the service classes that contain business logic.
              - **ProblemService.java**: Service class for managing problems.
              - **StudyPlanService.java**: Service class for managing study plans.

- **src/main/resources**: Contains the application configuration and other resource files.
	- **application.yml**: Configuration file for Spring Boot application.

- **src/test/java**: Contains the test Java source files.
	- **com.dcp.api_service**: The base test package for the project.
      - **v1**: Contains the version 1 of the API.
          - **ApiServiceApplicationTests.java**: Test class to verify that the application context loads correctly.
          - **controller**: Contains the test classes for REST controllers.
              - **ProblemControllerTest.java**: Unit tests for `ProblemController`.
              - **StudyPlanControllerTest.java**: Unit tests for `StudyPlanController`.
          - **service**: Contains the test classes for service classes.
              - **ProblemServiceTest.java**: Unit tests for `ProblemService`.
              - **StudyPlanServiceTest.java**: Unit tests for `StudyPlanService`.

- **.github/README.md**: The README file for the GitHub repository.

- **compose.yaml**: Docker Compose file for running the Spring Boot application.

- **Dockerfile**: Dockerfile for containerizing the Spring Boot application.

- **.gitignore**: Specifies the files and directories that should be ignored by Git.

- **pom.xml**: Maven project configuration file.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
