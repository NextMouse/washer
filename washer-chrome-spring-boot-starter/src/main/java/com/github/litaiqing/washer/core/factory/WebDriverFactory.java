package com.github.litaiqing.washer.core.factory;

import com.github.litaiqing.washer.core.config.properties.WebDriverCoreProperties;
import com.github.litaiqing.washer.core.exception.WebDriverNotFoundException;
import com.github.litaiqing.washer.core.utils.SpringBeanUtil;
import com.github.litaiqing.washer.core.webdriver.HeadlessWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * <h3>WebDriver工厂方法</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/2/28 14:35
 * @since JDK 1.8
 */
@Slf4j
public class WebDriverFactory {

    /**
     * 获取一个简单的WebDriver对象
     *
     * @return
     */
    public static WebDriver get() throws WebDriverNotFoundException {
        WebDriverCoreProperties properties = WebDriverCoreProperties.get();
        return WebDriverFactory.getByType(properties.getType());
    }

    /**
     * 获取一个简单的WebDriver对象
     *
     * @return
     */
    public static WebDriver getByType(String type) throws WebDriverNotFoundException {
        Assert.hasText(type, "type not has text");
        // 获取所有 HeadlessWebDriver 的实现类
        Map<String, HeadlessWebDriver> beanMap = SpringBeanUtil.getBeansOfType(HeadlessWebDriver.class);
        // 如果没有实现类
        if (CollectionUtils.isEmpty(beanMap)) {
            throw new WebDriverNotFoundException("Not found HeadlessWebDriver implement!");
        }
        // 根据type查找实现类
        HeadlessWebDriver headlessWebDriverImpl = findBeanByType(beanMap, type);
        if (headlessWebDriverImpl == null) {
            throw new WebDriverNotFoundException("Not found Type HeadlessWebDriver implement! Type=" + type);
        }
        return headlessWebDriverImpl.get();
    }

    /**
     * 获取具有回调功能的一个WebDriver
     *
     * @param consumer
     * @return
     */
    public static WebDriver get(Consumer<WebDriver> consumer) {
        WebDriver webDriver = get();
        consumer.accept(webDriver);
        return webDriver;
    }

    /**
     * 根据类型名词查找HeadlessWebDriver
     *
     * @param beanMap
     * @param type
     * @return
     */
    public static HeadlessWebDriver findBeanByType(Map<String, HeadlessWebDriver> beanMap, String type) {
        if (type == null) {
            return null;
        }
        Optional<Map.Entry<String, HeadlessWebDriver>> optional = beanMap.entrySet()
                .stream()
                .filter(e -> type.equalsIgnoreCase(e.getValue().getType()))
                .findFirst();
        return optional.isPresent() ? optional.get().getValue() : null;
    }

}
