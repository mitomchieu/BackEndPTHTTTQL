package com.backend.template.domain.QuanLyQuy.model;

import com.backend.template.modules.core.user.model.User;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class ChiTienEntity extends GiaoDichEntity {
    ChiTienEntity(String maGiaoDich, String doiTuongGiaoDich, Date thoiGianGiaoDich, Date thoiGianHachToan, String lyDoNop, String user, Long tongTien) {
        super(maGiaoDich, doiTuongGiaoDich, thoiGianGiaoDich, thoiGianHachToan, lyDoNop, tongTien, ELoaiGiaoDich.CHITIEN, user ,null);
    }

    public ChiTienEntity() {

    }
}
