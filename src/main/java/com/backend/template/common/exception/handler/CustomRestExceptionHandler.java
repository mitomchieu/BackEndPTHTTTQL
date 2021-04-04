package com.backend.template.common.exception.handler;

import com.backend.template.common.exception.dto.ApiErrorDTO;

import com.backend.template.common.exception.BackendError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CustomRestExceptionHandler  implements AuthenticationEntryPoint {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        // Get error value
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setMessage(errors.toString());
        apiErrorDTO.setStatus(HttpStatus.BAD_REQUEST);
        ResponseEntity<ApiErrorDTO> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorDTO);
        return response;
    }

    @ExceptionHandler(value = BackendError.class)
    public ResponseEntity<ApiErrorDTO> handleCustomBackendError(BackendError ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setMessage(ex.getMessage());
        apiErrorDTO.setStatus(ex.getStatusCode());
        ResponseEntity<ApiErrorDTO> response = ResponseEntity.status(ex.getStatusCode()).body(apiErrorDTO);
        return response;
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorDTO> handleAll(Exception ex) {
        System.out.println(ex);
        log.error(ex.getMessage());
        ex.printStackTrace();
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setMessage(ex.getMessage());
        apiErrorDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseEntity<ApiErrorDTO> response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorDTO);
        return response;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        BackendError error = new BackendError(HttpStatus.UNAUTHORIZED, e.getMessage());
        e.printStackTrace();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<?> response = handleCustomBackendError(error);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().print(mapper.writeValueAsString(response.getBody()));
        httpServletResponse.setStatus(response.getStatusCodeValue());
        httpServletResponse.flushBuffer();
        return;
    }
}