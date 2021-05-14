package com.backend.template.domain.BangChamCong;

import com.backend.template.base.common.BaseService;
import com.backend.template.domain.BangChamCong.model.BangChamCongEntity;
import com.backend.template.domain.BangChamCong.model.QBangChamCongEntity;
import com.querydsl.core.types.dsl.PathBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    public BangChamCongEntity createBangChamCong(BangChamCongEntity bangChamCongEntity) {
        em.joinTransaction();
        em.persist(bangChamCongEntity);
        return this.getBangChamCongById(bangChamCongEntity.getMaBangChamCong());
    }

}
