package com.biblioteca.b.service;

import com.biblioteca.b.controller.dto.BookDto;
import com.biblioteca.b.controller.form.BookForm;
import com.biblioteca.b.model.Book;
import com.biblioteca.b.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    public BookRepository bookRepository;

    public Page<BookDto> findAll(String title, String author, Pageable pageable) {

        if (title == null && author == null) {
            Page<Book> books = bookRepository.findAll(pageable);
            return Book.convert(books);
        } else if (author == null) {
            Page<Book> books = bookRepository.findByTitle(title, pageable);
            return Book.convert(books);
        } else if (title == null) {
            Page<Book> books = bookRepository.findByAuthor(author, pageable);
            return Book.convert(books);
        }else if(!title.isEmpty() && !author.isEmpty()){
            Page<Book> books = bookRepository.findByTitleAndAuthor(title, author, pageable);
            return Book.convert(books);
        } else {
            Page<Book> books = bookRepository.findAll(pageable);
            return Book.convert(books);
        }

    }

    public ResponseEntity<BookDto> createBook(BookForm bookForm,
                                              UriComponentsBuilder uriComponentsBuilder) {
        Book book = bookForm.convert();
        bookRepository.save(book);
        URI uri = uriComponentsBuilder.path("/livro/{id}").buildAndExpand(book.getId()).toUri();
        return ResponseEntity.created(uri).body(new BookDto(book));

    }

    public ResponseEntity<BookDto> findById(String idBookStr) {
        Long idBook = Long.valueOf(idBookStr);
        Optional<Book> optionalBook = bookRepository.findById(idBook);
        return ResponseEntity.ok(new BookDto(optionalBook.get()));
    }
}