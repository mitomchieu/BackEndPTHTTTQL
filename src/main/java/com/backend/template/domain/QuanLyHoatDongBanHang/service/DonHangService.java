package com.backend.template.domain.QuanLyHoatDongBanHang.service;

import com.backend.template.domain.QuanLyHoatDongBanHang.model.DonHangEntity;

import java.util.List;

public interface DonHangService {
    Iterable<DonHangEntity> findAll();

    List<DonHangEntity> searchByNhanVien(String nv);

    DonHangEntity searchByMaDonHang(Long id);

    void save(DonHangEntity donHang);

    void delete(DonHangEntity donHang);
}
