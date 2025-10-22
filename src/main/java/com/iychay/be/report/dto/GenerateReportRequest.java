package com.iychay.be.report.dto;

import com.iychay.be.report.model.ReportScope;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record GenerateReportRequest(
        @NotNull ReportScope scope,
        @NotNull Long refId,
        @NotBlank String periodo,
        Map<String, Double> pesos
) {
}
