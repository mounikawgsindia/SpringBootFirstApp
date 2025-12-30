package com.example.studentdemo.service;

import com.example.studentdemo.entity.Student;
import com.example.studentdemo.exception.DataAlreadyExistsException;
import com.example.studentdemo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    public Student createStudent(Student student) {
        // Check if student with same name already exists
        Optional<Student> existingStudent = studentRepository.findByName(student.getName());
        if (existingStudent.isPresent()) {
            throw new DataAlreadyExistsException("Student with name '" + student.getName() + "' already exists");
        }
        return studentRepository.save(student);
    }
    
    public Student updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        // Check if another student with the same name exists (excluding current student)
        Optional<Student> existingStudent = studentRepository.findByName(studentDetails.getName());
        if (existingStudent.isPresent() && !existingStudent.get().getId().equals(id)) {
            throw new DataAlreadyExistsException("Student with name '" + studentDetails.getName() + "' already exists");
        }
        
        student.setName(studentDetails.getName());
        if (studentDetails.getSports() != null) {
            student.setSports(studentDetails.getSports());
        }
        return studentRepository.save(student);
    }
    
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        studentRepository.delete(student);
    }
    
    public Student addSportToStudent(Long studentId, String sportName) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        student.getSports().add(sportName);
        return studentRepository.save(student);
    }
    
    public Student removeSportFromStudent(Long studentId, String sportName) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + studentId));
        
        student.getSports().remove(sportName);
        return studentRepository.save(student);
    }
}
