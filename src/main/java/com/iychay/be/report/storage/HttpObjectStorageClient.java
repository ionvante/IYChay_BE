package com.iychay.be.report.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class HttpObjectStorageClient implements ObjectStorageClient {

    private final WebClient webClient;
    private final String bucketBaseUrl;

    public HttpObjectStorageClient(WebClient.Builder builder,
                                   @Value("${app.storage.bucket-url}") String bucketBaseUrl) {
        this.webClient = builder.build();
        this.bucketBaseUrl = normalizeBaseUrl(bucketBaseUrl);
    }

    @Override
    public byte[] download(String location) {
        String targetUrl = resolveUrl(location);
        try {
            return webClient.get()
                    .uri(targetUrl)
                    .accept(MediaType.APPLICATION_PDF)
                    .retrieve()
                    .onStatus(status -> status.value() == HttpStatus.NOT_FOUND.value(), response ->
                            Mono.error(new StorageFileNotFoundException("Archivo no encontrado en almacenamiento: " + targetUrl)))
                    .onStatus(HttpStatusCode::is4xxClientError, response ->
                            Mono.error(new StorageAccessException("El almacenamiento rechazó la solicitud para: " + targetUrl)))
                    .onStatus(HttpStatusCode::is5xxServerError, response ->
                            Mono.error(new StorageAccessException("Error del almacenamiento al obtener: " + targetUrl)))
                    .bodyToMono(byte[].class)
                    .block();
        } catch (StorageFileNotFoundException | StorageAccessException ex) {
            throw ex;
        } catch (WebClientResponseException.NotFound ex) {
            throw new StorageFileNotFoundException("Archivo no encontrado en almacenamiento: " + targetUrl, ex);
        } catch (WebClientRequestException ex) {
            throw new StorageAccessException("Fallo de red al acceder al almacenamiento: " + targetUrl, ex);
        } catch (WebClientResponseException ex) {
            throw new StorageAccessException("Error al recuperar archivo del almacenamiento: " + targetUrl, ex);
        }
    }

    private String resolveUrl(String location) {
        if (location == null || location.isBlank()) {
            throw new StorageAccessException("La ubicación del archivo no puede ser vacía");
        }
        URI uri;
        try {
            uri = URI.create(location);
        } catch (IllegalArgumentException ex) {
            throw new StorageAccessException("Ubicación de archivo inválida: " + location, ex);
        }
        if (uri.isAbsolute()) {
            return uri.toString();
        }
        String relative = location.startsWith("/") ? location.substring(1) : location;
        return bucketBaseUrl + relative;
    }

    private String normalizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("La URL base del almacenamiento no puede ser vacía");
        }
        return baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
    }
}
