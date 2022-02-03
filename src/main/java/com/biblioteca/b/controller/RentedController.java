package com.biblioteca.b.controller;


import com.biblioteca.b.controller.dto.RentedDto;
import com.biblioteca.b.controller.form.RentedForm;
import com.biblioteca.b.service.RentedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/locacoes")
public class RentedController {


    @Autowired
    private RentedService rentedService;

    @GetMapping
    public List<RentedDto> list(String bookTitle) {
       List<RentedDto> rentedDtoResponseEntity = rentedService.findAll(bookTitle);
        return rentedDtoResponseEntity;

    }

    @PostMapping
    @Transactional
    public ResponseEntity<RentedDto> renting(@RequestBody @Valid RentedForm rentedForm,
                                             UriComponentsBuilder uriComponentsBuilder){
        return rentedService.renting(rentedForm,uriComponentsBuilder);

    }

    @GetMapping("/{id}")
    public ResponseEntity<RentedDto> details(@PathVariable("id") Long id){
        ResponseEntity<RentedDto> rentedDtoResponseEntity = rentedService.findById(id);
        return rentedDtoResponseEntity ;

    }

    // Minhas locações -> //user/iduser/locacoes
    //Nested Controllers
    //UUID



}
