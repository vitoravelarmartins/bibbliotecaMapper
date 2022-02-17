package com.biblioteca.b.mapper;


import com.biblioteca.b.controller.dto.RentedDto;
import com.biblioteca.b.controller.form.RentedForm;
import com.biblioteca.b.model.Book;
import com.biblioteca.b.model.LeaseHistory;
import com.biblioteca.b.model.Person;
import com.biblioteca.b.model.Rented;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface RentedMapper {

    RentedMapper INSTANCE = Mappers.getMapper(RentedMapper.class);

    //Converter Model Rented em DTO
    RentedDto rentedToDto(Rented rented);

    //Converter FORM em Model Rented
   // @Mapping(source ="rentedForm.book",target = "book.id")
    @Mapping(source ="person",target = "person")
    @Mapping(source = "book",target = "book")
    Rented formToRented(Person person, RentedForm rentedForm, Book book);

    @Mapping(source ="leaseHistory.idLease" , target = "idRented")
    @Mapping(source = "leaseHistory.statusLease",target = "statusRented")
    RentedDto leaseToRentedDto(LeaseHistory leaseHistory);



}
