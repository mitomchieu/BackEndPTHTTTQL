package com.backend.template.domain.KhoHang;

import com.backend.template.base.common.BaseService;
import com.backend.template.base.common.ParameterObject.SearchParameter;
import com.backend.template.base.common.exception.BackendError;
import com.backend.template.base.common.response.model.APIPagingResponse;
import com.backend.template.domain.HangHoa.model.HangHoaEntity;
import com.backend.template.domain.HangHoa.model.QHangHoaEntity;
import com.backend.template.domain.KhoHang.model.HangTrongKhoEntity;
import com.backend.template.domain.KhoHang.model.KhoHangEntity;
import com.backend.template.domain.KhoHang.model.QHangTrongKhoEntity;
import com.backend.template.domain.KhoHang.model.QKhoHangEntity;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KhoHangService extends BaseService<QKhoHangEntity> {

    public KhoHangService() {
        super(QKhoHangEntity.khoHangEntity, KhoHangEntity.class.getName());
        this.entityPathBuilder = new PathBuilder<>(this.modelClass, "khoHangEntity");
    }

    @Transactional
    public KhoHangEntity createKhoHang(KhoHangEntity khoHangEntity) {
        em.joinTransaction();
        em.persist(khoHangEntity);
        return khoHangEntity;
    }

    public KhoHangEntity getByMaKhoHang(String maKhoHang) {
        JPAQueryFactory jpaQueryFactory = this.getJPAQueryFactory();
        return jpaQueryFactory.selectFrom(this.qModel)
                        .where(this.qModel.maKho.stringValue().equalsIgnoreCase(maKhoHang))
                        .fetchOne();
    }

    public List <HangHoaEntity>  getAllHangHoaTrongKho(
            String maKhoHang,
            String maHangHoa,
            SearchParameter searchParameter
    ) {
        QHangHoaEntity qHangHoaEntity = QHangHoaEntity.hangHoaEntity;
        QHangTrongKhoEntity qHangTrongKhoEntity = QHangTrongKhoEntity.hangTrongKhoEntity;
        JPAQueryFactory jpaQueryFactory = this.getJPAQueryFactory();
        JPAQuery query = jpaQueryFactory.from(qHangTrongKhoEntity)
                        .where(qHangTrongKhoEntity
                            .hangTrongKhoID
                            .khoHangEntity
                            .maKho
                                .stringValue()
                                .contains(maKhoHang));
        if (!Objects.isNull(maHangHoa)) {
            query.where(qHangTrongKhoEntity
                    .hangTrongKhoID
                    .hangHoaEntity
                    .maHangHoa
                    .stringValue()
                    .contains(maHangHoa));
        }

        List<String> searchList = searchParameter.getSearch();
        if (!Objects.isNull(searchList)) {
            for (String search : searchList) {
                Pattern pattern = Pattern.compile("(\\w+)(:|<|>)(.+)");
                Matcher matcher = pattern.matcher(search);
                while (matcher.find()) {
                    if(matcher.group(1).equals("soLuong")) {
                        NumberExpression soLuongCondition = qHangTrongKhoEntity.soLuong;
                        BooleanExpression condition = soLuongCondition.isNotNull();
                        String op = matcher.group(2);
                        Integer num = Integer.parseInt(matcher.group(3));
                        if (op.equals(":")) {
                            condition = soLuongCondition.eq(num);
                        }
                        if (op.equals("<")) {
                            condition = soLuongCondition.loe(num);
                        }
                        if (op.equals(">")) {
                            condition = soLuongCondition.goe(num);
                        }
                        query.where(condition);
                    };
                }
            }
        }
        List<HangHoaEntity> result = query.select(qHangTrongKhoEntity
                                        .hangTrongKhoID
                                        .hangHoaEntity).fetch();
        return result;
    }


}
