package com.backend.template.domain.QuanLyQuy.model;

import javax.persistence.*;

@Entity
public class HachToanEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String id;
    private String đienGiai;
    private String taiKhoanNo;
    private String taiKhoanCo;
    private Long soTien;
    private String doiTuong;

    @ManyToOne()
    @JoinColumn(name =  "maGiaoDich", nullable = false)
    private GiaoDichEntity giaoDich;
}
