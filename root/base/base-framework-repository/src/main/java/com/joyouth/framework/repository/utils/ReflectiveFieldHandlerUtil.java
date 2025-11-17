package com.joyouth.framework.repository.utils;

import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;

/**
 * @PACKAGE_NAME: com.joyouth.ts.platform.business.common.utils
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/7/15
 * @TIME: 11:48
 * @Description:
 */
public class ReflectiveFieldHandlerUtil {
    public static <T> Object getValueByPropName(T t, String propName, boolean isSet, Object fieldValue) {
        try {
            // 通过属性获取对象的属性
            //.getDeclaredFields() 获得某个类的所有声明的字段，即包括public、private和proteced但不包括父类申明字段
            //.getClass() 是⼀个对象实例的⽅法，只有对象实例才有这个⽅法，具体的类是没有的
            Field field = getField(t.getClass(), propName);
            if (ObjectUtils.isEmpty(field)) {
                return null;
            }
            // 对象的属性的访问权限设置为可访问
            //允许获取实体类private的参数信息
            field.setAccessible(true);
            // 获取属性的对应的值
            if (!isSet) {
                return field.get(t).toString();
            } else {
                field.set(t, fieldValue);
            }
            return fieldValue;
        } catch (Exception e) {
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String filedName) {
        try {
            if (clazz != null) {
                return clazz.getDeclaredField(filedName);
            }
        } catch (NoSuchFieldException e) {
            return getField(clazz.getSuperclass(), filedName);
        }
        return null;
    }

}
