package com.github.litaiqing.washer.core.webdriver.vo;

import lombok.Data;
import org.openqa.selenium.WebDriver;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/14 18:32
 * @since JDK 1.8
 */
@Data
public class WebDriverData {

    /**
     * 保存的WebDriver
     */
    private WebDriver webDriver;

    /**
     * 是否保持浏览器开启状态
     */
    private boolean alwaysOpen = false;

    /**
     * 保持运行时close跳转的页面，默认是空白页
     */
    private String closeUrl;


}
