package com.backend.template.domain.BangChamCong.model;

import com.backend.template.domain.NhanVien.model.NhanVienEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienInBangChamCongID implements Serializable {

    @ManyToOne
    public NhanVienEntity nhanVienEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public BangChamCongEntity bangChamCongEntity;
}
