package com.backend.template.domain.BangChamCong.model;

import com.backend.template.domain.ChiNhanh.entity.ChiNhanhEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maBangChamCong;

}
