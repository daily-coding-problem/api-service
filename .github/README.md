# API Service [![Java CI with Maven](https://github.com/daily-coding-problem/api-service/actions/workflows/maven.yml/badge.svg)](https://github.com/daily-coding-problem/api-service/actions/workflows/maven.yml)

![Docker](https://img.shields.io/badge/-Docker-2496ED?style=flat-square&logo=Docker&logoColor=white)
![Linux](https://img.shields.io/badge/-Linux-FCC624?style=flat-square&logo=linux&logoColor=black)
![Java](https://img.shields.io/badge/-Java-007396?style=flat-square&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/-Spring-6DB33F?style=flat-square&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/-PostgreSQL-336791?style=flat-square&logo=postgresql&logoColor=white)
![Supabase](https://img.shields.io/badge/-Supabase-3ECF8E?style=flat-square&logo=supabase&logoColor=white)

API Service a modular service that obtains LeetCode problems and study plans from the database.

## Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [License](#license)

## Features

- **API Endpoints**: Provides endpoints to obtain LeetCode problems and study plans.
- **Database Integration**: Connects to a PostgreSQL database to obtain data.
- **Docker Support**: Can be run in a Docker container.
- **Logging**: Logs information to the console.
- **Testing**: Includes unit tests for the service.
- **GitHub Actions**: Includes a workflow to test the service.
- **GDPR Compliance**: Ensures data protection and privacy for all individuals.
- **Security**: Protects against security threats and vulnerabilities.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Docker and Docker Compose installed on your machine.
- Java 22 or higher installed on your machine.
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

### Test the GitHub Actions via _Docker_

To test the GitHub Actions via _Docker_, execute the following:

```sh
docker compose down -v && docker compose up --build db github-action-maven-test
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
