package com.backend.template.domain.HangHoa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HangHoaEntity {
    @Id
    @Column(unique = true)
    private  String maHangHoa;

    @Column
    private String tenHangHoa;

    @Column
    private Long thoiHanBaoHanh;

    @Column
    private String nguonGoc;

    @Column
    private String moTa;
}
