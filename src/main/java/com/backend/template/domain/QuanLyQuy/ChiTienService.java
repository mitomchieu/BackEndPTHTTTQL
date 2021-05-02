package com.backend.template.domain.QuanLyQuy;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.domain.QuanLyQuy.model.ChiTienEntity;
import com.backend.template.domain.QuanLyQuy.model.QChiTienEntity;
import com.backend.template.modules.core.auth.AuthController;
import com.backend.template.modules.core.user.UserService;
import com.backend.template.modules.core.user.model.User;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChiTienService extends BaseService<QChiTienEntity> {

    @Autowired
    private UserService userService;

    public ChiTienService() {
        super(QChiTienEntity.chiTienEntity, ChiTienEntity.class.getName());
        entityPathBuilder = new PathBuilder<>(this.modelClass, "chiTienEntity");
    }

    @Transactional
    public ChiTienEntity createChiTienEntity(ChiTienEntity chiTienEntity) {
        String usernameCreated = AuthController.getCurrentUsername();
        User user = this.userService.getByUsername(usernameCreated);
        em.joinTransaction();
        chiTienEntity.setUserCreated(user);
        em.persist(chiTienEntity);
        return getJPAQueryFactory().selectFrom(qModel).where(qModel.maGiaoDich.eq(chiTienEntity.getMaGiaoDich())).fetchOne();
    }

    public ChiTienEntity getByMaGiaoDich(String maGiaoDich) {
        return  getJPAQueryFactory().selectFrom(qModel).where(qModel.maGiaoDich.eq(maGiaoDich)).fetchOne();
    }

    public APIPagingResponse getAll(
            Pageable pageable,
            SearchParameter searchParameter
    ) throws BackendError {
        JPAQuery<?> jpaQuery = getJPAQueryFactory().selectFrom(qModel)
                .fetchAll()
                .where(getMultiSearchPredicate(searchParameter.getSearch()));
        JPAQuery<?> jpaQueryCount = jpaQuery.clone();

        final int total = (int) jpaQueryCount.fetchCount();

        BaseService.queryPagable(jpaQuery, pageable, qModel);
        List<?> result = jpaQuery.select(qModel).fetch();
        return  new APIPagingResponse(Collections.singletonList(result), total);
    }
}
