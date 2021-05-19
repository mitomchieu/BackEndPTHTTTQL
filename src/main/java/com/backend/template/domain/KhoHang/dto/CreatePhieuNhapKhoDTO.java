package com.backend.template.domain.KhoHang.dto;

import com.backend.template.domain.HangHoa.model.HangHoaEntity;
import com.backend.template.domain.KhoHang.model.KhoHangEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhieuNhapKhoDTO {
    private String doiTuong;

    private  String diaChi;

    private String dienGiai;

    private Integer soLuong;

    private Date ngayHachToan;

    private Date ngayChungTu;


    private String soChungTu;

    private String maKhoHang;

    private String maHangHoa;
}
