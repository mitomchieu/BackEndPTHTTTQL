package com.backend.template.domain.ChiNhanh;

import com.backend.template.base.common.BaseService;
import com.backend.template.domain.ChiNhanh.entity.ChiNhanhEntity;
import com.backend.template.domain.ChiNhanh.entity.QChiNhanhEntity;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ChiNhanhService extends BaseService<QChiNhanhEntity> {
    public ChiNhanhService() {
        super(QChiNhanhEntity.chiNhanhEntity, ChiNhanhEntity.class.getName());
        entityPathBuilder = new PathBuilder<Object>(this.modelClass, "chiNhanhEntity");
    }

    public ChiNhanhEntity getChiNhanhByMaChiNhanhh(String maChiNhanh) {
        return getJPAQueryFactory()
                .selectFrom(this.qModel)
                .where(qModel.maChiNhanh.stringValue().contains(maChiNhanh))
                .fetchOne();
    }

    @Transactional
    public ChiNhanhEntity createChiNhanh(ChiNhanhEntity chiNhanhEntity) {
        em.joinTransaction();
        em.persist(chiNhanhEntity);
        return chiNhanhEntity;
    }
}
