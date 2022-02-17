package com.biblioteca.b.mapper;

import com.biblioteca.b.controller.dto.PersonDto;
import com.biblioteca.b.controller.form.PersonForm;
import com.biblioteca.b.model.Person;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    //Converter Modelo Person em DTO
    PersonDto personToDto(Person person);

    //Converter FORM em Person

    Person formToPerson(PersonForm personForm);

}
