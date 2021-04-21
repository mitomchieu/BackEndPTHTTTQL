package com.backend.template.domain.QuanLyQuy;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.domain.QuanLyQuy.model.GiaoDichEntity;
import com.backend.template.domain.QuanLyQuy.model.QThuTienEntity;
import com.backend.template.domain.QuanLyQuy.model.ThuTienEntity;
import com.backend.template.domain.QuanLyQuy.repository.ThuTienRepository;
import com.backend.template.modules.core.auth.AuthController;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Set;

@Service("ThuTienService")
public class ThuTienService extends BaseService<QThuTienEntity> {


    private ThuTienRepository thuTienRepository;
    @Autowired
    public ThuTienService(ThuTienRepository thuTienRepository) {
        super(QThuTienEntity.thuTienEntity);
        this.thuTienRepository = thuTienRepository;
    }

    @Transactional
    public  ThuTienEntity createThuTienEntity(ThuTienEntity thuTienEntity) {
        String userCreated = AuthController.getCurrentUsername();
        em.joinTransaction();
        GiaoDichEntity giaoDichEntity = thuTienEntity;
        giaoDichEntity.setUserCreated(userCreated);
        em.persist(giaoDichEntity);
        return getJPAQueryFactory().selectFrom(qModel).where(qModel.maGiaoDich.eq(thuTienEntity.getMaGiaoDich())).fetchOne();
    }

    public ThuTienEntity getByMaGiaoDich(String maGiaoDich) {
        return  getJPAQueryFactory().selectFrom(qModel).where(qModel.maGiaoDich.eq(maGiaoDich)).fetchOne();
    }

    public APIPagingResponse getAll(Pageable pageable) {
        JPAQuery  jpaQuery = getJPAQueryFactory().selectFrom(qModel);
        final int total = (int) jpaQuery.fetchCount();
//        if (pageable != null) {
//            if (pageable.getSize() == null) {
//                pageable.setSize(10);
//            }
//            if (pageable.getPage() == null) {
//                pageable.setPage(1);
//            }
//            final Integer size = pageable.getSize();
//            final int offset = Math.max(pageable.getPage()-1, 0)*size;
//            jpaQuery.offset(offset);
//            jpaQuery.limit(size);
//            pageable.getSort();
//            for (String orderBy : pageable.getSort()) {
//                jpaQuery.orderBy(orderBy);
//            }
//        }
        PageRequest paging = PageRequest.of(pageable.getPage(), pageable.getSize());
        Page<ThuTienEntity> result = thuTienRepository.findAll(paging);
        return  new APIPagingResponse(Collections.singletonList(result.getContent()), total);
    }
}
