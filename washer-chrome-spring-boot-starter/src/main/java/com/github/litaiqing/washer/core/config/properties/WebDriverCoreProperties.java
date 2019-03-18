package com.github.litaiqing.washer.core.config.properties;

import com.github.litaiqing.washer.core.utils.SpringBeanUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * <h3>核心配置</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/2/28 17:11
 * @since JDK 1.8
 */
@Data
@Slf4j
@Component
@ConfigurationProperties(prefix = CoreDefaultConfig.CONFIG_PREFIX)
@PropertySource(value = CoreDefaultConfig.CONFIG_PATH, ignoreResourceNotFound = true)
public class WebDriverCoreProperties {

    /**
     * chrome参数
     */
    private String chromeArgs = "--headless --disable-web-security --disable-gpu --hide-scrollbars --disable-dev-shm-usage --no-sandbox";

    /**
     * chrome浏览器位置
     */
    private String chromeBinaryLocation;

    /**
     * 类型
     */
    private String type = "chrome";

    /**
     * 执行路径key
     */
    private String pathProp = ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY;

    /**
     * webdriver执行路径
     */
    private String path;

    /**
     * 保持运行时close跳转的页面，默认是空白页
     */
    private String closeUrl = "about:blank";

    /**
     * WebDriver缓存队列最大个数
     */
    private Integer cacheSize = 5;

    /**
     * 获取自身对象
     *
     * @return
     */
    public static WebDriverCoreProperties get() {
        WebDriverCoreProperties webDriverCoreProperties = SpringBeanUtil.getBean(WebDriverCoreProperties.class);
        if (webDriverCoreProperties == null) {
            webDriverCoreProperties = new WebDriverCoreProperties();
        }
        if (log.isDebugEnabled()) {
            log.debug(" ==> {}", webDriverCoreProperties);
        }
        if (webDriverCoreProperties.getCacheSize() == null) {
            webDriverCoreProperties.setCacheSize(0);
        }
        return webDriverCoreProperties;
    }

}
