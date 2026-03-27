package com.example.studentmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.studentmanagement.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    // Custom Query 1: Find by Department
    List<Student> findByDepartment(String department);

    // Custom Query 2: Find students with age greater than given value
    List<Student> findByAgeGreaterThan(int age);
}