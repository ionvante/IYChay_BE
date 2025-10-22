package com.iychay.be.report.controller;

import com.iychay.be.report.model.IaReport;
import com.iychay.be.report.model.ReportScope;
import com.iychay.be.report.repository.IaReportRepository;
import com.iychay.be.report.storage.ObjectStorageClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ReportControllerPdfIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IaReportRepository iaReportRepository;

    @MockBean
    private ObjectStorageClient objectStorageClient;

    @AfterEach
    void tearDown() {
        iaReportRepository.deleteAll();
    }

    @Test
    @WithMockUser
    void downloadPdfReturnsContentWhenAvailable() throws Exception {
        IaReport report = new IaReport();
        report.setScope(ReportScope.alumno);
        report.setRefId(1L);
        report.setPeriodo("2024-01");
        report.setPdfUrl("reports/report-1.pdf");
        IaReport saved = iaReportRepository.save(report);

        byte[] expectedContent = "PDF".getBytes();
        when(objectStorageClient.download("reports/report-1.pdf")).thenReturn(expectedContent);

        mockMvc.perform(get("/reportes/{id}/pdf", saved.getId()))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=report-" + saved.getId() + ".pdf"))
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(content().bytes(expectedContent));
    }

    @Test
    @WithMockUser
    void downloadPdfWithoutUrlReturnsNotFound() throws Exception {
        IaReport report = new IaReport();
        report.setScope(ReportScope.alumno);
        report.setRefId(2L);
        report.setPeriodo("2024-02");
        IaReport saved = iaReportRepository.save(report);

        mockMvc.perform(get("/reportes/{id}/pdf", saved.getId()))
                .andExpect(status().isNotFound());
    }
}
