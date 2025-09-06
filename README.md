# Spring Backend API

This repository contains the Spring Boot backend API for the project. You can run it locally using Docker and Docker Compose.

## Prerequisites

- [Docker](https://www.docker.com/get-started) installed
- [Docker Compose](https://docs.docker.com/compose/install/) installed

## Config

This project uses a `.env` file for configuration. A template is provided as `.env.template`. You should copy it to `.env` and fill in your values

```bash
cp .env.template .env
```
>**Note:** Datasource username and password can be left as recipe_user, recipe_password for development. Todoist auth not currently implemented.
## Running the Backend

1. Clone the repository:l
```
git clone <repo-url>
cd <repo-folder>
```
2. Build and start the backend using Docker Compose
```
docker-compose up --build
```
3. Once started, API will be available at 
```
http://localhost:8080
```

