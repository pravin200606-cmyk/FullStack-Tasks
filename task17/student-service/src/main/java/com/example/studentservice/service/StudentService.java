package com.example.studentservice.service;

import com.example.studentservice.entity.Student;
import com.example.studentservice.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    // Get all students
    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    // Get student by ID
    public Optional<Student> getStudentById(Long id) {
        return repo.findById(id);
    }

    // Save a new student
    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    // Delete student by ID
    public void deleteStudent(Long id) {
        repo.deleteById(id);
    }
}
