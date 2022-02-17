package com.biblioteca.b.service;

import com.biblioteca.b.controller.dto.BookDto;
import com.biblioteca.b.controller.form.BookForm;
import com.biblioteca.b.exception.EntityNotFoundException;
import com.biblioteca.b.mapper.BookMapper;
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

    @Autowired
    public BookMapper bookMapper;

    public Page<BookDto> findAll(String title, String author, Pageable pageable) {

        if (title == null && author == null) {
            return bookRepository.findAll(pageable).map(bookMapper::bookToDto);
        } else if (author == null) {
            return bookRepository.findByTitle(title, pageable).map(bookMapper::bookToDto);
        } else if (title == null) {
            return bookRepository.findByAuthor(author, pageable).map(bookMapper::bookToDto);
        } else if (!title.isEmpty() && !author.isEmpty()) {
            return bookRepository.findByTitleAndAuthor(title, author, pageable).map(bookMapper::bookToDto);
        } else {
            return bookRepository.findAll(pageable).map(bookMapper::bookToDto);
        }
    }

        public ResponseEntity<BookDto> createBook (BookForm bookForm,
                                                   UriComponentsBuilder uriComponentsBuilder){
            Book savedBook = bookRepository.save(bookMapper.formToBook(bookForm));
            URI uri = uriComponentsBuilder.path("/livro/{id}").buildAndExpand(savedBook.getIdBook()).toUri();
            return ResponseEntity.created(uri).body(bookMapper.bookToDto(savedBook));

        }

        public ResponseEntity<BookDto> findById (String idBookStr){
            Long idBook = Long.valueOf(idBookStr);
            Optional<Book> optionalBook = Optional.ofNullable((bookRepository.findById(
                    idBook).orElseThrow(() -> new EntityNotFoundException("ID livro " + idBook + ", não encontrado"))));
            return ResponseEntity.ok(bookMapper.bookToDto(optionalBook.get()));

        }
    }