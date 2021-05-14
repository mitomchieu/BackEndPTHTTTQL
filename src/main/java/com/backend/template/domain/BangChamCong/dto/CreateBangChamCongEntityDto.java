package com.backend.template.domain.BangChamCong.dto;

import com.backend.template.domain.ChiNhanh.entity.ChiNhanhEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBangChamCongEntityDto {
    private Date ngayTao;
    private String ghiChu;
    private String chiNhanh;
}
