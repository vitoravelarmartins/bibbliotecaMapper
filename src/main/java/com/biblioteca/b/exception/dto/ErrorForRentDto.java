package com.biblioteca.b.exception.dto;

import lombok.Getter;

@Getter
public class ErrorForRentDto {

    private String textFild;
    private String error;

    public ErrorForRentDto(String textFild, String error) {
        this.textFild = textFild;
        this.error = error;
    }
}
