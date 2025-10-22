package com.iychay.be.message.model;

import com.iychay.be.user.model.User;
import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "mensajes")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversacion_id", nullable = false)
    private Conversation conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emisor_usuario_id", nullable = false)
    private User emisor;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    @Column(name = "adjunto_url")
    private String adjuntoUrl;

    @Column(nullable = false)
    private OffsetDateTime fecha = OffsetDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public User getEmisor() {
        return emisor;
    }

    public void setEmisor(User emisor) {
        this.emisor = emisor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAdjuntoUrl() {
        return adjuntoUrl;
    }

    public void setAdjuntoUrl(String adjuntoUrl) {
        this.adjuntoUrl = adjuntoUrl;
    }

    public OffsetDateTime getFecha() {
        return fecha;
    }

    public void setFecha(OffsetDateTime fecha) {
        this.fecha = fecha;
    }
}
