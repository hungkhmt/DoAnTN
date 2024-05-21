package com.aht.UserManagementService.controller;

import com.aht.UserManagementService.dto.request.AuthenticationRequest;
import com.aht.UserManagementService.dto.request.IntrospectRequest;
import com.aht.UserManagementService.dto.response.AuthenticationResponse;
import com.aht.UserManagementService.dto.response.IntrospectResponse;
import com.aht.UserManagementService.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticate(request);
        if(result.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }

    @PostMapping("/introspect")
    ResponseEntity<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        if (result.isValid()) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }

    }

    @PostMapping("/logout")
    ResponseEntity<String> logout(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ResponseEntity.status(HttpStatus.OK).body("Logout success");
    }

    @PostMapping("/refresh")
    ResponseEntity<AuthenticationResponse> refreshToken(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
