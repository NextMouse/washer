package com.github.litaiqing.washer.core.webdriver;

import com.github.litaiqing.washer.core.config.properties.WebDriverCoreProperties;
import com.github.litaiqing.washer.core.exception.WebDriverNotFoundException;
import com.github.litaiqing.washer.core.webdriver.vo.WebDriverData;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.util.StringUtils;

/**
 * <h3>定义WebDriver抽象类</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/2/28 14:22
 * @since JDK 1.8
 */
@Slf4j
public abstract class HeadlessWebDriver {

    public static final ThreadLocal<WebDriverData> threadLocal = new ThreadLocal<>();

    /**
     * 关闭方法
     */
    public static void close() {
        if (threadLocal.get() == null) {
            return;
        }
        if (threadLocal.get().getWebDriver() == null) {
            return;
        }
        if (threadLocal.get().isAlwaysOpen() && StringUtils.hasText(threadLocal.get().getCloseUrl())) {
            threadLocal.get().getWebDriver().get(threadLocal.get().getCloseUrl());
        } else {
            threadLocal.get().getWebDriver().close();
            threadLocal.get().getWebDriver().quit();
            threadLocal.remove();
        }
    }

    /**
     * 设置类型名词
     */
    public abstract String getType();

    /**
     * 生成WebDriver方法
     */
    public abstract WebDriver newWebDriver();

    /**
     * 获取WebDriver方法
     */
    public WebDriver get() {
        WebDriverData webDriverData = threadLocal.get();
        if (webDriverData == null || webDriverData.getWebDriver() == null) {
            webDriverData = new WebDriverData();
            WebDriverCoreProperties properties = WebDriverCoreProperties.get();
            if (log.isDebugEnabled()) {
                log.debug(" ==> set system property: {}={}", properties.getPathProp(), properties.getPath());
            }
            if (Boolean.TRUE == properties.getAlwaysOpen()) {
                webDriverData.setAlwaysOpen(true);
            }
            if (StringUtils.hasText(properties.getCloseUrl())) {
                webDriverData.setCloseUrl(properties.getCloseUrl());
            }
            if (StringUtils.hasText(properties.getPathProp()) && StringUtils.hasText(properties.getPath())) {
                System.setProperty(properties.getPathProp(), properties.getPath());
            }
        }
        if (threadLocal.get() != null && webDriverData.isAlwaysOpen()) {
            if (log.isDebugEnabled()) {
                log.debug(" ==> from thread local get WebDriver!");
            }
            return threadLocal.get().getWebDriver();
        }
        WebDriver webDriver = newWebDriver();
        if (webDriver == null) {
            throw new WebDriverNotFoundException("init web driver error...");
        }
        webDriverData.setWebDriver(webDriver);
        threadLocal.set(webDriverData);
        if (log.isDebugEnabled()) {
            log.debug("thread local get:{}", threadLocal.get());
        }
        return webDriver;
    }

}
