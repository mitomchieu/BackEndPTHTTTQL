package com.backend.template.base.common;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class BaseService<T> {
    @PersistenceContext
    protected EntityManager em;
    protected JPAQueryFactory getJPAQueryFactory() {
        return new JPAQueryFactory(em);
    }
    protected T qModel;

    public BaseService (T qModel) {
        this.qModel = qModel;
    }

    public T getQueryModel() {
        return this.qModel;
    }

}
