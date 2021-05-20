package com.backend.template.domain.TienLuong.model;

import com.backend.template.domain.NhanVien.model.NhanVienEntity;
import com.backend.template.domain.ThanhToanLuong.model.ThanhToanLuongEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TienLuongEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long maTienLuong;

    @ManyToOne
    @JsonIgnore
    private NhanVienEntity nhanVienEntity;

    @Column
    private Double tienLuong;

    @ManyToOne
    @JsonIgnore
    private ThanhToanLuongEntity thanhToanLuongEntity;


}
