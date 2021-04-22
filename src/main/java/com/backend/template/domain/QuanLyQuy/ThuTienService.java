package com.backend.template.domain.QuanLyQuy;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.domain.QuanLyQuy.model.GiaoDichEntity;
import com.backend.template.domain.QuanLyQuy.model.QThuTienEntity;
import com.backend.template.domain.QuanLyQuy.model.ThuTienEntity;
import com.backend.template.domain.QuanLyQuy.repository.ThuTienRepository;
import com.backend.template.modules.core.auth.AuthController;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.jpa.impl.JPAQuery;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service("ThuTienService")
public class ThuTienService extends BaseService<QThuTienEntity> {


    private ThuTienRepository thuTienRepository;
    @Autowired
    public ThuTienService(ThuTienRepository thuTienRepository) {
        super(QThuTienEntity.thuTienEntity);
        this.thuTienRepository = thuTienRepository;
    }

    @Transactional
    public  ThuTienEntity createThuTienEntity(ThuTienEntity thuTienEntity) {
        String userCreated = AuthController.getCurrentUsername();
        em.joinTransaction();
        GiaoDichEntity giaoDichEntity = thuTienEntity;
        giaoDichEntity.setUserCreated(userCreated);
        em.persist(giaoDichEntity);
        return getJPAQueryFactory().selectFrom(qModel).where(qModel.maGiaoDich.eq(thuTienEntity.getMaGiaoDich())).fetchOne();
    }

    public ThuTienEntity getByMaGiaoDich(String maGiaoDich) {
        return  getJPAQueryFactory().selectFrom(qModel).where(qModel.maGiaoDich.eq(maGiaoDich)).fetchOne();
    }

    public APIPagingResponse getAll(Pageable pageable) throws BackendError {
        JPAQuery  jpaQuery = getJPAQueryFactory().selectFrom(qModel);
        final int total = (int) jpaQuery.fetchCount();
        jpaQuery = getJPAQueryFactory().selectFrom(qModel);
        BaseService.queryPagable(jpaQuery, pageable, qModel);
        List<ThuTienEntity> result = jpaQuery.select(qModel).fetch();
        return  new APIPagingResponse(Collections.singletonList(result), total);
    }
}
