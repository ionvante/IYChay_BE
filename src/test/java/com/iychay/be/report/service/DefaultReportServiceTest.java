package com.iychay.be.report.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iychay.be.report.client.IaReportClient;
import com.iychay.be.report.dto.GenerateReportRequest;
import com.iychay.be.report.dto.ReportResponse;
import com.iychay.be.report.model.IaReport;
import com.iychay.be.report.model.ReportScope;
import com.iychay.be.report.repository.IaReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultReportServiceTest {

    @Mock
    private IaReportRepository iaReportRepository;

    @Mock
    private IaReportClient iaReportClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private DefaultReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new DefaultReportService(iaReportRepository, iaReportClient, objectMapper);
    }

    @Test
    void triggerGenerationSerializesMetricsToJson() throws Exception {
        GenerateReportRequest request = new GenerateReportRequest(ReportScope.alumno, 99L, "2024-Q1", Map.of());

        Map<String, Object> metricas = new LinkedHashMap<>();
        metricas.put("precision", 0.95);
        metricas.put("recall", 0.9);

        IaReportClient.IaGenerationResponse iaResponse = new IaReportClient.IaGenerationResponse(
                "Reporte generado",
                metricas,
                null,
                "https://example.com/report.pdf"
        );

        when(iaReportClient.generateAsync(request)).thenReturn(Mono.just(iaResponse));
        when(iaReportRepository.save(any(IaReport.class))).thenAnswer(invocation -> {
            IaReport report = invocation.getArgument(0);
            report.setId(1L);
            return report;
        });

        ReportResponse response = reportService.triggerGeneration(request);

        ArgumentCaptor<IaReport> reportCaptor = ArgumentCaptor.forClass(IaReport.class);
        verify(iaReportRepository).save(reportCaptor.capture());
        IaReport storedReport = reportCaptor.getValue();

        assertNotNull(storedReport.getJson());
        JsonNode jsonNode = assertDoesNotThrow(() -> objectMapper.readTree(storedReport.getJson()));
        assertEquals(0.95, jsonNode.get("precision").asDouble());
        assertEquals(0.9, jsonNode.get("recall").asDouble());
        assertEquals(storedReport.getJson(), response.json());
        assertDoesNotThrow(() -> objectMapper.readTree(response.json()));
    }

    @Test
    void triggerGenerationThrowsWhenMetricsCannotBeSerialized() {
        GenerateReportRequest request = new GenerateReportRequest(ReportScope.alumno, 101L, "2024-Q2", Map.of());

        Map<String, Object> metricas = new HashMap<>();
        metricas.put("self", metricas);

        IaReportClient.IaGenerationResponse iaResponse = new IaReportClient.IaGenerationResponse(
                "Reporte inválido",
                metricas,
                null,
                null
        );

        when(iaReportClient.generateAsync(request)).thenReturn(Mono.just(iaResponse));

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> reportService.triggerGeneration(request));

        assertThat(exception).hasMessageContaining("No se pudieron serializar las métricas generadas por IA");
        verify(iaReportRepository, never()).save(any());
    }
}
