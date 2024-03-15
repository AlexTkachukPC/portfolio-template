package com.portfolio.portfoliotemplate.transport.mapper;

import com.portfolio.portfoliotemplate.config.MapStructConfig;
import com.portfolio.portfoliotemplate.transport.dto.RegisterRequest;
import com.portfolio.portfoliotemplate.persistence.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(config = MapStructConfig.class)
public abstract class UserMapper {

  @Autowired
  protected PasswordEncoder passwordEncoder;

  @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.getPassword()))")
  public abstract User toUser(RegisterRequest request);
}
