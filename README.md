# Recipe App Backend API

A Spring Boot REST API for managing recipes with ingredients. Supports creating recipes, managing users, and future
integration with Todoist for shopping lists.

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

## API Endpoints

### Base URL

http://localhost:8080/api

### Recipes

- `GET /recipes` - List all recipes
- `GET /recipes/{id}` - Get recipe by ID
- `POST /recipes` - Create new recipe
- `PUT /recipes/{id}` - Update recipe (partial updates supported)

### Ingredients

- `GET /ingredients` - List all ingredients
- `GET /ingredients/{id}` - Get ingredient by ID
- `POST /ingredients` - Create ingredient
- `PUT /ingredients/{id}` - Update ingredient
- `DELETE /ingredients` - Delete ingredient

### Users

- `GET /users` - List all users
- `GET /users/{id}` - Get user by ID

### Documentation

- `GET /` - API root with navigation links
- `GET /docs` - API documentation with examples

## Example Usage

### Create a Recipe

```bash
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
      {"name": "parmesan cheese", "quantity": "1/2", "unit": "cup", "orderIndex": 3},
    ]
  }'
```

### Update a Recipe

```bash
# Update only the recipe name (ingredients preserved)
curl -X PUT http://localhost:8080/api/recipes/1 \
  -H "Content-Type: application/json" \
  -u user:password \
  -d '{
    "name": "Updated Recipe Name"
  }'

# Update name and description
curl -X PUT http://localhost:8080/api/recipes/1 \
  -H "Content-Type: application/json" \
  -u user:password \
  -d '{
    "name": "New Recipe Name",
    "description": "Updated description",
    "favorite": true
  }'

# Mark recipe as favorite
curl -X PUT http://localhost:8080/api/recipes/1 \
  -H "Content-Type: application/json" \
  -u user:password \
  -d '{
    "favorite": true
  }'

# Replace entire ingredients list
curl -X PUT http://localhost:8080/api/recipes/1 \
  -H "Content-Type: application/json" \
  -u user:password \
  -d '{
    "ingredients": [
      {"id": 1, "name": "large egg", "quantity": "5", "unit": null, "orderIndex": 1},
      {"name": "cheese", "quantity": "2", "unit": "slices", "orderIndex": 2}
    ]
  }'
```
### Create an ingredient

```bash
curl -X POST http://localhost:8080/api/ingredients \
  -H "Content-Type: application/json" \
  -u user:password \
  -d '{
    "name": "olive oil",
    "quantity": "1",
    "defaultUnit": "tbsp"
  }'
```
### Update an ingredient
```bash
curl -X PUT http://localhost:8080/api/ingredients/9 \
    -H "Content-Type: application/json" \
    -u user:password \
    -d '{
    "name": "extra virgin olive oil",
    "quantity": "3",
    }'
```
    

## Update Notes

- PUT requests support partial updates - only include fields you want to change
- If you include `ingredients` in a PUT request, it will **replace the entire ingredients list**
- To preserve existing ingredients, omit the `ingredients` field from your request
- User and creation timestamp are never modified through PUT requests
