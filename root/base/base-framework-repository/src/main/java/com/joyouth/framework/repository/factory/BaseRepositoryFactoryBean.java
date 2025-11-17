package com.joyouth.framework.repository.factory;

import com.joyouth.framework.repository.entitys.SimpleBaseEntity;
import com.joyouth.framework.repository.repositorys.impl.BaseRepositoryImpl;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class BaseRepositoryFactoryBean<R extends JpaRepository<T, ID>, T extends SimpleBaseEntity<ID>, ID extends Serializable> extends JpaRepositoryFactoryBean<R, T, ID> {

    public BaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new BaseRepositoryFactory(entityManager);

    }

    private static class BaseRepositoryFactory<T extends SimpleBaseEntity<ID>, ID extends Serializable> extends JpaRepositoryFactory {

        public BaseRepositoryFactory(EntityManager em) {
            super(em);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected JpaRepositoryImplementation<?, ?> getTargetRepository(
                RepositoryInformation information, EntityManager entityManager) {
            // 获取实体信息 - 调用父类 JpaRepositoryFactory 的实现

            // 修复方案 1：显式指定泛型
            JpaEntityInformation<T, ID> entityInformation =
                    this.<T, ID>getEntityInformation((Class<T>) information.getDomainType());

            return new BaseRepositoryImpl<>(
                    entityInformation,
                    entityManager
            );
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            // 返回自定义的 Repository 基类
            return BaseRepositoryImpl.class;
        }
    }
}

