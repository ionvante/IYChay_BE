package com.iychay.be.student.controller;

import com.iychay.be.student.dto.StudentDetailResponse;
import com.iychay.be.student.dto.StudentRequest;
import com.iychay.be.student.dto.StudentResponse;
import com.iychay.be.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alumnos")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Page<StudentResponse>> list(@RequestParam(required = false) String search,
                                                       @RequestParam(required = false) Long aulaId,
                                                       Pageable pageable) {
        return ResponseEntity.ok(studentService.search(search, aulaId, pageable));
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(studentService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> update(@PathVariable Long id, @Valid @RequestBody StudentRequest request) {
        return ResponseEntity.ok(studentService.update(id, request));
    }

    @GetMapping("/{id}/ficha")
    public ResponseEntity<StudentDetailResponse> detail(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getDetail(id));
    }
}
