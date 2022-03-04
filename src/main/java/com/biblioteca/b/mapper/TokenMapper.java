package com.biblioteca.b.mapper;

import com.biblioteca.b.controller.dto.TokenDto;
import org.apache.tomcat.util.json.Token;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TokenMapper {

    TokenMapper INSTANCE = Mappers.getMapper(TokenMapper.class);

    TokenDto stringEmObjeto(String contentAsString1, Class<TokenDto> tokenDtoClass);
}
