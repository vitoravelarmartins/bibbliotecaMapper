package com.biblioteca.b.controller;

import com.biblioteca.b.controller.dto.BookDto;
import com.biblioteca.b.controller.form.BookForm;
import com.biblioteca.b.model.Book;
import com.biblioteca.b.repository.BookRepository;
import com.biblioteca.b.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livro")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> lstBook(){
        return bookRepository.findAll();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookForm bookForm,
                                              UriComponentsBuilder uriComponentsBuilder){
        return bookService.createBook(bookForm,uriComponentsBuilder);
        
//        Book book = bookForm.convert();
//        bookRepository.save(book);
//        URI uri = uriComponentsBuilder.path("/livro/{id}").buildAndExpand(book.getId()).toUri();
//        return ResponseEntity.created(uri).body(new BookDto(book));
    }
}
