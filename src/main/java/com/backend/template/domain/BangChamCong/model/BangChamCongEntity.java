package com.backend.template.domain.BangChamCong.model;

import com.backend.template.domain.ChiNhanh.entity.ChiNhanhEntity;
import com.backend.template.domain.ThanhToanLuong.model.ThanhToanLuongEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BangChamCongEntity {
    @Temporal(TemporalType.DATE)
    private Date ngayTao;

    @Column
    private String ghiChu;

    @ManyToOne
    private ChiNhanhEntity chiNhanhEntity;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean khoaBang = false;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maBangChamCong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    @JsonIgnore
    private ThanhToanLuongEntity thanhToanLuongEntity;

    public ThanhToanLuongEntity getThanhToanLuongEntity() {
        thanhToanLuongEntity.setBangChamCongEntity(null);
        return  thanhToanLuongEntity;
    }
}
