package com.tkj.wechat.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author czx
 */
@Component
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }
    }

    /**
     * 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过class获取Bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 通过Clazz返回指定的Beans
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Map<String, T> getBeansByType(Class<T> clazz) {
        return getApplicationContext().getBeansOfType(clazz);
    }

    /**
     * 通过name，读取属性
     * @param name
     * @return
     */
    public static String getProperty(String name) {
        Environment environment = applicationContext.getEnvironment();
        return environment.getProperty(name);
    }

    /**
     * 在运行期间动态注入bean
     * @param beanId
     * @param classPath 类路径
     * @param propertyValues key位属性的set方法， value为属性值
     */
    public static void registerBean(String beanId, String classPath, Map<String, Object> propertyValues) {
        BeanDefinition beanDf = new GenericBeanDefinition();
        beanDf.setBeanClassName(classPath);

        if (propertyValues != null) {
            for (Map.Entry<String, Object> entry : propertyValues.entrySet()) {
                beanDf.getPropertyValues().add(entry.getKey(), entry.getValue());
            }
        }
        DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        fty.registerBeanDefinition(beanId, beanDf);
    }

    /**
     * 注销bean
     * @param beanId
     */
    public static void destroyBean(String beanId){
        DefaultListableBeanFactory fty = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory() ;
        if(fty.containsBean(beanId)) {
            fty.removeBeanDefinition(beanId);
        }
    }
}
