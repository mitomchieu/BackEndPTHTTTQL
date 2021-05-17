package com.backend.template.domain.BangChamCong.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.security.DenyAll;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdataBangChamCongDTO {
    private Date ngayTao;
    private String ghiChu;
    private String chiNhanhEntity;
    private Boolean khoaBang;
}
