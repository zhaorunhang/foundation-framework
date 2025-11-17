package com.zrh.framework.core.configurations.responseAdvice;

import com.zrh.framework.core.advices.DynamicResponseAdvice;
import com.zrh.framework.core.advices.DynamicResponseAdviceWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

/**
 * @PACKAGE_NAME: com.zrh.framework.core.configurations
 * @NAME: ZRH
 * @USER: ZRH13
 * @DATE: 2025/11/13
 * @TIME: 15:15
 * @Description:
 */
@AutoConfiguration
public class ResponseAdviceAutoConfiguration implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
    private Environment environment;

    @Override
    public void setEnvironment(@NonNull Environment environment) {
        this.environment = environment;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry registry) throws BeansException {
        // 读取配置
        Binder binder = Binder.get(environment);
        ResponseAdviceProperties properties = binder.bind("response.advice", ResponseAdviceProperties.class).orElse(new ResponseAdviceProperties());
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(DynamicResponseAdvice.class);
        beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        if (!properties.isEnabled()) return;
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, properties.getBasePackages());
        String delegateName = "responseAdviceDelegate-" + properties.getBeanName();
        registry.registerBeanDefinition(delegateName, beanDefinition);
        AnnotatedGenericBeanDefinition wrapperDef = new AnnotatedGenericBeanDefinition(DynamicResponseAdviceWrapper.class);
        wrapperDef.setScope(BeanDefinition.SCOPE_SINGLETON);
        // 再创建 Wrapper 的 BeanDefinition
        // 通过引用注入 delegate
        wrapperDef.getConstructorArgumentValues().addIndexedArgumentValue(0, new RuntimeBeanReference(delegateName));
        registry.registerBeanDefinition("responseAdvice-" + properties.getBeanName(), wrapperDef);
    }
}
