package com.example.courseservice.controller;

import com.example.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService service;

    /**
     * Fetch all students via Course Service.
     * URL: GET http://localhost:8082/courses/students
     *
     * Case 1 - Student Service UP   → returns student list JSON
     * Case 2 - Student Service DOWN → returns fallback message
     */
    @GetMapping("/students")
    public Object fetchStudents() {
        return service.getStudentData();
    }

    /**
     * Fetch a single student by ID via Course Service.
     * URL: GET http://localhost:8082/courses/students/1
     */
    @GetMapping("/students/{id}")
    public Object fetchStudentById(@PathVariable Long id) {
        return service.getStudentById(id);
    }
}
