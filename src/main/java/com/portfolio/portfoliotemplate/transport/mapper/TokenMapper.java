package com.portfolio.portfoliotemplate.transport.mapper;

import com.portfolio.portfoliotemplate.config.MapStructConfig;
import com.portfolio.portfoliotemplate.persistence.entity.token.Token;
import com.portfolio.portfoliotemplate.persistence.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface TokenMapper {

  @Mapping(target = "user", source = "user")
  Token toToken(User user, String token);
}
