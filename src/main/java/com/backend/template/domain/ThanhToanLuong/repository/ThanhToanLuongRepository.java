package com.backend.template.domain.ThanhToanLuong.repository;


import com.backend.template.domain.ThanhToanLuong.ThanhToanLuongService;
import com.backend.template.domain.ThanhToanLuong.model.ThanhToanLuongEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThanhToanLuongRepository extends CrudRepository<ThanhToanLuongEntity, Long> {
    ThanhToanLuongEntity findByMaThanhToanLuong(Long maThanhToanLuong);
}
