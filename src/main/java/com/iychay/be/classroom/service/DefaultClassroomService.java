package com.iychay.be.classroom.service;

import com.iychay.be.classroom.dto.ClassroomResponse;
import com.iychay.be.classroom.dto.CreateClassroomRequest;
import com.iychay.be.classroom.dto.UpdateClassroomRequest;
import com.iychay.be.classroom.model.Classroom;
import com.iychay.be.classroom.repository.ClassroomRepository;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DefaultClassroomService implements ClassroomService {

    private final ClassroomRepository classroomRepository;

    public DefaultClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Override
    public Page<ClassroomResponse> search(String search, Pageable pageable) {
        // TODO: Implement filtering by nombre and other criteria
        return classroomRepository.findAll(pageable).map(this::toResponse);
    }

    @Override
    public ClassroomResponse create(CreateClassroomRequest request) {
        Classroom classroom = new Classroom();
        classroom.setNombre(request.nombre());
        classroom.setHorario(request.horario());
        classroom.setCapacidad(request.capacidad());
        // TODO: map tutor ID to user entity once user service is implemented
        Classroom saved = classroomRepository.save(classroom);
        return toResponse(saved);
    }

    @Override
    public ClassroomResponse getById(Long id) {
        return classroomRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Aula no encontrada"));
    }

    @Override
    public ClassroomResponse update(Long id, UpdateClassroomRequest request) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aula no encontrada"));
        if (Objects.nonNull(request.nombre())) {
            classroom.setNombre(request.nombre());
        }
        if (Objects.nonNull(request.horario())) {
            classroom.setHorario(request.horario());
        }
        if (Objects.nonNull(request.capacidad())) {
            classroom.setCapacidad(request.capacidad());
        }
        if (Objects.nonNull(request.estado())) {
            classroom.setEstado(request.estado());
        }
        // TODO: update tutor when user module is ready
        Classroom updated = classroomRepository.save(classroom);
        return toResponse(updated);
    }

    @Override
    public void delete(Long id) {
        classroomRepository.deleteById(id);
    }

    private ClassroomResponse toResponse(Classroom classroom) {
        return new ClassroomResponse(
                classroom.getId(),
                classroom.getNombre(),
                classroom.getTutor() != null ? classroom.getTutor().getId() : null,
                classroom.getHorario(),
                classroom.getCapacidad(),
                classroom.getEstado()
        );
    }
}
