package com.portfolio.portfoliotemplate.controller;

import com.portfolio.portfoliotemplate.transport.dto.AuthenticationRequest;
import com.portfolio.portfoliotemplate.transport.dto.AuthenticationResponse;
import com.portfolio.portfoliotemplate.service.auth.AuthenticationService;
import com.portfolio.portfoliotemplate.transport.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  @PostMapping("/register")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request
  ) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<AuthenticationResponse> refreshToken(
      @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
    return ResponseEntity.ok(service.refreshToken(authHeader));
  }
}
