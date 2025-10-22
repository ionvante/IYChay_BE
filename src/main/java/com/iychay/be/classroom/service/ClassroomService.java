package com.iychay.be.classroom.service;

import com.iychay.be.classroom.dto.ClassroomResponse;
import com.iychay.be.classroom.dto.CreateClassroomRequest;
import com.iychay.be.classroom.dto.UpdateClassroomRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClassroomService {

    Page<ClassroomResponse> search(String search, Pageable pageable);

    ClassroomResponse create(CreateClassroomRequest request);

    ClassroomResponse getById(Long id);

    ClassroomResponse update(Long id, UpdateClassroomRequest request);

    void delete(Long id);
}
