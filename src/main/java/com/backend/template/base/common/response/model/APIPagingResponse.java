package com.backend.template.base.common.response.model;

import java.util.List;

import com.backend.template.domain.QuanLyQuy.model.ThuTienEntity;
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


    public APIPagingResponse(List<Object> data, int total) {
        this.data = data;
        this.total = total;
    }
}
