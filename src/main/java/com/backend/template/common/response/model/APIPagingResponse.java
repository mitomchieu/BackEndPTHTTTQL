package com.backend.template.common.response.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIPagingResponse {
    private int status;
    private String message;
    private List<Object> data;
    private int total;
}
