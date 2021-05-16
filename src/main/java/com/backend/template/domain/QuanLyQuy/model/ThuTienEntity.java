package com.backend.template.domain.QuanLyQuy.model;

import com.backend.template.modules.core.user.model.User;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class ThuTienEntity extends GiaoDichEntity {

    public ThuTienEntity() {
        this.loaiGiaoDich = ELoaiGiaoDich.THUTIEN;
    }
}
