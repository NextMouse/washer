package com.github.litaiqing.washer.core.webdriver;

import com.github.litaiqing.washer.core.config.properties.WebDriverCoreProperties;
import com.github.litaiqing.washer.core.exception.WebDriverNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.util.StringUtils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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

    public static final Queue<WebDriver> webDriverQueue = new ConcurrentLinkedQueue();

    /**
     * 关闭方法
     */
    public static void close(WebDriver webDriver) {
        if (webDriver == null) {
            return;
        }
        WebDriverCoreProperties properties = WebDriverCoreProperties.get();
        if (properties.getCloseUrl() != null) {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("get close url:{}", properties.getCloseUrl());
                }
                webDriver.get(properties.getCloseUrl());
            } catch (Exception e) {
                log.error("close url:" + properties.getCloseUrl(), e);
            }
        }
        if (webDriverQueue.size() < properties.getCacheSize()) { // 回收
            webDriverQueue.offer(webDriver);
        } else { // 销毁
            webDriver.close();
            webDriver.quit();
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
        WebDriver webDriver = webDriverQueue.poll();
        if (webDriver != null) {
            if (log.isDebugEnabled()) {
                log.debug(" ==> from webDriverQueue get WebDriver! Queue surplus size:{}", webDriverQueue.size());
            }
            return webDriver;
        }
        WebDriverCoreProperties properties = WebDriverCoreProperties.get();
        if (System.getProperty(properties.getPathProp()) == null) {
            if (log.isDebugEnabled()) {
                log.debug(" ==> set system property: {}={}", properties.getPathProp(), properties.getPath());
            }
            if (StringUtils.hasText(properties.getPathProp()) && StringUtils.hasText(properties.getPath())) {
                System.setProperty(properties.getPathProp(), properties.getPath());
            }
        }
        webDriver = newWebDriver();
        if (webDriver == null) {
            throw new WebDriverNotFoundException("init web driver error...");
        }
        return webDriver;
    }

}
