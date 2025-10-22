package com.iychay.be.student.service;

import com.iychay.be.student.dto.StudentDetailResponse;
import com.iychay.be.student.dto.StudentRequest;
import com.iychay.be.student.dto.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

    Page<StudentResponse> search(String search, Long aulaId, Pageable pageable);

    StudentResponse create(StudentRequest request);

    StudentResponse update(Long id, StudentRequest request);

    StudentDetailResponse getDetail(Long id);
}
