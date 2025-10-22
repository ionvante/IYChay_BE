package com.iychay.be.report.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReportPdfNotFoundException extends RuntimeException {

    public ReportPdfNotFoundException(Long reportId) {
        super("El reporte " + reportId + " no tiene PDF disponible");
    }

    public ReportPdfNotFoundException(Long reportId, Throwable cause) {
        super("El reporte " + reportId + " no tiene PDF disponible", cause);
    }
}
