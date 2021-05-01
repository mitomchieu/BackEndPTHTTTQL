package com.backend.template.domain.QuanLyQuy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HachToanEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int maHachToan;
    @Column
    private String dienGiai;
    @Column
    private Long taiKhoanNo;
    @Column
    private Long taiKhoanCo;
    @Column
    private Long soTien;
    @Column
    private String doiTuong;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name =  "maGiaoDich", nullable = false)
    private GiaoDichEntity giaoDich;
}
