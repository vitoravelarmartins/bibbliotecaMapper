package com.biblioteca.b.mapper;

import com.biblioteca.b.controller.dto.BookDto;
import com.biblioteca.b.controller.form.BookForm;
import com.biblioteca.b.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    //Converter Model Book em sua DTO
    BookDto bookToDto(Book book);

    //Converter form para  Model Book
    Book formToBook(BookForm bookForm);

}
