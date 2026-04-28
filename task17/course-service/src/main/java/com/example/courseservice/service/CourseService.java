package com.example.courseservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CourseService {

    @Autowired
    private RestTemplate restTemplate;

    // Student Service base URL (running on port 8081)
    private final String STUDENT_URL = "http://localhost:8081/students";

    /**
     * Fetch all students from Student Service.
     * Circuit Breaker: if studentService keeps failing → opens circuit → calls fallbackMethod.
     */
    @CircuitBreaker(name = "studentService", fallbackMethod = "fallbackMethod")
    public Object getStudentData() {
        return restTemplate.getForObject(STUDENT_URL, Object.class);
    }

    /**
     * Fetch student by ID from Student Service.
     */
    @CircuitBreaker(name = "studentService", fallbackMethod = "fallbackMethodWithId")
    public Object getStudentById(Long id) {
        return restTemplate.getForObject(STUDENT_URL + "/" + id, Object.class);
    }

    // ── Fallback Methods ──────────────────────────────────────────

    /**
     * Fallback when Student Service is DOWN or Circuit is OPEN.
     * Must have the same return type and accept Throwable as last param.
     */
    public Object fallbackMethod(Exception e) {
        return "Fallback: Student Service is currently DOWN! Error: " + e.getMessage();
    }

    public Object fallbackMethodWithId(Long id, Exception e) {
        return "Fallback: Could not fetch Student ID=" + id + ". Student Service is DOWN!";
    }
}
