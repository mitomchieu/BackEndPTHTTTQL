package com.backend.template.domain.ThanhToanLuong;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.domain.BangChamCong.model.BangChamCongEntity;
import com.backend.template.domain.BangChamCong.model.NhanVienInBangChamCongEntity;
import com.backend.template.domain.BangChamCong.model.QNhanVienInBangChamCongEntity;
import com.backend.template.domain.BangLuong.BangLuongService;
import com.backend.template.domain.BangLuong.model.BangLuongEntity;
import com.backend.template.domain.NhanVien.NhanVienService;
import com.backend.template.domain.NhanVien.model.NhanVienEntity;
import com.backend.template.domain.ThanhToanLuong.model.QThanhToanLuongEntity;
import com.backend.template.domain.ThanhToanLuong.model.ThanhToanLuongEntity;
import com.backend.template.domain.ThanhToanLuong.repository.ThanhToanLuongRepository;
import com.backend.template.domain.TienLuong.model.QTienLuongEntity;
import com.backend.template.domain.TienLuong.model.TienLuongEntity;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThanhToanLuongService extends
        BaseService<QThanhToanLuongEntity> {

    @Autowired
    private  ThanhToanLuongRepository thanhToanLuongRepository;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private BangLuongService bangLuongService;

    public ThanhToanLuongService() {
        super(QThanhToanLuongEntity.thanhToanLuongEntity,
                ThanhToanLuongEntity.class.getName());
        entityPathBuilder = new PathBuilder<Object>(this.modelClass, "thanhToanLuongEntity");
    }

    public ThanhToanLuongEntity getByMaBangThanhToanLuong(
            Long maBangThanhToanLuong
    ) {
        ThanhToanLuongEntity result =  thanhToanLuongRepository.findByMaThanhToanLuong(maBangThanhToanLuong);
        return  result;
    }

    @Transactional
    public ThanhToanLuongEntity createThanhToanLuong(ThanhToanLuongEntity thanhToanLuongEntity) {
        em.joinTransaction();
        em.persist(thanhToanLuongEntity);
        return thanhToanLuongEntity;
    }

    @Transactional
    public  ThanhToanLuongEntity themVaoThanhToanLuong(
        ThanhToanLuongEntity thanhToanLuongEntity,
        BangChamCongEntity bangChamCongEntity
    ) throws BackendError {
        if (thanhToanLuongEntity.getTrangThaiThanhToan().name
                == ThanhToanLuongEntity.ETrangThaiThanhToan.DATHANHTOAN.name) {
            throw new BackendError(HttpStatus.BAD_REQUEST,
                    "Đã được thanh toán, không được tính lại");
        }
        em.joinTransaction();
        bangChamCongEntity.setThanhToanLuongEntity(thanhToanLuongEntity);
        em.merge(bangChamCongEntity);
        return thanhToanLuongEntity;
    }

    public List<TienLuongEntity> getTienLuongBymaThanhToanLuong(
            Long maThanhToanLuong
    ) {
        QTienLuongEntity qTienLuongEntity =
                QTienLuongEntity.tienLuongEntity;
        return  this.getJPAQueryFactory()
                .selectFrom(qTienLuongEntity)
                .where(qTienLuongEntity.thanhToanLuongEntity.maThanhToanLuong.eq(maThanhToanLuong))
                .fetch();
    }

    public List<TienLuongEntity> getTienLuongByMaNhanVien(
            String maNhanVien
    ) {
        QTienLuongEntity qTienLuongEntity =
                QTienLuongEntity.tienLuongEntity;
        return  this.getJPAQueryFactory()
                .selectFrom(qTienLuongEntity)
                .where(qTienLuongEntity.nhanVienEntity.maNhanVien.eq(maNhanVien))
                .fetch();
    }



    @Transactional
    public  Boolean tinhLuong(Long maThanhToanLuong) throws BackendError {
        ThanhToanLuongEntity thanhToanLuongEntity
                 = this.getByMaBangThanhToanLuong(maThanhToanLuong);
        return tinhLuong(thanhToanLuongEntity);
    }

    @Transactional
    public  Boolean traLuong(Long maThanHToanLuong) throws BackendError {
        em.joinTransaction();
        ThanhToanLuongEntity thanhToanLuongEntity
                = this.getByMaBangThanhToanLuong(maThanHToanLuong);
        if (thanhToanLuongEntity.getTrangThaiThanhToan().name
                == ThanhToanLuongEntity.ETrangThaiThanhToan.DATHANHTOAN.name) {
            throw new BackendError(HttpStatus.BAD_REQUEST,
                    "Đã được thanh toán, không thể thanh toán thêm nwuax");
        }
        thanhToanLuongEntity.setNgayThanhToan(new Date());
        thanhToanLuongEntity.setTrangThaiThanhToan(
                ThanhToanLuongEntity.ETrangThaiThanhToan.DATHANHTOAN
        );
        thanhToanLuongEntity.setTongTien(
                this.tinhTongTienTheoMaThanhToanLuong(maThanHToanLuong)
        );
        em.merge(thanhToanLuongEntity);
        return true;
    }

    public Double tinhTongTienTheoMaThanhToanLuong(
            Long maThanhToanLuong
    ) {
        QTienLuongEntity qTienLuongEntity
                = QTienLuongEntity.tienLuongEntity;
        Double result = this.getJPAQueryFactory()
                .from(qTienLuongEntity)
                .select(qTienLuongEntity.tienLuong.sum().as("tongTien"))
                .where(qTienLuongEntity
                .thanhToanLuongEntity
                .maThanhToanLuong
                .eq(maThanhToanLuong))
                .fetchOne();
        return result;
    }


    @Transactional
    public Boolean tinhLuong(ThanhToanLuongEntity thanhToanLuongEntity) throws BackendError {
        em.joinTransaction();
        if (thanhToanLuongEntity.getTrangThaiThanhToan().name
                == ThanhToanLuongEntity.ETrangThaiThanhToan.DATHANHTOAN.name) {
            throw new BackendError(HttpStatus.BAD_REQUEST,
                    "Đã được thanh toán, không được tính lại");
        }
        QNhanVienInBangChamCongEntity qNhanVienInBangChamCongEntity
                = QNhanVienInBangChamCongEntity.nhanVienInBangChamCongEntity;
        QTienLuongEntity qTienLuongEntity = QTienLuongEntity.tienLuongEntity;
        this.getJPAQueryFactory()
                .delete(qTienLuongEntity)
                .where(qTienLuongEntity
                        .thanhToanLuongEntity
                        .maThanhToanLuong
                        .eq(thanhToanLuongEntity
                                .getMaThanhToanLuong()))
        .execute();
        List<Long> listMaBangChamCong = thanhToanLuongEntity.getBangChamCongEntity()
                .stream()
                .map(bangChamCongEntity -> {
                    return bangChamCongEntity.getMaBangChamCong();
                })
                .collect(Collectors.toList());

        List<Tuple> listHeSo = this.getJPAQueryFactory()
                    .select(qNhanVienInBangChamCongEntity
                            .nhanVienInBangChamCongId
                            .nhanVienEntity
                            .maNhanVien,
                            qNhanVienInBangChamCongEntity
                        .heSo)
                    .from(qNhanVienInBangChamCongEntity)
                    .where(qNhanVienInBangChamCongEntity
                        .nhanVienInBangChamCongId
                        .bangChamCongEntity
                        .maBangChamCong
                        .in(listMaBangChamCong))
                    .groupBy(
                            qNhanVienInBangChamCongEntity
                            .nhanVienInBangChamCongId
                            .nhanVienEntity
                            .maNhanVien
                    )
                .fetch();
        for (Tuple  tongHeSo: listHeSo ) {
            String maNhanVen = (String) tongHeSo.toArray()[0];
            NhanVienEntity nhanVienEntity = nhanVienService.getByMaNhanVien(maNhanVen);
            BangLuongEntity bangLuongEntity = bangLuongService.getByMaNhanVien(maNhanVen);
            Double heSo = (Double) tongHeSo.toArray()[1];
            TienLuongEntity tienLuongEntity = TienLuongEntity
                    .builder()
                    .nhanVienEntity(nhanVienEntity)
                    .tienLuong(
                            bangLuongEntity.getTienLuongTheoNgay() * heSo
                    )
                    .thanhToanLuongEntity(thanhToanLuongEntity)
                    .build();
            em.persist(tienLuongEntity);
        }
        return true;
    }

}
