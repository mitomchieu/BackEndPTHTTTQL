package com.backend.template.domain.KhoHang;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.domain.HangHoa.model.HangHoaEntity;
import com.backend.template.domain.HangHoa.model.QHangHoaEntity;
import com.backend.template.domain.KhoHang.model.*;
import com.querydsl.core.Query;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class PhieuKhoService extends BaseService<QPhieuKhoEntity> {
    public PhieuKhoService() {
        super(QPhieuKhoEntity.phieuKhoEntity, "phieuKhoEntity");
        entityPathBuilder = new PathBuilder<>(this.modelClass, "phieuKhoEntity");
    }

    @Transactional
    public HangTrongKhoEntity getHangTrongKhoByMaKhoAndMaHang(
            String maKho,
            String maHang
    ) {
        QHangTrongKhoEntity qHangTrongKhoEntity = QHangTrongKhoEntity.hangTrongKhoEntity;
        JPAQueryFactory jpaQueryFactory = this.getJPAQueryFactory();
        HangTrongKhoEntity hangTrongKhoEntity = jpaQueryFactory.selectFrom(qHangTrongKhoEntity)
                .where(qHangTrongKhoEntity
                        .hangTrongKhoID
                        .khoHangEntity
                        .maKho
                        .stringValue()
                        .equalsIgnoreCase(maKho)
                        .and(
                                qHangTrongKhoEntity
                                        .hangTrongKhoID
                                        .hangHoaEntity
                                        .maHangHoa
                                        .stringValue()
                                        .equalsIgnoreCase(maHang)
                        )
                ).fetchOne();
        if (Objects.isNull(hangTrongKhoEntity)) {
            QHangHoaEntity qHangHoaEntity = QHangHoaEntity.hangHoaEntity;
            QKhoHangEntity qKhoHangEntity = QKhoHangEntity.khoHangEntity;
            HangHoaEntity hangHoaEntity = jpaQueryFactory
                    .selectFrom(qHangHoaEntity)
                    .where(qHangHoaEntity
                            .maHangHoa
                            .stringValue()
                            .equalsIgnoreCase(maHang)
                    ).fetchOne();
            KhoHangEntity khoHangEntity = jpaQueryFactory
                    .selectFrom(qKhoHangEntity)
                    .where(qKhoHangEntity
                        .maKho
                        .stringValue()
                        .equalsIgnoreCase(maKho)
                    ).fetchOne();
            HangTrongKhoID hangTrongKhoID = new HangTrongKhoID(hangHoaEntity, khoHangEntity);
            hangTrongKhoEntity = new HangTrongKhoEntity(0, hangTrongKhoID);
            em.persist(hangTrongKhoEntity);
        }
        return hangTrongKhoEntity;
    }

    @Transactional
    public void updateHangTrongKho(PhieuKhoEntity phieuKhoEntity) throws BackendError {
        HangTrongKhoEntity hangTrongKhoEntity = getHangTrongKhoByMaKhoAndMaHang(
                                                phieuKhoEntity.getKhoNhap().getMaKho(),
                                                phieuKhoEntity.getMatHang().getMaHangHoa());
        if (phieuKhoEntity.getLoaiPhieuKho().name == PhieuKhoEntity.ELoaiPhieuKho.NHAPKHO.name) {
            hangTrongKhoEntity.setSoLuong(hangTrongKhoEntity.getSoLuong() + phieuKhoEntity.getSoLuong());
        }
        if (phieuKhoEntity.getLoaiPhieuKho().name == PhieuKhoEntity.ELoaiPhieuKho.XUATKHO.name) {
            hangTrongKhoEntity.setSoLuong(hangTrongKhoEntity.getSoLuong() - phieuKhoEntity.getSoLuong());
        }
        if (phieuKhoEntity.getLoaiPhieuKho().name == PhieuKhoEntity.ELoaiPhieuKho.CHUYENKHO.name) {
            hangTrongKhoEntity.setSoLuong(hangTrongKhoEntity.getSoLuong() - phieuKhoEntity.getSoLuong());
            HangTrongKhoEntity hangTrongKhoDuocChuyen = getHangTrongKhoByMaKhoAndMaHang(
                                                phieuKhoEntity.getKhoDuocChuyen().getMaKho(),
                                                phieuKhoEntity.getMatHang().getMaHangHoa());
            hangTrongKhoDuocChuyen.setSoLuong(hangTrongKhoDuocChuyen.getSoLuong() + phieuKhoEntity.getSoLuong());
            em.merge(hangTrongKhoDuocChuyen);
        }
        if (hangTrongKhoEntity.getSoLuong() < 0) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Số lượng nhập không hợp lệ");
        }
        em.merge(hangTrongKhoEntity);
    }

    @Transactional(rollbackOn = BackendError.class)
    public PhieuKhoEntity createPhieuKho(PhieuKhoEntity phieuKhoEntity) throws BackendError {
        em.joinTransaction();
        em.persist(phieuKhoEntity);
        updateHangTrongKho(phieuKhoEntity);
        return phieuKhoEntity;
    }
}

