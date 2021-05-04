package com.backend.template.domain.KhoHang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateKhoHangDTO {
    private String maKho;

    private String tenKho;

    private String diaChi;
}
