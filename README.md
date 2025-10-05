# Recipe App Backend API

A Spring Boot REST API for managing recipes and ingredients, providing the backend for the [Recipe App Frontend](https://github.com/stopthink/recipe-app-frontend).

## Features

- Create and manage recipes with ingredients
- Auto-import recipes from URLs
- User authentication and management
- Favorite recipes
- RESTful API with comprehensive documentation
- Todoist integration for one-click shopping lists *(coming soon)*

## Tech Stack

- Java 17+ / Spring Boot
- PostgreSQL (via Docker)
- Spring Data JPA
- Docker 

## Prerequisites

- [Docker](https://www.docker.com/get-started) installed

## Config

This project uses a `.env` file for configuration. A template is provided as `.env.template`. You should copy it to
`.env` and fill in your values

```bash
cp .env.template .env
```

> **Note:** Datasource username and password can be left as recipe_user, recipe_password for development. Todoist auth
> not currently implemented.

## Running the Backend

1. Clone the repository:

```
git clone <repo-url>
cd <repo-folder>
```

2. Build and start the backend using Docker Compose

```
docker compose up --build
```

3. Once started, API will be available at

```
http://localhost:8080
```

## Restarting the Backend

**For most code changes:**

```
docker compose up --build
```

**For configuration changes or issues:**

```
docker compose down && docker compose up --build
```

**For a clean restart with fresh database:**

```
docker compose down -v && docker compose up --build
```

## API Documentation

### Interactive Documentation
Full API documentation with interactive testing is available via SwaggerUI:

http://localhost:8080/docs

### Quick Overview
The API provides the following resources:
- **Recipes** (`/api/recipes`) - Create, read, update, and delete recipes with ingredients
- **Ingredients** (`/api/ingredients`) - Manage individual ingredients
- **Users** (`/api/users`) - User management
- **Recipe Ingredients** (`/api/recipeIngredients`) - Query ingredients for specific recipes

### Base URL
http://localhost:8080/api

Run the application and visit `/docs` for complete endpoint details, request/response schemas, and the ability to test all endpoints directly from your browser.

## Update Notes

- PUT requests support partial updates - only include fields you want to change
- If you include `ingredients` in a PUT request, it will **replace the entire ingredients list**
- To preserve existing ingredients, omit the `ingredients` field from your request
- User and creation timestamp are never modified through PUT requests
