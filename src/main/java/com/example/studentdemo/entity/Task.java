package com.example.studentdemo.entity;




import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data                 // Generates getters, setters, toString, equals, hashCode
@NoArgsConstructor     // Generates no-arg constructor
@AllArgsConstructor    // Generates all-args constructor
@Builder              // Optional: allows builder pattern
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING; // default value

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }
}
