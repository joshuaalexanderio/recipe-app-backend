
# Recipe App Backend API

A Spring Boot REST API for managing recipes with ingredients. Supports creating recipes, managing users, and future integration with Todoist for shopping lists.

## Prerequisites
- [Docker](https://www.docker.com/get-started) installed

## Config
This project uses a `.env` file for configuration. A template is provided as `.env.template`. You should copy it to `.env` and fill in your values
```bash
cp .env.template .env
```
>**Note:** Datasource username and password can be left as recipe_user, recipe_password for development. Todoist auth not currently implemented.

## Running the Backend
1. Clone the repository:
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

## API Endpoints
### Base URL
http://localhost:8080/api

### Recipes
- `GET /recipes` - List all recipes
- `GET /recipes/{id}` - Get recipe by ID  
- `POST /recipes` - Create new recipe

### Ingredients
- `GET /ingredients` - List all ingredients
- `GET /ingredients/{id}` - Get ingredient by ID

### Users
- `GET /users` - List all users
- `GET /users/{id}` - Get user by ID

### Documentation
- `GET /` - API root with navigation links
- `GET /docs` - API documentation with examples

## Example Usage
```bash
# Create a recipe
curl -X POST http://localhost:8080/api/recipes \
  -H "Content-Type: application/json" \
  -u user:password \
  -d '{
    "name": "Simple Pasta",
    "description": "Basic pasta with marinara sauce",
    "user": {"id": 1},
    "ingredients": [
      {"name": "pasta", "quantity": "1", "unit": "lb", "orderIndex": 1},
      {"name": "marinara sauce", "quantity": "2", "unit": "cups", "orderIndex": 2},
      {"name": "parmesan cheese", "quantity": "1/2", "unit": "cup", "orderIndex": 3}
    ]
  }'
```
