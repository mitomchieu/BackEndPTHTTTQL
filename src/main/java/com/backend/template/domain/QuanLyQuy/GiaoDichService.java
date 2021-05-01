package com.backend.template.domain.QuanLyQuy;

import com.backend.template.base.common.BaseService;
import com.backend.template.domain.QuanLyQuy.model.GiaoDichEntity;
import com.backend.template.domain.QuanLyQuy.model.QGiaoDichEntity;
import com.backend.template.domain.QuanLyQuy.model.QThuTienEntity;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

@Service
public class GiaoDichService extends BaseService<QGiaoDichEntity> {

    public GiaoDichService() {
        super(QGiaoDichEntity.giaoDichEntity, GiaoDichEntity.class.getName());
        entityPathBuilder = new PathBuilder(this.modelClass, "giaoDichEntity");
    }

    public GiaoDichEntity getBybId(String maGiaoDich) {
        JPAQueryFactory jpaQueryFactory = this.getJPAQueryFactory();
        final  GiaoDichEntity result = jpaQueryFactory
                        .from(this.qModel)
                        .where(this.qModel.maGiaoDich.stringValue().contains(maGiaoDich))
                        .select(this.qModel)
                        .fetchOne();
        return result;
    }
}
