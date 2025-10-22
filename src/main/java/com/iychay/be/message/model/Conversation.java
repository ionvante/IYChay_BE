package com.iychay.be.message.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversaciones")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ConversationType tipo;

    @Column(name = "ref_id")
    private Long refId;

    @ElementCollection
    @CollectionTable(name = "conversacion_participantes", joinColumns = @JoinColumn(name = "conversacion_id"))
    @Column(name = "usuario_id")
    private List<Long> participantes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ConversationType getTipo() {
        return tipo;
    }

    public void setTipo(ConversationType tipo) {
        this.tipo = tipo;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public List<Long> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Long> participantes) {
        this.participantes = participantes;
    }
}
