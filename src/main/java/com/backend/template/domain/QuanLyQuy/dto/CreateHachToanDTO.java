package com.backend.template.domain.QuanLyQuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHachToanDTO {
    private String dienGiai;
    private Long taiKhoanNo;
    private Long taiKhoanCo;
    private Long soTien;
    private String doiTuong;
    private String maGiaoDich;
}
