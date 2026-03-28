package dev.joshalexander.recipeappbackend.exception;

public class RecipeNotFoundException extends RuntimeException {

  public RecipeNotFoundException(String message) {
    super(message);
  }
}