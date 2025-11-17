package com.joyouth.framework.repository.entitys;

import cn.hutool.core.util.IdUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Objects;

/**
 * @PACKAGE_NAME: com.joyouth.framework.repository.entitys
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/10/10
 * @TIME: 15:34
 * @Description:
 */
public class CustomUUIDGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        String entityName = object.getClass().getName();
        Serializable id = (Serializable) session.getEntityPersister(entityName, object)
                .getIdentifier(object, session);
        // ID 存在则返回，否则生成新 UUID
        if (!Objects.isNull(id) && !String.valueOf(id).trim().isEmpty()) {
            return id;
        }
        return IdUtil.fastSimpleUUID();
    }
}
