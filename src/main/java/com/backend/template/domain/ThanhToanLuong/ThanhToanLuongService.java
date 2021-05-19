package com.backend.template.domain.ThanhToanLuong;

import com.backend.template.base.common.BaseService;
import com.backend.template.domain.BangChamCong.model.BangChamCongEntity;
import com.backend.template.domain.ThanhToanLuong.model.QThanhToanLuongEntity;
import com.backend.template.domain.ThanhToanLuong.model.ThanhToanLuongEntity;
import com.backend.template.domain.ThanhToanLuong.repository.ThanhToanLuongRepository;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ThanhToanLuongService extends
        BaseService<QThanhToanLuongEntity> {

    @Autowired
    private  ThanhToanLuongRepository thanhToanLuongRepository;

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

}
