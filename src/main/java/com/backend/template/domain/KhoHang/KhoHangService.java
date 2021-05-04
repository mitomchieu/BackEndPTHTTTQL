package com.backend.template.domain.KhoHang;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.domain.KhoHang.model.KhoHangEntity;
import com.backend.template.domain.KhoHang.model.QKhoHangEntity;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class KhoHangService extends BaseService<QKhoHangEntity> {

    public KhoHangService() {
        super(QKhoHangEntity.khoHangEntity, KhoHangEntity.class.getName());
        this.entityPathBuilder = new PathBuilder<>(this.modelClass, "khoHangEntity");
    }

    @Transactional
    public KhoHangEntity createKhoHang(KhoHangEntity khoHangEntity) {
        em.joinTransaction();
        em.persist(khoHangEntity);
        return khoHangEntity;
    }

    public KhoHangEntity getByMaKhoHang(String maKhoHang) {
        JPAQueryFactory jpaQueryFactory = this.getJPAQueryFactory();
        return jpaQueryFactory.selectFrom(this.qModel)
                        .where(this.qModel.maKho.stringValue().equalsIgnoreCase(maKhoHang))
                        .fetchOne();
    }

    


}
