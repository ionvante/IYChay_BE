package com.iychay.be.report.client;

import com.iychay.be.report.dto.GenerateReportRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class IaReportClient {

    private static final Logger log = LoggerFactory.getLogger(IaReportClient.class);

    private final WebClient webClient;

    public IaReportClient(WebClient.Builder builder,
                          @Value("${app.ia.base-url}") String baseUrl,
                          @Value("${app.ia.api-key:}") String apiKey) {
        this.webClient = builder
                .baseUrl(baseUrl)
                .defaultHeader("X-API-KEY", apiKey)
                .build();
    }

    public Mono<IaGenerationResponse> generateAsync(GenerateReportRequest request) {
        return webClient.post()
                .uri("/ia/generar")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(IaGenerationResponse.class)
                .doOnError(error -> log.error("Error calling IA service", error));
    }

    public record IaGenerationResponse(
            String texto,
            Object metricas,
            String html,
            String pdf_url
    ) {
    }
}
