package com.biblioteca.b.controller;


import com.biblioteca.b.controller.dto.RentedDto;
import com.biblioteca.b.controller.form.RentedForm;
import com.biblioteca.b.model.Rented;
import com.biblioteca.b.service.RentedService;
import com.biblioteca.b.service.SecurityCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;


@RestController
@RequestMapping("/users/{idUser}/locacoes")
@PreAuthorize("@securityCheck.check(#idUserStr,authentication)")
public class RentedController {


    @Autowired
    private RentedService rentedService;

    @Autowired
    private SecurityCheck securityCheck;


    @GetMapping("/{id}")
    public ResponseEntity<RentedDto> details(@PathVariable("idUser") String idUserStr,
                                             @PathVariable("id") String idS) {
        Long id = Long.valueOf(idS);
        ResponseEntity<RentedDto> rentedDtoResponseEntity = rentedService.findById(id);
        return rentedDtoResponseEntity;

    }

    @GetMapping
    public Page<RentedDto> list(@PathVariable("idUser") String idUserStr,
                                @RequestParam(required = false) String bookTitle,
                                @PageableDefault(sort = "dateDelivery",
                                        direction = Sort.Direction.DESC,
                                        page = 0, size = 10) Pageable pageable) {
        Page<RentedDto> rentedDtoResponseEntity = rentedService.findAll(idUserStr, bookTitle, pageable);
        return rentedDtoResponseEntity;

    }

    @Transactional
    @PostMapping("/locar")
    public ResponseEntity<?> renting(@PathVariable("idUser") String idUserStr,
                                     //      @PathVariable("idBook") String idBookStr,
                                     @RequestBody @Valid RentedForm rentedForm,
                                     UriComponentsBuilder uriComponentsBuilder) {
        return rentedService.renting(idUserStr, rentedForm, uriComponentsBuilder);

    }


    @Transactional
    @PostMapping("/{id}/devolver")
    public ResponseEntity<?> delivery(@PathVariable("idUser") String idUserStr,
                                      @PathVariable("id") String idRentedStr,
                                      UriComponentsBuilder uriComponentsBuilder) {
        return rentedService.delivery(idUserStr, idRentedStr, uriComponentsBuilder);
    }

    @GetMapping("/leaseHistory")
    public Page<RentedDto> detailsLease(@PathVariable("idUser") String idUserStr,
                                        @RequestParam(required = false) String bookTitle,
                                        @PageableDefault(sort = "dateDelivery",
                                                direction = Sort.Direction.DESC,
                                                page = 0, size = 10) Pageable pageable) {
        Page<RentedDto> rentedDtoResponseEntity = rentedService.leaseFind(idUserStr, bookTitle, pageable);
        return rentedDtoResponseEntity;

    }

    @GetMapping("/leaseHistory/{idLease}")
    public ResponseEntity<RentedDto> detailsLeaseFindId(@PathVariable("idUser") String idUserStr,
                                        @PathVariable("idLease") String idLeaseString) {
        ResponseEntity<RentedDto> rentedDtoResponseEntity = rentedService.leaseFind(idLeaseString);
        return rentedDtoResponseEntity;

    }


    // Minhas locações -> //user/iduser/locacoes
    //Nested Controllers
    //UUID


}
