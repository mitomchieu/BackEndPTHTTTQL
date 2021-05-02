package com.backend.template.domain.QuanLyQuy;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.domain.QuanLyQuy.model.GiaoDichEntity;
import com.backend.template.domain.QuanLyQuy.model.QThuTienEntity;
import com.backend.template.domain.QuanLyQuy.model.ThuTienEntity;
import com.backend.template.modules.core.auth.AuthController;
import com.backend.template.modules.core.user.UserService;
import com.backend.template.modules.core.user.model.User;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("ThuTienService")
public class ThuTienService extends BaseService<QThuTienEntity> {
    private UserService userService;

    @Autowired
    public ThuTienService(
            @Qualifier("userService") UserService userService) {
        super(QThuTienEntity.thuTienEntity, ThuTienEntity.class.getName());
        entityPathBuilder = new PathBuilder<>(this.modelClass, "thuTienEntity");
        this.userService = userService;
    }

    @Transactional
    public  ThuTienEntity createThuTienEntity(ThuTienEntity thuTienEntity) {
        String usernameCreated = AuthController.getCurrentUsername();
        User user = this.userService.getByUsername(usernameCreated);
        em.joinTransaction();
        GiaoDichEntity giaoDichEntity = thuTienEntity;
        giaoDichEntity.setUserCreated(user);
        em.persist(giaoDichEntity);
        return getJPAQueryFactory().selectFrom(qModel).where(qModel.maGiaoDich.eq(thuTienEntity.getMaGiaoDich())).fetchOne();
    }

    public ThuTienEntity getByMaGiaoDich(String maGiaoDich) {
        return  getJPAQueryFactory().selectFrom(qModel).where(qModel.maGiaoDich.eq(maGiaoDich)).fetchOne();
    }


    public APIPagingResponse getAll(
            Pageable pageable,
            SearchParameter searchParameter
    ) throws BackendError {
        JPAQuery<?>  jpaQuery = getJPAQueryFactory().selectFrom(qModel)
                .fetchAll()
                .where(getMultiSearchPredicate(searchParameter.getSearch()));
        JPAQuery<?> jpaQueryCount = jpaQuery.clone();

        final int total = (int) jpaQueryCount.fetchCount();

        BaseService.queryPagable(jpaQuery, pageable, qModel);
        List<ThuTienEntity> result = jpaQuery.select(qModel).fetch();
        return  new APIPagingResponse(Collections.singletonList(result), total);
    }
}
