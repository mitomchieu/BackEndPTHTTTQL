package com.backend.template.domain.QuanLyQuy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateGiaoDichDTO {

    @Size(min = 3)
    protected String maGiaoDich;

    protected String doiTuongGiaoDich;

    protected Date thoiGianGiaoDich;

    protected  Date thoiGianHachToan;

    protected  String lyDo;

    protected  Long tongTien;

    protected Boolean isTienMat = true;

}
