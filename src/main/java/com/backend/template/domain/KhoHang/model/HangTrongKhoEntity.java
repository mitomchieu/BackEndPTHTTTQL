package com.backend.template.domain.KhoHang.model;

import com.backend.template.domain.HangHoa.model.HangHoaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HangTrongKhoEntity {
    @Column
    private Integer soLuong;

    @EmbeddedId
    private HangTrongKhoID hangTrongKhoID;
}
