package com.iychay.be.report.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iychay.be.report.client.IaReportClient;
import com.iychay.be.report.dto.GenerateReportRequest;
import com.iychay.be.report.dto.ReportResponse;
import com.iychay.be.report.model.IaReport;
import com.iychay.be.report.model.ReportScope;
import com.iychay.be.report.repository.IaReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultReportService implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(DefaultReportService.class);

    private final IaReportRepository iaReportRepository;
    private final IaReportClient iaReportClient;
    private final ObjectMapper objectMapper;

    public DefaultReportService(IaReportRepository iaReportRepository,
                               IaReportClient iaReportClient,
                               ObjectMapper objectMapper) {
        this.iaReportRepository = iaReportRepository;
        this.iaReportClient = iaReportClient;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReportResponse> listReports(ReportScope scope, Long refId, String periodo, Pageable pageable) {
        if (scope == null) {
            return iaReportRepository.findAll(pageable).map(this::toResponse);
        }
        if (refId == null) {
            return iaReportRepository.findByScope(scope, pageable).map(this::toResponse);
        }
        if (periodo == null || periodo.isBlank()) {
            return iaReportRepository.findByScopeAndRefId(scope, refId, pageable).map(this::toResponse);
        }
        return iaReportRepository.findByScopeAndRefIdAndPeriodoContainingIgnoreCase(scope, refId, periodo, pageable)
                .map(this::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ReportResponse getById(Long id) {
        return iaReportRepository.findById(id).map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado"));
    }

    @Override
    public ReportResponse triggerGeneration(GenerateReportRequest request) {
        var iaResponse = iaReportClient.generateAsync(request).block();
        // TODO: manejar la generación asíncrona y almacenamiento de PDF
        IaReport report = new IaReport();
        report.setScope(request.scope());
        report.setRefId(request.refId());
        report.setPeriodo(request.periodo());
        if (iaResponse != null) {
            report.setTexto(iaResponse.texto());
            if (iaResponse.metricas() != null) {
                try {
                    report.setJson(objectMapper.writeValueAsString(iaResponse.metricas()));
                } catch (JsonProcessingException e) {
                    log.error("Error serializando métricas IA para scope {} y refId {}", request.scope(), request.refId(), e);
                    throw new IllegalStateException("No se pudieron serializar las métricas generadas por IA", e);
                }
            }
            report.setPdfUrl(iaResponse.pdf_url());
        }
        return toResponse(iaReportRepository.save(report));
    }

    @Override
    public byte[] downloadPdf(Long id) {
        IaReport report = iaReportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado"));
        // TODO: Integrate with object storage service to retrieve PDF bytes
        if (report.getPdfUrl() == null) {
            throw new IllegalStateException("Reporte sin PDF disponible");
        }
        return new byte[0];
    }

    private ReportResponse toResponse(IaReport report) {
        return new ReportResponse(
                report.getId(),
                report.getScope(),
                report.getRefId(),
                report.getPeriodo(),
                report.getTexto(),
                report.getJson(),
                report.getPdfUrl(),
                report.getCreadoEn()
        );
    }
}
