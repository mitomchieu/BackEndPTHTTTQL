package com.backend.template.domain.KhoHang.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhieuChuyenKhoDTO {
    private String doiTuong;

    private  String diaChi;

    private String dienGiai;

    private Integer soLuong;

    private Date ngayHachToan;

    private Date ngayChungTu;


    private String soChungTu;

    private String maKhoHang;

    private String maHangHoa;

    private  String maKhoDuocChuyen;
}
