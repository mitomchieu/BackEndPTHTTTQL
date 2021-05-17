package com.backend.template.domain.BangLuong.model;

import com.backend.template.domain.NhanVien.model.NhanVienEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BangLuongEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long maBangLuong;

    @Column
    private Long tienLuongTheoNgay;

    @OneToOne
    @JoinColumn(name = "ma_nhan_vien", unique = true)
    private NhanVienEntity nhanVienEntity;
}
