package com.example.studentmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.repository.StudentRepository;

@Service
public class StudentService {

    @Autowired
    private StudentRepository repo;

    // SAVE or UPDATE
    public void saveStudent(Student student) {
        repo.save(student);
    }

    // READ ALL
    public List<Student> getAllStudents() {
        return repo.findAll();
    }

    // CUSTOM QUERY - Department
    public List<Student> getStudentsByDepartment(String dept) {
        return repo.findByDepartment(dept);
    }

    // CUSTOM QUERY - Age
    public List<Student> getStudentsByAge(int age) {
        return repo.findByAgeGreaterThan(age);
    }

    // PAGINATION + SORTING
    public Page<Student> getStudentsWithPagination(int pageNo, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return repo.findAll(pageable);
    }

    // DELETE
    public void deleteStudent(Long id) {
        repo.deleteById(id);
    }
}