package com.iychay.be.report.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "reportes_ia")
public class IaReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReportScope scope;

    @Column(name = "ref_id")
    private Long refId;

    @Column(length = 20)
    private String periodo;

    @Column(columnDefinition = "TEXT")
    private String texto;

    @Column(columnDefinition = "TEXT")
    private String json;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @Column(name = "creado_en", nullable = false)
    private OffsetDateTime creadoEn = OffsetDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportScope getScope() {
        return scope;
    }

    public void setScope(ReportScope scope) {
        this.scope = scope;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public OffsetDateTime getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(OffsetDateTime creadoEn) {
        this.creadoEn = creadoEn;
    }
}
