package com.backend.template.domain.QuanLyHoatDongBanHang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoanhThuEntity {
    @Column
    @Id
    private String maDoanhThu;

}
