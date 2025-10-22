package com.iychay.be.classroom.controller;

import com.iychay.be.classroom.dto.ClassroomResponse;
import com.iychay.be.classroom.dto.CreateClassroomRequest;
import com.iychay.be.classroom.dto.UpdateClassroomRequest;
import com.iychay.be.classroom.service.ClassroomService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aulas")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    @GetMapping
    public ResponseEntity<Page<ClassroomResponse>> list(@RequestParam(required = false) String search, Pageable pageable) {
        return ResponseEntity.ok(classroomService.search(search, pageable));
    }

    @PostMapping
    public ResponseEntity<ClassroomResponse> create(@Valid @RequestBody CreateClassroomRequest request) {
        return ResponseEntity.ok(classroomService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(classroomService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomResponse> update(@PathVariable Long id, @Valid @RequestBody UpdateClassroomRequest request) {
        return ResponseEntity.ok(classroomService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classroomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
