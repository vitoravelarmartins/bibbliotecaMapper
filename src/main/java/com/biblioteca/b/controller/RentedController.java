package com.biblioteca.b.controller;

import com.biblioteca.b.controller.dto.RentedDto;
import com.biblioteca.b.controller.form.RentedForm;
import com.biblioteca.b.service.RentedService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@PreAuthorize("@securityCheck.check(#idUser,authentication)")
public class RentedController {

    @Autowired
    private RentedService rentedService;

    @Operation(security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/{idLocacao}")
    public ResponseEntity<RentedDto> details(@PathVariable("idUser") Long idUser,
                                             @PathVariable("idLocacao") Long idS) {
        ResponseEntity<RentedDto> rentedDtoResponseEntity = rentedService.findById(idS,idUser);
        return rentedDtoResponseEntity;
    }
    
    @Operation(security = @SecurityRequirement(name = "Authorization"))
    @GetMapping
    public Page<RentedDto> list(@PathVariable("idUser") Long idUser,
                                @RequestParam(required = false) String bookTitle,
                                @PageableDefault(sort = "dateDelivery",
                                        direction = Sort.Direction.DESC,
                                        page = 0, size = 10) Pageable pageable) {
        Page<RentedDto> rentedDtoResponseEntity = rentedService.findAll(idUser, bookTitle, pageable);
        return rentedDtoResponseEntity;

    }

    @Operation(security = @SecurityRequirement(name = "Authorization"))
    @Transactional
    @PostMapping("/locar")
    public ResponseEntity<?> renting(@PathVariable("idUser") Long idUser,
                                     @RequestBody @Valid RentedForm rentedForm,
                                     UriComponentsBuilder uriComponentsBuilder) {
        return rentedService.renting(idUser, rentedForm, uriComponentsBuilder);

    }

    @Operation(security = @SecurityRequirement(name = "Authorization"))
    @Transactional
    @PostMapping("/{idRented}/devolver")
    public ResponseEntity<?> delivery(@PathVariable("idUser") Long idUser,
                                      @PathVariable("idRented") String idRentedStr,
                                      UriComponentsBuilder uriComponentsBuilder) {
        return rentedService.delivery(idUser, idRentedStr, uriComponentsBuilder);
    }

    @Operation(security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/leaseHistory")
    public Page<RentedDto> detailsLease(@PathVariable("idUser") Long idUser,
                                        @RequestParam(required = false) String bookTitle,
                                        @PageableDefault(sort = "dateDelivery",
                                                direction = Sort.Direction.DESC,
                                                page = 0, size = 10) Pageable pageable) {
        Page<RentedDto> rentedDtoResponseEntity = rentedService.leaseFind(idUser, bookTitle, pageable);
        return rentedDtoResponseEntity;

    }

    @Operation(security = @SecurityRequirement(name = "Authorization"))
    @GetMapping("/leaseHistory/{idLease}")
    public ResponseEntity<RentedDto> detailsLeaseFindId(@PathVariable("idUser") Long idUser,
                                                        @PathVariable("idLease") Long idLease) {
        ResponseEntity<RentedDto> rentedDtoResponseEntity = rentedService.leaseFindId(idLease);
        return rentedDtoResponseEntity;

    }
}
