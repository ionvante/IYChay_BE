package com.iychay.be.report.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ReportPdfDownloadException extends RuntimeException {

    public ReportPdfDownloadException(Long reportId, Throwable cause) {
        super("No se pudo descargar el PDF del reporte " + reportId, cause);
    }
}
