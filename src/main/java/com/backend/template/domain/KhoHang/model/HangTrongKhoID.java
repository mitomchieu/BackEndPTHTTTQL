package com.backend.template.domain.KhoHang.model;

import com.backend.template.domain.HangHoa.model.HangHoaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HangTrongKhoID implements Serializable {
    @ManyToOne()
    public HangHoaEntity hangHoaEntity;
    @ManyToOne()
    public KhoHangEntity khoHangEntity;
}
