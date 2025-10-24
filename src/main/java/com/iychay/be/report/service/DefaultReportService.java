package com.iychay.be.report.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iychay.be.report.client.IaReportClient;
import com.iychay.be.report.dto.GenerateReportRequest;
import com.iychay.be.report.dto.ReportResponse;
import com.iychay.be.report.exception.ReportNotFoundException;
import com.iychay.be.report.exception.ReportPdfDownloadException;
import com.iychay.be.report.exception.ReportPdfNotFoundException;
import com.iychay.be.report.model.IaReport;
import com.iychay.be.report.model.ReportScope;
import com.iychay.be.report.repository.IaReportRepository;

import com.iychay.be.report.storage.ObjectStorageClient;
import com.iychay.be.report.storage.StorageAccessException;
import com.iychay.be.report.storage.StorageFileNotFoundException;
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
    private final ObjectStorageClient objectStorageClient;

    public DefaultReportService(IaReportRepository iaReportRepository,
                               IaReportClient iaReportClient,
                               ObjectStorageClient objectStorageClient) {
        this.iaReportRepository = iaReportRepository;
        this.iaReportClient = iaReportClient;
        this.objectStorageClient = objectStorageClient;
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
        return iaReportRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ReportNotFoundException(id));
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
                .orElseThrow(() -> new ReportNotFoundException(id));
        String pdfUrl = report.getPdfUrl();
        if (pdfUrl == null || pdfUrl.isBlank()) {
            throw new ReportPdfNotFoundException(id);
        }
        try {
            return objectStorageClient.download(pdfUrl);
        } catch (StorageFileNotFoundException e) {
            throw new ReportPdfNotFoundException(id, e);
        } catch (StorageAccessException e) {
            throw new ReportPdfDownloadException(id, e);
        }
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
