package com.biblioteca.b.exception;

import com.biblioteca.b.exception.dto.ErrorForRentDto;
import com.biblioteca.b.exception.dto.StandartErroDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorForRentDto> handle(MethodArgumentNotValidException exception){
        List<ErrorForRentDto> dto = new ArrayList<>();

        List<FieldError> fieldErrors =  exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e ->{
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErrorForRentDto error= new ErrorForRentDto(e.getField(),message);
            dto.add(error);
        });
        return dto;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandartErroDto> entityNotFound(EntityNotFoundException exception,
                                                          HttpServletRequest request){
        StandartErroDto erroDto = new StandartErroDto();
        erroDto.setTimestamp(Instant.now());
        erroDto.setStatus(HttpStatus.NOT_FOUND.value());
        erroDto.setError("Valor não encontrado");
        erroDto.setMessage(exception.getMessage());
        erroDto.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroDto);

    }

    @ExceptionHandler(NoContent.class)
    public ResponseEntity<StandartErroDto> notContent(NoContent exception,
                                                          HttpServletRequest request){
        StandartErroDto erroDto = new StandartErroDto();
        erroDto.setTimestamp(Instant.now());
        erroDto.setStatus(HttpStatus.NO_CONTENT.value());
        erroDto.setError("Valor não encontrado");
        erroDto.setMessage(exception.getMessage());
        erroDto.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(erroDto);

    }


}
