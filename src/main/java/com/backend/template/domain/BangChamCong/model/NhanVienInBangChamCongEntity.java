package com.backend.template.domain.BangChamCong.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienInBangChamCongEntity {

    @Column
    private Double heSo;

    @EmbeddedId
    private NhanVienInBangChamCongID nhanVienInBangChamCongId;
}
