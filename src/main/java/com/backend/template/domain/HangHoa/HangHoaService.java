package com.backend.template.domain.HangHoa;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.domain.HangHoa.model.HangHoaEntity;
import com.backend.template.domain.HangHoa.model.QHangHoaEntity;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.stereotype.Service;

@Service
public class HangHoaService extends BaseService<QHangHoaEntity> {

    public HangHoaService() {
        super(QHangHoaEntity.hangHoaEntity, HangHoaEntity.class.getName());
        this.entityPathBuilder = new PathBuilder<>(this.modelClass, "hangHoaEntity");
    }

    @Transactional
    public HangHoaEntity createHangHoa(HangHoaEntity hangHoaEntity){
        em.joinTransaction();
        em.persist(hangHoaEntity);
        return hangHoaEntity;
    }

    public HangHoaEntity getByMaHangHoa(String maHangHoa) {
        JPAQueryFactory jpaQueryFactory = this.getJPAQueryFactory();
        HangHoaEntity result = jpaQueryFactory
                    .selectFrom(this.qModel)
                    .where(this.qModel.maHangHoa.stringValue().equalsIgnoreCase(maHangHoa))
                    .fetchOne();
        return  result;
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
        List<HangHoaEntity> result = jpaQuery.select(qModel).fetch();
        return  new APIPagingResponse(Collections.singletonList(result), total);
    }

    Long deleteByMaHangHoa(String maHangHoa) {
        JPAQueryFactory jpaQueryFactory = getJPAQueryFactory();
        return jpaQueryFactory.delete(this.qModel)
                        .where(this.qModel.maHangHoa.stringValue().equalsIgnoreCase(maHangHoa))
                        .execute();
    }


}
