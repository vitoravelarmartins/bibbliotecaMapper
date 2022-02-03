package com.biblioteca.b.service;

import com.biblioteca.b.controller.dto.BookDto;
import com.biblioteca.b.controller.form.BookForm;
import com.biblioteca.b.model.Book;
import com.biblioteca.b.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class BookService {

    @Autowired
    public BookRepository bookRepository;

    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return Book.convert(books);
    }

    public ResponseEntity<BookDto> createBook(BookForm bookForm,
                                              UriComponentsBuilder uriComponentsBuilder){
        Book book = bookForm.convert();
        bookRepository.save(book);
        URI uri = uriComponentsBuilder.path("/livro/{id}").buildAndExpand(book.getId()).toUri();
        return ResponseEntity.created(uri).body(new BookDto(book));

    }
}