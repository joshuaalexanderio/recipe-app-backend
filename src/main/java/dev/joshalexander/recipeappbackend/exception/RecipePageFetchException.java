package dev.joshalexander.recipeappbackend.exception;

public class RecipePageFetchException extends RuntimeException {

  public enum Reason {
    BLOCKED,       // site returned 403/401/429
    EMPTY,         // fetch succeeded but body was blank
    NETWORK_ERROR  // connection failure, timeout, etc.
  }

  private final Reason reason;
  private final String url;

  public RecipePageFetchException(Reason reason, String url, String message) {
    super(message);
    this.reason = reason;
    this.url = url;
  }

  public RecipePageFetchException(Reason reason, String url, String message, Throwable cause) {
    super(message, cause);
    this.reason = reason;
    this.url = url;
  }

  public Reason getReason() {
    return reason;
  }

  public String getUrl() {
    return url;
  }
}