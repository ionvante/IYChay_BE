package com.iychay.be.student.repository;

import com.iychay.be.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
