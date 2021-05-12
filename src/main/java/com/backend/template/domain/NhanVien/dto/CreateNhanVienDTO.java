package com.backend.template.domain.NhanVien.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateNhanVienDTO {

    private String maNhanVien;
    private String tenNhanVien;
    protected Date ngaySinh;
    private String soTaiKhoanNganHang;
    private String maChiNhanh;
}
