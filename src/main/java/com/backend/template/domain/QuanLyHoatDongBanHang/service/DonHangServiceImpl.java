package com.backend.template.domain.QuanLyHoatDongBanHang.service;

import java.util.List;

import com.backend.template.domain.QuanLyHoatDongBanHang.model.DonHangEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DonHangServiceImpl implements DonHangService {
    @Autowired
    private DonHangRepository repo;

    @Override
    public Iterable<DonHangEntity> findAll() {
        return repo.findAll();
    }

    @Override
    public List<DonHangEntity> searchByNhanVien(String nv) {
        return repo.findByNhanVienBanContaining(nv);
    }

    @Override
    public DonHangEntity searchByMaDonHang(Long id) {
        return repo.getOne(id);
    }

    @Override
    public void save(DonHangEntity donHang) {
        repo.save(donHang);
    }

    @Override
    public void delete(DonHangEntity donHang) {
        repo.delete(donHang);
    }
}
