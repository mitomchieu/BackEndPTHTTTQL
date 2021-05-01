package com.backend.template.domain.QuanLyQuy;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.domain.QuanLyQuy.model.GiaoDichEntity;
import com.backend.template.domain.QuanLyQuy.model.QThuTienEntity;
import com.backend.template.domain.QuanLyQuy.model.ThuTienEntity;
import com.backend.template.domain.QuanLyQuy.repository.ThuTienRepository;
import com.backend.template.modules.core.auth.AuthController;
import com.backend.template.modules.core.user.UserService;
import com.backend.template.modules.core.user.model.User;
import com.ibm.icu.impl.locale.AsciiUtil;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;
import java.util.Collections;
import java.util.List;

@Service("ThuTienService")
public class ThuTienService extends BaseService<QThuTienEntity> {


    private ThuTienRepository thuTienRepository;
    private UserService userService;


    @Autowired
    public ThuTienService(
            ThuTienRepository thuTienRepository,
            @Qualifier("userService") UserService userService) {
        super(QThuTienEntity.thuTienEntity, ThuTienEntity.class.getName());
        entityPathBuilder = new PathBuilder<>(this.modelClass, "thuTienEntity");
        this.thuTienRepository = thuTienRepository;
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

    public List<ThuTienEntity> testGet() {
        JPAQuery jpaQuery = getJPAQueryFactory().selectFrom(qModel);
        EnumPath path = entityPathBuilder.getEnum("loaiGiaoDich", Enum.class);
        jpaQuery.where(path.stringValue().eq("THUTIEN"));
        jpaQuery.select(qModel);
        return jpaQuery.fetch();
    }

    public APIPagingResponse getAll(
            Pageable pageable,
            SearchParameter searchParameter
    ) throws BackendError {
        JPAQuery  jpaQuery = getJPAQueryFactory().selectFrom(qModel)
                .fetchAll()
                .where(getMultiSearchPredicate(searchParameter.getSearch()));
        JPAQuery jpaQueryCount = (JPAQuery) jpaQuery.clone();

        final int total = (int) jpaQueryCount.fetchCount();

        BaseService.queryPagable(jpaQuery, pageable, qModel);
        List<ThuTienEntity> result = jpaQuery.select(qModel).fetch();
        return  new APIPagingResponse(Collections.singletonList(result), total);
    }
}
