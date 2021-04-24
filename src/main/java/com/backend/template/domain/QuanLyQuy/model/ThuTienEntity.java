package com.backend.template.domain.QuanLyQuy.model;

import com.backend.template.modules.core.user.model.User;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class ThuTienEntity extends GiaoDichEntity {
    ThuTienEntity(String maGiaoDich, String nguoiNop, Date thoiGianGiaoDich, Date thoiGianHachToan, String lyDoNop, Long tongTien, User user) {
        super(maGiaoDich, nguoiNop, thoiGianGiaoDich, thoiGianHachToan, lyDoNop, tongTien, ELoaiGiaoDich.THUTIEN, user, null);
    }

    public ThuTienEntity() {
        this.loaiGiaoDich = ELoaiGiaoDich.THUTIEN;
    }
}
