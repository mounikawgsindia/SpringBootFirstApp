package com.example.studentdemo.controller;

import com.example.studentdemo.entity.Student;
import com.example.studentdemo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
// @RestController takes http request return json response
@RestController

// @RequestMapping its act as prefix("api/students") for Student controller
@RequestMapping("/api/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    // GET - Get all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
    
    // GET - Get student by id
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // POST - Create a new student
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }
    
    // PUT - Update an existing student
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            return ResponseEntity.ok(updatedStudent);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // DELETE - Delete a student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // POST - Add a sport to a student
    @PostMapping("/{studentId}/sports/{sportName}")
    public ResponseEntity<?> addSportToStudent(@PathVariable Long studentId, @PathVariable String sportName) {
        try {
            Student student = studentService.addSportToStudent(studentId, sportName);
            return ResponseEntity.ok(student);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    // DELETE - Remove a sport from a student
    @DeleteMapping("/{studentId}/sports/{sportName}")
    public ResponseEntity<?> removeSportFromStudent(@PathVariable Long studentId, @PathVariable String sportName) {
        try {
            Student student = studentService.removeSportFromStudent(studentId, sportName);
            return ResponseEntity.ok(student);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
