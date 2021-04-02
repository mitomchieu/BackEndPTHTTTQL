
package com.backend.template.common.exception.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorDTO {
    private HttpStatus status;
    private String message;
    private String stackStrace;
}