package com.backend.template.domain.QuanLyQuy.model;

import com.backend.template.modules.core.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class GiaoDichEntity {

    public enum ELoaiGiaoDich {
        THUTIEN("THUTIEN"),
        CHITIEN ("CHITIEN");
        public  String name;
        ELoaiGiaoDich(String name) {
            this.name = name;
        }
    };

    @Id
    @Column(unique = true)
    protected String maGiaoDich;

    @Column
    protected String doiTuongGiaoDich;

    @Temporal(TemporalType.DATE)
    protected Date thoiGianGiaoDich;


    @Temporal(TemporalType.DATE)
    protected  Date thoiGianHachToan;

    @Column
    protected  String lyDo;

    @Column
    protected  Long tongTien;

    @Enumerated(EnumType.STRING)
    @Column(name = "loaiGiaoDich")
    protected  ELoaiGiaoDich loaiGiaoDich;

    @ManyToOne()
    @JoinColumn(name = "userCreated")
    protected  User userCreated;

    @OneToMany(mappedBy = "giaoDich")
    protected Set<HachToanEntity> danhSachHachToan;
}
