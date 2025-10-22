package com.iychay.be.student.model;

import com.iychay.be.classroom.model.Classroom;
import jakarta.persistence.*;

@Entity
@Table(name = "alumnos")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombres;

    @Column(nullable = false, length = 120)
    private String apellidos;

    @Column(unique = true, length = 15)
    private String dni;

    @Column(length = 120)
    private String contacto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id")
    private Classroom classroom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }
}
