package com.backend.template.base.common;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.backend.template.base.common.exception.BackendError;
import com.backend.template.domain.QuanLyQuy.model.ThuTienEntity;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DatePath;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimplePath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.core.types.dsl.TimeExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.http.HttpStatus;

@SuppressWarnings( { "rawtypes", "unchecked" }) // Disable noise :((
public class BaseService<T> {

    @PersistenceContext
    public EntityManager em;

    public JPAQueryFactory getJPAQueryFactory() {
        return new JPAQueryFactory(em);
    }

    public PathBuilder<?> entityPathBuilder;
    public String baseModelClassName = ThuTienEntity.class.getName();
    public Class<?> modelClass;
    public T qModel;

    public BaseService (T qModel, String baseModelClassName) {
        this.qModel = qModel;
        try {
            modelClass = Class.forName(baseModelClassName);
        } catch (Exception e) {
            System.out.println("Not found " + baseModelClassName);
        }

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
                OrderSpecifier<?> orderSpecifier = new OrderSpecifier(order, fieldPath);
                return orderSpecifier;
            }).toArray(OrderSpecifier[]::new);
        } catch ( Exception e) {
            throw new BackendError(HttpStatus.BAD_REQUEST, "Paging not valid format");
        }
    }

    public static JPAQuery<?> queryPagable(JPAQuery<?> jpaQuery, Pageable pageable, Path<?> model) throws BackendError {
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

    public List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        fields.addAll(Arrays.asList(type.getDeclaredFields()));

        if (type.getSuperclass() != null) {
            fields.addAll(getAllFields(type.getSuperclass()));
        }

        return fields;
    }

    public Class<?> getTypeOfFieldObject(String key) {
        try {
            List<Field> allFields = this.getAllFields(modelClass);
            for (Field field : allFields) {
                if (field.getName().equals(key)) {
                    return field.getType();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return String.class;
    }

    public BooleanExpression getSingleSearchPredicate(String key, String operation, Object value) {
        Class<?> type = this.getTypeOfFieldObject(key);
        if (type.equals(Long.class)) {
            NumberPath<Long> path = entityPathBuilder.getNumber(key, Long.class);
            long val = Long.parseLong(value.toString());
            if (operation.equals(":")) {
                return path.eq(val);
            }
            if (operation.equals(">")) {
                return path.goe(val);
            }
            if (operation.equals("<")) {
                return  path.loe(val);
            }
        }
        if (type.equals(Integer.class)) {
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
        }
        if (type.equals(Date.class)) {
            DatePath<Date> path = entityPathBuilder.getDate(key, Date.class);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                TimeExpression<Date> fieldDate = Expressions.asTime(simpleDateFormat.parse(value.toString()));
                if (operation.equals(":")) {
                    return path.eq(fieldDate);
                }
                if (operation.equals("<")) {
                    return  path.loe(fieldDate);
                }
                if (operation.equals(">")) {
                    return path.goe(fieldDate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return  path.stringValue().equalsIgnoreCase(value.toString());
        }
        if (type.isInstance(Enum.class) || type.equals(Enum.class)) {
            EnumPath<?> path = entityPathBuilder.getEnum(key, Enum.class);
            return path.stringValue().equalsIgnoreCase(value.toString());
        }
        StringPath path = entityPathBuilder.getString(key);
        return path.stringValue().containsIgnoreCase(value.toString().trim());
    }

    public BooleanExpression getMultiSearchPredicate(List<String> searchList) {
        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        if (searchList == null) {
            return result;
        }
        for (String search : searchList) {
            Pattern pattern = Pattern.compile("(\\w+)(:|<|>)(.+)");
            Matcher matcher = pattern.matcher(search);
            while (matcher.find()) {
                result = result.and(getSingleSearchPredicate(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }
        return  result;
    }
}
