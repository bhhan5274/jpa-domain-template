package io.github.bhhan.common;

import io.github.bhhan.common.error.ErrorCode;
import io.github.bhhan.common.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBindException(MethodArgumentNotValidException e){
        return new ErrorResponse(ErrorCode.BAD_REQUEST, e.getFieldErrors());
    }
}
