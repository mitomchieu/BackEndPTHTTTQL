package com.backend.template.domain.BangLuong.dto;

import com.backend.template.domain.NhanVien.model.NhanVienEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBangLuongDTO {
    private Long tienLuongTheoNgay;
    private String nhanVienEntity;
}
