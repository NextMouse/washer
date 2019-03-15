package com.github.litaiqing.washer.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <h3>SpringBeanUtil工具类</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/2/28 14:48
 * @since JDK 1.8
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (this.applicationContext == null) {
            this.applicationContext = applicationContext;
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取一个Bean
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name);
    }

    /**
     * 通过Class获取一组Bean
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * 通过Class获取一个Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }


}
