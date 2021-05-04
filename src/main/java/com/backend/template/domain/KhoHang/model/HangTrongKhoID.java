package com.backend.template.domain.KhoHang.model;

import com.backend.template.domain.HangHoa.model.HangHoaEntity;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class HangTrongKhoID implements Serializable {
    @ManyToOne()
    public HangHoaEntity hangHoaEntity;
    @ManyToOne()
    public KhoHangEntity khoHangEntity;
}
