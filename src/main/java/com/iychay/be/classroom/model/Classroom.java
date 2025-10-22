package com.iychay.be.classroom.model;

import com.iychay.be.user.model.User;
import jakarta.persistence.*;

@Entity
@Table(name = "aulas")
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_usuario_id")
    private User tutor;

    @Column(length = 120)
    private String horario;

    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClassroomStatus estado = ClassroomStatus.ACTIVA;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public ClassroomStatus getEstado() {
        return estado;
    }

    public void setEstado(ClassroomStatus estado) {
        this.estado = estado;
    }
}
