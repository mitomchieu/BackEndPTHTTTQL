package com.backend.template.domain.NhanVien;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.domain.NhanVien.model.NhanVienEntity;
import com.backend.template.domain.NhanVien.model.QNhanVienEntity;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.apache.catalina.LifecycleState;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
public class NhanVienService extends BaseService<QNhanVienEntity> {
    public NhanVienService() {
        super(QNhanVienEntity.nhanVienEntity, NhanVienEntity.class.getName());
        entityPathBuilder = new PathBuilder<Object>(this.modelClass, "nhanVienEntity");
    }

    public NhanVienEntity getByMaNhanVien(
            String maNhanVien
    ) {
        return this.getJPAQueryFactory()
                .selectFrom(this.qModel)
                .where(this.qModel.maNhanVien.stringValue().contains(maNhanVien))
                .fetchOne();
    }

    public APIPagingResponse getAllByMaChiNhanh(
            String maChiNhanh,
            Pageable pageable,
            SearchParameter searchParameter
    ) throws BackendError {
        JPAQuery<?> jpaQuery = getJPAQueryFactory().selectFrom(this.qModel)
                .fetchAll()
                .where(getMultiSearchPredicate(searchParameter.getSearch()))
                .where(this.qModel
                        .chiNhanh
                        .maChiNhanh
                        .stringValue()
                        .contains(maChiNhanh));
        JPAQuery<?> jpaQueryCount = jpaQuery.clone();

        final int total = (int) jpaQueryCount.fetchCount();

        BaseService.queryPagable(jpaQuery, pageable, qModel);
        List<?> result = jpaQuery.select(qModel).fetch();
        return  new APIPagingResponse(Collections.singletonList(result), total);
    }

    @Transactional
    public NhanVienEntity createNhanVien(
            NhanVienEntity nhanVienEntity
    ) {
        em.joinTransaction();
        em.persist(nhanVienEntity);
        return nhanVienEntity;
    }
}
