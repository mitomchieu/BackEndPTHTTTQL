package com.backend.template.domain.QuanLyQuy.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.backend.template.modules.core.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(columnDefinition =  "tinyint(1) default 1")
    private Boolean isTienMat = true;

    @ManyToOne()
    @JoinColumn(name = "userCreated")
    protected  User userCreated;

    @JsonIgnore
    @OneToMany(mappedBy = "giaoDich", fetch = FetchType.LAZY)
    protected Set<HachToanEntity> danhSachHachToan;
}
