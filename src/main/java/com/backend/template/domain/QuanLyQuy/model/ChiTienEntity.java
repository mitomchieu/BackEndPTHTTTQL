package com.backend.template.domain.QuanLyQuy.model;

import com.backend.template.modules.core.user.model.User;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
public class ChiTienEntity extends GiaoDichEntity {


    ChiTienEntity(String maGiaoDich, String doiTuongGiaoDich, Date thoiGianGiaoDich, Date thoiGianHachToan, String lyDoNop, User user, Long tongTien) {
        super(maGiaoDich, doiTuongGiaoDich, thoiGianGiaoDich, thoiGianHachToan, lyDoNop, tongTien, ELoaiGiaoDich.CHITIEN, user ,null);
    }

    public ChiTienEntity() {

    }
}
