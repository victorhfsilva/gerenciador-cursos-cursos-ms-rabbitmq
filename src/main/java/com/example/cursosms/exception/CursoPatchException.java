package com.example.cursosms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CursoPatchException extends RuntimeException {
    private String mensagem;
}
