package com.backend.template.domain.BangLuong;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.domain.BangLuong.model.BangLuongEntity;
import com.backend.template.domain.BangLuong.model.QBangLuongEntity;
import com.backend.template.domain.NhanVien.model.NhanVienEntity;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BangLuongService extends BaseService<QBangLuongEntity>  {
    public BangLuongService() {
        super(QBangLuongEntity.bangLuongEntity, BangLuongEntity.class.getName());
        entityPathBuilder = new PathBuilder<Object>(this.modelClass, "bangLuongEntity");
    }

    @Transactional
    public BangLuongEntity createBangLuong(BangLuongEntity bangLuongEntity) {
        em.joinTransaction();
        em.persist(bangLuongEntity);
        return bangLuongEntity;
    }

    @Transactional
    public BangLuongEntity updateBangLuong(BangLuongEntity bangLuongEntity) {
        em.joinTransaction();
        em.merge(bangLuongEntity);
        return bangLuongEntity;
    }

    public  BangLuongEntity getByMaNhanVien(
            String maNhanVien
    ) {
        return  this.getJPAQueryFactory()
                .selectFrom(this.qModel)
                .where(this.qModel
                .nhanVienEntity
                .maNhanVien.eq(maNhanVien))
                .fetchOne();
    }

    public BangLuongEntity getByMaBangLuong(Long maBangLuong) {
        return this.getJPAQueryFactory()
                .selectFrom(this.qModel)
                .where(this.qModel.maBangLuong.eq(maBangLuong))
                .fetchOne();
    }
}
