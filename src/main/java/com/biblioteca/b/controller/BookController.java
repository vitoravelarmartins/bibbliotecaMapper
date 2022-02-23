package com.biblioteca.b.controller;

import com.biblioteca.b.controller.dto.BookDto;
import com.biblioteca.b.controller.form.BookForm;
import com.biblioteca.b.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;


//Para cadastrar livro so Admin da biblioteca

@RestController
@RequestMapping("/livro")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(security = @SecurityRequirement(name = "Authorization"))
    @GetMapping
    public Page<BookDto> lstBook(@RequestParam(required = false) String title,
                                 @RequestParam(required = false) String author,
                                 @PageableDefault(sort = "title", direction = Sort.Direction.ASC,
                                         page = 0, size = 10)
                                         Pageable pageable) {
        return bookService.findAll(title, author, pageable);
    }
    @Operation(security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> lstBookForId(@PathVariable("id") String idBookStr) {
        return bookService.findById(idBookStr);
    }
    @Operation(security = @SecurityRequirement(name = "Authorization"))
    @PostMapping
    @Transactional
    public ResponseEntity<BookDto> createBook(@RequestBody @Valid BookForm bookForm,
                                              UriComponentsBuilder uriComponentsBuilder) {
        return bookService.createBook(bookForm, uriComponentsBuilder);
    }
}
