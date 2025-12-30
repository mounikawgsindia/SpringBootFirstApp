package com.example.studentdemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @ElementCollection
    @CollectionTable(name = "student_sports", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "sport_name")
    private Set<String> sports = new HashSet<>();
}
