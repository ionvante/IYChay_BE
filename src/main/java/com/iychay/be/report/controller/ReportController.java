package com.iychay.be.report.controller;

import com.iychay.be.report.dto.GenerateReportRequest;
import com.iychay.be.report.dto.ReportResponse;
import com.iychay.be.report.model.ReportScope;
import com.iychay.be.report.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reportes")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<Page<ReportResponse>> list(@RequestParam(required = false) ReportScope scope,
                                                     @RequestParam(required = false) Long refId,
                                                     @RequestParam(required = false) String periodo,
                                                     Pageable pageable) {
        return ResponseEntity.ok(reportService.listReports(scope, refId, periodo, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getById(id));
    }

    @PostMapping("/generar")
    public ResponseEntity<ReportResponse> generate(@Valid @RequestBody GenerateReportRequest request) {
        return ResponseEntity.ok(reportService.triggerGeneration(request));
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        byte[] pdf = reportService.downloadPdf(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
