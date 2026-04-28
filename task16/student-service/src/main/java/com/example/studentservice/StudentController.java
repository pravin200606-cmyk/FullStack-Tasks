package com.example.studentservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Value("${server.port}")
    private String port;

    @GetMapping
    public String getStudents() {
        return "Response from Student Service - Instance running on port: " + port;
    }
}
