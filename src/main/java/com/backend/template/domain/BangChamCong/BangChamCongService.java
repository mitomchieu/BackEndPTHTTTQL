package com.backend.template.domain.BangChamCong;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.domain.BangChamCong.model.BangChamCongEntity;
import com.backend.template.domain.BangChamCong.model.QBangChamCongEntity;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class BangChamCongService extends BaseService<QBangChamCongEntity> {
    public BangChamCongService() {
        super(QBangChamCongEntity.bangChamCongEntity, BangChamCongEntity.class.getName());
        entityPathBuilder = new PathBuilder<Object>(this.modelClass, "bangChamCongEntity");
    }

    public BangChamCongEntity getBangChamCongById(
            Long maBangChamCong
    ) {
        return this.getJPAQueryFactory()
                .selectFrom(qModel)
                .where(qModel
                        .maBangChamCong
                        .eq(maBangChamCong))
                .fetchOne();
    }

    @Transactional
    public BangChamCongEntity updateBangChamCong(
        BangChamCongEntity bangChamCongEntity
    ) {
        em.joinTransaction();
        em.merge(bangChamCongEntity);
        return this.getBangChamCongById(bangChamCongEntity.getMaBangChamCong());
    }


    @Transactional
    public BangChamCongEntity createBangChamCong(BangChamCongEntity bangChamCongEntity) {
        em.joinTransaction();
        em.persist(bangChamCongEntity);
        return this.getBangChamCongById(bangChamCongEntity.getMaBangChamCong());
    }


    public APIPagingResponse getBangChamCongByMaChiNhanh(
            String maChiNhanh,
            Pageable pageable,
            SearchParameter searchParameter) throws BackendError {
        JPAQuery<?> jpaQuery = getJPAQueryFactory().selectFrom(this.qModel)
                .fetchAll()
                .where(getMultiSearchPredicate(searchParameter.getSearch()))
                .where(this.qModel
                        .chiNhanhEntity
                        .maChiNhanh
                        .stringValue()
                        .contains(maChiNhanh));
        JPAQuery<?> jpaQueryCount = jpaQuery.clone();
        final int total = (int) jpaQueryCount.fetchCount();

        BaseService.queryPagable(jpaQuery, pageable, qModel);
        List<?> result = jpaQuery.select(qModel).fetch();
        return  new APIPagingResponse(Collections.singletonList(result), total);
    }
}
