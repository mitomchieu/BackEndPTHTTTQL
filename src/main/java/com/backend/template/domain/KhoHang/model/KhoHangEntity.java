package com.backend.template.domain.KhoHang.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhoHangEntity {
    @Column
    @Id
    private String maKho;

    @Column
    private String tenKho;

    @Column
    private String diaChi;


}
