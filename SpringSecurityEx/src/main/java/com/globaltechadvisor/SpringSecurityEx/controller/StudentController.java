package com.globaltechadvisor.SpringSecurityEx.controller;


import com.globaltechadvisor.SpringSecurityEx.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    //Just for testing It
    @GetMapping("hello")
    public String greet(HttpServletRequest request) {
        return "Hello World "+request.getSession().getId();
    }

    @GetMapping("about")
    public String about(HttpServletRequest request) {
        return "Atanu "+request.getSession().getId();
    }

    List<Student> students=new ArrayList<>(List.of(
            new Student(1,"Atanu",".Net-Angular"),
            new Student(2,"Seb","Angular")
    ));

    // will get this token and use this to pass in header: 'X-CSRF-TOKEN' for doing operations other than get
    @GetMapping("csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request) {
        return (CsrfToken) request.getAttribute("_csrf");
    }


    @GetMapping("students")
    public List<Student> getStudents(){
        return students;
    }
    @PostMapping("students")
    public void addStudent(@RequestBody Student student) {
        students.add(student);
    }
}
