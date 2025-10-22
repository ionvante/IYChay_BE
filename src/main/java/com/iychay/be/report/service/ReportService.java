package com.iychay.be.report.service;

import com.iychay.be.report.dto.GenerateReportRequest;
import com.iychay.be.report.dto.ReportResponse;
import com.iychay.be.report.model.ReportScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {

    Page<ReportResponse> listReports(ReportScope scope, Long refId, String periodo, Pageable pageable);

    ReportResponse getById(Long id);

    ReportResponse triggerGeneration(GenerateReportRequest request);

    byte[] downloadPdf(Long id);
}
