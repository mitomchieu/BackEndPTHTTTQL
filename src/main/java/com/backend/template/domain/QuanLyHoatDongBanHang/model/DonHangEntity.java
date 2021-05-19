package com.backend.template.domain.QuanLyHoatDongBanHang.model;

import com.backend.template.domain.NhanVien.model.NhanVienEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonHangEntity {
    @Column
    @Id
    private Long maDonHang;

    @Column
    private String tenKhachHang;

    @Column
    private String diaChi;

    @Column
    private String soDienThoai;

    @Column
    private String tenMatHang;

    @Column
    private String thanhTien;

    @Column
    private Date ngayXuatDon;

    @Column
    private String nhanVienBan;
}
