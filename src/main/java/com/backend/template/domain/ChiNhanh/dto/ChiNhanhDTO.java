package com.backend.template.domain.ChiNhanh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiNhanhDTO {
    private String maChiNhanh;
    private String tenChiNhanh;
    private String diaChi;
}
