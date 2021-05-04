package com.backend.template.domain.KhoHang;

import com.backend.template.base.common.BaseService;
import com.backend.template.domain.KhoHang.model.HangTrongKhoEntity;
import com.backend.template.domain.KhoHang.model.PhieuKhoEntity;
import com.backend.template.domain.KhoHang.model.QHangTrongKhoEntity;
import com.backend.template.domain.KhoHang.model.QPhieuKhoEntity;
import com.querydsl.core.Query;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        em.joinTransaction();
        QHangTrongKhoEntity qHangTrongKhoEntity = QHangTrongKhoEntity.hangTrongKhoEntity;
        JPAQueryFactory jpaQueryFactory = this.getJPAQueryFactory();
        return jpaQueryFactory.selectFrom(qHangTrongKhoEntity)
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
    }

    @Transactional
    public void updateHangTrongKho(PhieuKhoEntity phieuKhoEntity) {
        em.joinTransaction();
        HangTrongKhoEntity hangTrongKhoEntity = getHangTrongKhoByMaKhoAndMaHang(
                                                phieuKhoEntity.getKhoNhap().getMaKho(),
                                                phieuKhoEntity.getMatHang().getMaHangHoa());
        if (phieuKhoEntity.getLoaiPhieuKho().name == PhieuKhoEntity.ELoaiPhieuKho.NHAPKHO.name) {
            hangTrongKhoEntity.setSoLuong(hangTrongKhoEntity.getSoLuong() + phieuKhoEntity.getSoLuong());
        }
        if (phieuKhoEntity.getLoaiPhieuKho().name == PhieuKhoEntity.ELoaiPhieuKho.XUATKHO.name) {
            hangTrongKhoEntity.setSoLuong(hangTrongKhoEntity.getSoLuong() - phieuKhoEntity.getSoLuong());
        }
        if (phieuKhoEntity.getLoaiPhieuKho().name == PhieuKhoEntity.ELoaiPhieuKho.NHAPKHO.name) {
            hangTrongKhoEntity.setSoLuong(hangTrongKhoEntity.getSoLuong() - phieuKhoEntity.getSoLuong());
            HangTrongKhoEntity hangTrongKhoDuocChuyen = getHangTrongKhoByMaKhoAndMaHang(
                                                phieuKhoEntity.getKhoDuocChuyen().getMaKho(),
                                                phieuKhoEntity.getMatHang().getMaHangHoa());
            hangTrongKhoDuocChuyen.setSoLuong(hangTrongKhoDuocChuyen.getSoLuong() + phieuKhoEntity.getSoLuong());
            em.merge(hangTrongKhoDuocChuyen);
        }
        em.merge(hangTrongKhoEntity);
    }

    @Transactional
    public PhieuKhoEntity createPhieuKho(PhieuKhoEntity phieuKhoEntity) {
        em.joinTransaction();
        em.persist(phieuKhoEntity);
        updateHangTrongKho(phieuKhoEntity);
        return phieuKhoEntity;
    }
}

