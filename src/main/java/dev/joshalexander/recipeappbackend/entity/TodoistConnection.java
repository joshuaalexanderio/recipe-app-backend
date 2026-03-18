package dev.joshalexander.recipeappbackend.entity;

import jakarta.persistence.*;

import java.time.Instant;

/**
 * Stores each user's Todoist OAuth access token.
 * Todoist tokens don't expire automatically, but we track
 * created/updated timestamps for auditing & potential re-auth.
 */
@Entity
@Table(name = "todoist_connections")
public class TodoistConnection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * FK to your User entity.
     * Adjust the relationship type if you want a @OneToOne to User.
     */
    @Column(nullable = false, unique = true)
    private Long userId;

    /**
     * The Bearer token returned by Todoist after OAuth.
     */
    @Column(nullable = false)
    private String accessToken;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant updatedAt;

    // ── lifecycle ────────────────────────────────────────────────────

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    void onUpdate() {
        updatedAt = Instant.now();
    }

    // ── getters / setters ────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}