package com.iychay.be.student.service;

import com.iychay.be.classroom.repository.ClassroomRepository;
import com.iychay.be.student.dto.StudentDetailResponse;
import com.iychay.be.student.dto.StudentRequest;
import com.iychay.be.student.dto.StudentResponse;
import com.iychay.be.student.model.Student;
import com.iychay.be.student.repository.StudentRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultStudentService implements StudentService {

    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;

    public DefaultStudentService(StudentRepository studentRepository, ClassroomRepository classroomRepository) {
        this.studentRepository = studentRepository;
        this.classroomRepository = classroomRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StudentResponse> search(String search, Long aulaId, Pageable pageable) {
        // TODO: Implement filtering using specifications or query methods
        return studentRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public StudentResponse create(StudentRequest request) {
        Student student = new Student();
        applyRequest(student, request);
        Student saved = studentRepository.save(student);
        return toResponse(saved);
    }

    @Override
    public StudentResponse update(Long id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado"));
        applyRequest(student, request);
        return toResponse(studentRepository.save(student));
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDetailResponse getDetail(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado"));
        // TODO: add asistencia, notas e historial real desde repositorios correspondientes
        return new StudentDetailResponse(
                student.getId(),
                student.getNombres(),
                student.getApellidos(),
                student.getDni(),
                student.getContacto(),
                student.getClassroom() != null ? student.getClassroom().getId() : null,
                List.of()
        );
    }

    private void applyRequest(Student student, StudentRequest request) {
        student.setNombres(request.nombres());
        student.setApellidos(request.apellidos());
        student.setDni(request.dni());
        student.setContacto(request.contacto());
        if (Objects.nonNull(request.aulaId())) {
            student.setClassroom(classroomRepository.findById(request.aulaId())
                    .orElseThrow(() -> new IllegalArgumentException("Aula no encontrada")));
        } else {
            student.setClassroom(null);
        }
    }

    private StudentResponse toResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getNombres(),
                student.getApellidos(),
                student.getDni(),
                student.getClassroom() != null ? student.getClassroom().getId() : null
        );
    }
}
