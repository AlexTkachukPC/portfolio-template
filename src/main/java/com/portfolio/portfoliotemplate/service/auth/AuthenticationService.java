package com.portfolio.portfoliotemplate.service.auth;

import com.portfolio.portfoliotemplate.transport.dto.RegisterRequest;
import com.portfolio.portfoliotemplate.transport.mapper.TokenMapper;
import com.portfolio.portfoliotemplate.transport.mapper.UserMapper;
import com.portfolio.portfoliotemplate.transport.dto.AuthenticationRequest;
import com.portfolio.portfoliotemplate.transport.dto.AuthenticationResponse;
import com.portfolio.portfoliotemplate.persistence.entity.token.Token;
import com.portfolio.portfoliotemplate.persistence.repository.TokenRepository;
import com.portfolio.portfoliotemplate.persistence.repository.UserRepository;
import com.portfolio.portfoliotemplate.persistence.entity.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserMapper userMapper;
  private final TokenMapper tokenMapper;
  private final JwtService jwtService;
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    User user = repository.save(userMapper.toUser(request));
    return generateAccessAndRefreshToken(user);
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    User user = repository.findByEmail(request.getEmail()).orElseThrow();
    revokeAllUserTokens(user);
    return generateAccessAndRefreshToken(user);
  }

  public AuthenticationResponse refreshToken(String authHeader) {
    String refreshToken = authHeader.substring(7);
    String userEmail = jwtService.extractUsername(refreshToken);
    User user = repository.findByEmail(userEmail).orElseThrow();
    validateToken(refreshToken, user);
    String accessToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    tokenRepository.save(tokenMapper.toToken(user, accessToken));
    return new AuthenticationResponse(accessToken, refreshToken);
  }

  private void validateToken(String token, User user) {
    if (!jwtService.isTokenValid(token, user)) {
      throw new IllegalArgumentException("Token is invalid");
    }
  }

  private AuthenticationResponse generateAccessAndRefreshToken(User user) {
    String accessToken = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);
    tokenRepository.save(tokenMapper.toToken(user, accessToken));
    return new AuthenticationResponse(accessToken, refreshToken);
  }

  private void revokeAllUserTokens(User user) {
    List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty()) {
      return;
    }
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}
