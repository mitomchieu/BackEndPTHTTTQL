package com.backend.template.domain.ThanhToanLuong;

import com.backend.template.base.common.BaseService;
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
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    ) {
        em.joinTransaction();
        bangChamCongEntity.setThanhToanLuongEntity(thanhToanLuongEntity);
        em.merge(bangChamCongEntity);
        return thanhToanLuongEntity;
    }

    @Transactional
    public  Boolean tinhLuong(Long maThanhToanLuong) {
        ThanhToanLuongEntity thanhToanLuongEntity
                 = this.getByMaBangThanhToanLuong(maThanhToanLuong);
        return tinhLuong(thanhToanLuongEntity);
    }

    @Transactional
    public Boolean tinhLuong(ThanhToanLuongEntity thanhToanLuongEntity) {
        em.joinTransaction();
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
            System.out.println(tongHeSo);
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

    public TienLuongEntity createTienLuongEntity() {
        return  null;
    }

}
