package com.github.litaiqing.washer.html.config.properties;

import com.github.litaiqing.washer.core.config.properties.CoreDefaultConfig;
import com.github.litaiqing.washer.core.utils.SpringBeanUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * <h3>phantomjs配置定义</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/2/27 18:19
 * @since JDK 1.8
 */
@Data
@Slf4j
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@ConfigurationProperties(prefix = CoreDefaultConfig.CONFIG_PREFIX)
@PropertySource(value = CoreDefaultConfig.CONFIG_PATH, ignoreResourceNotFound = true)
public class WebDriverProperties {

    /**
     * 加载完成XPath标记
     */
    private String loadFinishXpath;

    /**
     * 隐形超时时间
     */
    private Long timeoutImplicitlyWait = 1l;

    /**
     * 加载完成后的等待时间(毫秒)-如果为非正数则表示不等待
     */
    private Long loadFinishWaitTime = 500l;

    /**
     * 开始加载的等待时间(毫秒)-如果为非正数则表示不等待
     */
    private Long loadStartWaitTime = 500l;

    /**
     * 窗口配置
     */
    @NestedConfigurationProperty
    private Window window = new Window();

    @Data
    public class Window {

        /**
         * 窗口大小
         */
        @NestedConfigurationProperty
        private Size size = new Size();

        @Data
        public class Size {

            /**
             * 指定Xpath元素的高度
             */
            private String heightByXpath;

            /**
             * 指定Xpath元素的宽度
             */
            private String widthByXpath;

            /**
             * 窗口扩大开关
             */
            private Boolean add = Boolean.FALSE;

            /**
             * 窗口扩大宽度(像素)
             */
            private Integer widthAdd;

            /**
             * 窗口扩大高度(像素)
             */
            private Integer heightAdd;

            /**
             * 是否最大化
             */
            private Boolean max = Boolean.FALSE;

            /**
             * 默认窗口宽度
             */
            private Integer width;

            /**
             * 默认窗口高度
             */
            private Integer height;

        }

    }

    /**
     * 获取自身对象
     *
     * @return
     */
    public static WebDriverProperties get() {
        WebDriverProperties webDriverProperties = SpringBeanUtil.getBean(WebDriverProperties.class);
        if (webDriverProperties == null) {
            webDriverProperties = new WebDriverProperties();
        }
        if (log.isDebugEnabled()) {
            log.debug(" ==> {}", webDriverProperties);
        }
        return webDriverProperties;
    }

}



