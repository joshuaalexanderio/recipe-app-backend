package dev.joshalexander.recipeappbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String todoistUserId;

    @Column(nullable=false)
    private String todoistAccessToken;

    @Column(length=100)
    @Size(min = 1, max = 100, message="Email must be between 1-100 characters")
    private String name;

    @Column(length=254)
    @Size(max=254, message="Email cannot exceed 254 characters")
    private String email;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
