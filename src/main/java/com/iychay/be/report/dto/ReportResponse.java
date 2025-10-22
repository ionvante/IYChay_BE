package com.iychay.be.report.dto;

import com.iychay.be.report.model.ReportScope;
import java.time.OffsetDateTime;

public record ReportResponse(
        Long id,
        ReportScope scope,
        Long refId,
        String periodo,
        String texto,
        String json,
        String pdfUrl,
        OffsetDateTime creadoEn
) {
}
