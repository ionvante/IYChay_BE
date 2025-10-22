package com.iychay.be.classroom.repository;

import com.iychay.be.classroom.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
}
