package com.github.litaiqing.washer.server.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 16:10
 * @since JDK 1.8
 */
@ControllerAdvice
public class BadRequestExceptionHandler {


    /**
     * 字段异常处理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(BindException exception) {
        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            Map<String, String> map = errors
                    .stream()
                    .collect(Collectors.toMap(
                            objectError -> ((FieldError) objectError).getField(),
                            objectError -> objectError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(map);
        }
        return ResponseEntity.badRequest().body(null);
    }

}
