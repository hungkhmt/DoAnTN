package com.aht.ApiGateway.repository;

import com.aht.ApiGateway.dto.ApiResponse;
import com.aht.ApiGateway.dto.request.IntrospectRequest;
import com.aht.ApiGateway.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IUserClient {
    @PostExchange(url = "api/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<IntrospectResponse> introspect(@RequestBody IntrospectRequest request);
}
