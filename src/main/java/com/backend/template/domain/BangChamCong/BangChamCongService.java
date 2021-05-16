package com.backend.template.domain.BangChamCong;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.domain.BangChamCong.model.*;
import com.backend.template.domain.NhanVien.model.NhanVienEntity;
import com.backend.template.domain.NhanVien.model.QNhanVienEntity;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    @Transactional(rollbackOn = BackendError.class)
    public NhanVienInBangChamCongEntity themNhanVienVaoBangChamCong(
            Long maBangChamCong,
            String maNhanVien,
            Double heSo) throws BackendError {
        em.joinTransaction();
        QNhanVienEntity qNhanVienEntity = QNhanVienEntity.nhanVienEntity;
        QNhanVienInBangChamCongEntity  qNhanVienInBangChamCongEntity = QNhanVienInBangChamCongEntity.nhanVienInBangChamCongEntity;
        JPAQueryFactory jpaQueryFactory = this.getJPAQueryFactory();
        NhanVienEntity nhanVienEntity = jpaQueryFactory.selectFrom(qNhanVienEntity)
                .where(qNhanVienEntity.maNhanVien.eq(maNhanVien))
                .fetchOne();
        BangChamCongEntity bangChamCongEntity = this.getBangChamCongById(maBangChamCong);
        if (bangChamCongEntity.getKhoaBang().equals(true)) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Bảng đã bị khóa");
        }
        if (Objects.isNull(nhanVienEntity) || Objects.isNull(bangChamCongEntity)) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Mã bảng hoặc nhân viên không hợp lệ");
        }
        if (! nhanVienEntity.getChiNhanh().getMaChiNhanh().equals(
                bangChamCongEntity.getChiNhanhEntity().getMaChiNhanh()
        )) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Nhân viên không trùng chi nhánh với bảng chấm công");
        }
        NhanVienInBangChamCongID nhanVienInBangChamCongID
                = new NhanVienInBangChamCongID(nhanVienEntity, bangChamCongEntity);
        NhanVienInBangChamCongEntity nhanVienInBangChamCongEntity
                = new NhanVienInBangChamCongEntity(heSo, nhanVienInBangChamCongID);
        em.persist(nhanVienInBangChamCongEntity);
        return  nhanVienInBangChamCongEntity;
    }

    @Transactional
    public Object xoaNhanVienKhoiBangChamCong(Long maBangChamCong, String maNhanVien) {
        QNhanVienInBangChamCongEntity qNhanVienInBangChamCongEntity
                = QNhanVienInBangChamCongEntity.nhanVienInBangChamCongEntity;
        return this.getJPAQueryFactory()
                .delete(qNhanVienInBangChamCongEntity)
                .where(
                        qNhanVienInBangChamCongEntity
                                .nhanVienInBangChamCongId
                                .bangChamCongEntity
                                .maBangChamCong
                                .eq(maBangChamCong)
                                .and(
                                        qNhanVienInBangChamCongEntity
                                        .nhanVienInBangChamCongId
                                        .nhanVienEntity
                                        .maNhanVien
                                        .eq(maNhanVien)
                                )
                ).execute();
    }

    public List<NhanVienInBangChamCongEntity> getAllNhanVienTrongBangChamCong(Long maBangChamCong) {
        QNhanVienInBangChamCongEntity qNhanVienInBangChamCongEntity = QNhanVienInBangChamCongEntity.nhanVienInBangChamCongEntity;
        List<NhanVienInBangChamCongEntity> result =  this.getJPAQueryFactory()
                .select(qNhanVienInBangChamCongEntity)
                .from(qNhanVienInBangChamCongEntity)
                .where(qNhanVienInBangChamCongEntity
                        .nhanVienInBangChamCongId
                        .bangChamCongEntity
                        .maBangChamCong
                        .eq(maBangChamCong)
                )
                .fetch();
        return  result;
    }
}
