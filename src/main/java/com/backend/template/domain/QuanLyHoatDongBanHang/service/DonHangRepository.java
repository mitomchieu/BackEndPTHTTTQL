package com.backend.template.domain.QuanLyHoatDongBanHang.service;
import java.util.List;

import com.backend.template.domain.QuanLyHoatDongBanHang.model.DonHangEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonHangRepository extends  JpaRepository<DonHangEntity, Long>{
    List<DonHangEntity> findByNhanVienBanContaining(String q);
}
