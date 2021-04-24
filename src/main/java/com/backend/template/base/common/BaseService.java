package com.backend.template.base.common;

import com.backend.template.base.common.exception.BackendError;
import com.ibm.icu.impl.locale.AsciiUtil;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springdoc.core.converters.models.Pageable;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BaseService<T> {

    @PersistenceContext
    public EntityManager em;

    public JPAQueryFactory getJPAQueryFactory() {
        return new JPAQueryFactory(em);
    }

    public PathBuilder entityPathBuilder;

    public T qModel;

    public BaseService (T qModel) {
        this.qModel = qModel;
    }

    public T getQueryModel() {
        return this.qModel;
    }

    public static OrderSpecifier<?>[] getSortedColumn(List<String> sorts, Object model) throws BackendError {
        try {
            return sorts.stream().map(sort -> {
                String[] sortData = sort.split("\\|");
                String field = sortData[0];
                String direction = sortData[1].toLowerCase();
                Order order = direction.equals("asc") ? Order.ASC : Order.DESC;
                SimplePath<Object> fieldPath = Expressions.path(Object.class, (Path<?>) model, field);
                return  new OrderSpecifier(order, fieldPath);
            }).toArray(OrderSpecifier[]::new);
        } catch ( Exception e) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Paging not valid format");
        }

    }

    public static JPAQuery<?> queryPagable(JPAQuery jpaQuery, Pageable pageable, Path<?> model) throws BackendError {
        if (pageable != null) {
            if (pageable.getSize() == null) {
                pageable.setSize(10);
            }
            if (pageable.getPage() == null) {
                pageable.setPage(1);
            }
            final Integer size = pageable.getSize();
            final int offset = Math.max(pageable.getPage()-1, 0)*size;
            if (pageable.getSort() != null)
                jpaQuery.orderBy(BaseService.getSortedColumn(pageable.getSort(), model));
            jpaQuery.offset(offset);
            jpaQuery.limit(size);
        }
        return  jpaQuery;
    }

    public BooleanExpression getSingleSearchPredicate(String key, String operation, Object value) {
        if (AsciiUtil.isNumericString(value.toString())) {
            NumberPath<Integer> path = entityPathBuilder.getNumber(key, Integer.class);
            int val = Integer.parseInt(value.toString());
            if (operation.equals(":")) {
                return path.eq(val);
            }
            if (operation.equals(">")) {
                return path.goe(val);
            }
            if (operation.equals("<")) {
                return  path.loe(val);
            }
        } else {
            StringPath path = entityPathBuilder.getString(key);
            return path.stringValue().containsIgnoreCase(value.toString().trim());
        }
        return null;
    }

    public BooleanExpression getMultiSearchPredicate(String search) {
        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+)(:|<|>)(\\w+)(\\s+)?");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                result = result.and(getSingleSearchPredicate(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return  result;
    }


}
