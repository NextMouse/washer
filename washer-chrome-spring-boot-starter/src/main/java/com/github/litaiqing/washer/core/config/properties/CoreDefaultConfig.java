package com.github.litaiqing.washer.core.config.properties;

/**
 * <h3>核心默认配置</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/2/28 17:14
 * @since JDK 1.8
 */
public interface CoreDefaultConfig {

    /**
     * 配置文件默认前缀
     */
    String CONFIG_PREFIX = "washer.web-driver";

    /**
     * 默认配置文件路径
     */
    String CONFIG_PATH = "classpath:default-config.properties";

    /**
     * 空格
     */
    String BLANK = " ";

}
