package com.backend.template.domain.QuanLyQuy;

import com.backend.template.base.common.BaseService;
import com.backend.template.domain.QuanLyQuy.model.GiaoDichEntity;
import com.backend.template.domain.QuanLyQuy.model.HachToanEntity;
import com.backend.template.domain.QuanLyQuy.model.QGiaoDichEntity;
import com.backend.template.domain.QuanLyQuy.model.QHachToanEntity;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class HachToanService extends BaseService<QHachToanEntity> {
    public HachToanService() {
        super(QHachToanEntity.hachToanEntity, QHachToanEntity.class.getName());
        entityPathBuilder = new PathBuilder(this.modelClass, "hachToanEntity");
    }

    public HachToanEntity getByMaHachToan(Integer maHachToan) {
        JPAQueryFactory jpaQueryFactory = getJPAQueryFactory();
        HachToanEntity result = jpaQueryFactory.from(this.qModel)
                        .where(this.qModel.maHachToan.eq(maHachToan))
                        .select(this.qModel)
                        .fetchOne();
        return result;
    }

    @Transactional
    public HachToanEntity createHachToanEntity(HachToanEntity hachToanEntity) {
        em.joinTransaction();
        em.persist(hachToanEntity);
        return hachToanEntity;
    }

    public List<HachToanEntity> getHachToanByMaGiaoDich(String maGiaoDich) {
        JPAQueryFactory jpaQueryFactory = getJPAQueryFactory();
        List<HachToanEntity> result = jpaQueryFactory
                                        .selectFrom(this.qModel)
                                        .where(this.qModel.giaoDich.maGiaoDich.eq(maGiaoDich))
                                        .fetch();
        return  result;
    }
}
