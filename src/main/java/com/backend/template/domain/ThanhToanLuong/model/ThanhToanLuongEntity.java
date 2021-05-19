package com.backend.template.domain.ThanhToanLuong.model;

import com.backend.template.domain.BangChamCong.model.BangChamCongEntity;
import com.backend.template.domain.ChiNhanh.entity.ChiNhanhEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ThanhToanLuongEntity {

    public  enum ETrangThaiThanhToan {
        DATHANHTOAN("DATHANHTOAN"),
        CHUATHANHTOAN("CHUATHANHTOAN");
        public String name;
        ETrangThaiThanhToan(String name) {
            this.name = name;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long maThanhToanLuong;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date ngayTao;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date ngayThanhToan;

    @Enumerated(EnumType.STRING)
    @Column(name = "trangThaiTHanhToan")
    private  ETrangThaiThanhToan trangThaiThanhToan;

    @Column
    private Long tongTien;

    @OneToMany(mappedBy = "thanhToanLuongEntity")
    private Set<BangChamCongEntity> bangChamCongEntity;

    @ManyToOne
    private ChiNhanhEntity chiNhanhEntity;
}
