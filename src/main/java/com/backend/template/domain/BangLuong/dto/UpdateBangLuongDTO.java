package com.backend.template.domain.BangLuong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBangLuongDTO {
    private Long tienLuongTheoNgay;
    private String nhanVienEntity;
}
