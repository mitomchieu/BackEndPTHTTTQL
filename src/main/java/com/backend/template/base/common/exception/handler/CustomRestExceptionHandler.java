package com.backend.template.base.common.exception.handler;

import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.exception.dto.ApiErrorDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class CustomRestExceptionHandler  implements AuthenticationEntryPoint {


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
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorDTO);
    }

    public ResponseEntity<ApiErrorDTO> handleCustomBackendError(BackendError ex) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setMessage(ex.getMessage());
        apiErrorDTO.setStatus(ex.getStatusCode());
        return ResponseEntity.status(ex.getStatusCode()).body(apiErrorDTO);
    }

    public ResponseEntity<ApiErrorDTO> handleDataIntegrityViolationExceptionExceptions(
            DataIntegrityViolationException ex
    ) {
        log.error(ex.getMessage());
        ex.printStackTrace();
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setMessage(ex.getMessage());
        apiErrorDTO.setStatus(HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorDTO);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiErrorDTO> handleAll(Exception ex) {
        System.out.println(ex.getClass());
        if (ex instanceof AccessDeniedException) {
            ex.printStackTrace();
            return handleCustomBackendError(new BackendError(HttpStatus.UNAUTHORIZED, ex.getMessage()));
        }
        if (ex instanceof BackendError) {
            return  handleCustomBackendError((BackendError) ex);
        }
        if (ex instanceof  MethodArgumentNotValidException) {
            return handleValidationExceptions((MethodArgumentNotValidException) ex);
        }
        if (ex instanceof DataIntegrityViolationException) {
            return handleDataIntegrityViolationExceptionExceptions((DataIntegrityViolationException) ex);
        }
        log.error(ex.getMessage());
        ex.printStackTrace();
        ApiErrorDTO apiErrorDTO = new ApiErrorDTO();
        apiErrorDTO.setMessage(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorDTO);
    }

    void servletExceptionWriter(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BackendError error) throws IOException {
        log.info(httpServletRequest.toString());
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<?> response = handleCustomBackendError(error);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().print(mapper.writeValueAsString(response.getBody()));
        httpServletResponse.setStatus(response.getStatusCodeValue());
        httpServletResponse.flushBuffer();
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        BackendError error = new BackendError(HttpStatus.UNAUTHORIZED, e.getMessage());
        e.printStackTrace();
        this.servletExceptionWriter(httpServletRequest, httpServletResponse, error);
    }
}