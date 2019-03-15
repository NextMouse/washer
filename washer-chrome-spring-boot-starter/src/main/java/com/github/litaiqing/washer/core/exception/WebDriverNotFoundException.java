package com.github.litaiqing.washer.core.exception;

/**
 * <h3>WebDriver实现类找不到异常</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/2/28 15:08
 * @since JDK 1.8
 */
public class WebDriverNotFoundException extends RuntimeException {
    public WebDriverNotFoundException() {
    }

    public WebDriverNotFoundException(String message) {
        super(message);
    }

}
