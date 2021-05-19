package com.backend.template.domain.QuanLyHoatDongBanHang.service;

import com.backend.template.domain.QuanLyHoatDongBanHang.model.DonHangEntity;

import java.util.List;

public interface DonHangService {
    List<DonHangEntity> findAll();

    List<DonHangEntity> searchByNhanVien(String nv);

    DonHangEntity searchByMaDonHang(Long id);

    DonHangEntity save(DonHangEntity donHang);

    DonHangEntity delete(DonHangEntity donHang);
}
