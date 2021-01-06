package com.zmlh.myexpectionhandler;

import com.zmlh.entity.Response;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @ClassName MyExceptionHandler
 * @Description TODO
 * @Author tyh
 * @Date 2021-01-05 16:47
 * @Version 1.0
 **/
@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @SneakyThrows
    public Object handleMethodArgumentNotValidException ( MethodArgumentNotValidException ex ) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder("校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = sb.toString();
        log.info("匹配失败！原因是：" + msg);
        return new Response().setCode(500).setObject(msg);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleConstraintViolationException ( ConstraintViolationException ex ) {
        return new Response().setCode(500).setObject(ex.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleException ( Exception ex ) {
        return new Response().setCode(500).setObject(ex.getMessage());
    }
}
