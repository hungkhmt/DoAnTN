package com.aht.ApiGateway.service;

import com.aht.ApiGateway.dto.ApiResponse;
import com.aht.ApiGateway.dto.request.IntrospectRequest;
import com.aht.ApiGateway.dto.response.IntrospectResponse;
import com.aht.ApiGateway.repository.IUserClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    IUserClient userClient;

    public Mono<IntrospectResponse> introspect(String token) {
        return userClient.introspect(IntrospectRequest.builder()
                .token(token)
                .build());
    }
}
