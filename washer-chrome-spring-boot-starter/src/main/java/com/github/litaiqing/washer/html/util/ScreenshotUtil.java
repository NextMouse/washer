package com.github.litaiqing.washer.html.util;

import com.github.litaiqing.washer.core.config.properties.WebDriverCoreProperties;
import com.github.litaiqing.washer.core.exec.SimpleExecClient;
import com.github.litaiqing.washer.core.factory.WebDriverFactory;
import com.github.litaiqing.washer.core.handler.CallbackHandler;
import com.github.litaiqing.washer.core.utils.ArrayUtil;
import com.github.litaiqing.washer.html.config.properties.WebDriverProperties;
import com.github.litaiqing.washer.html.handler.ScreenshotHandler;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/2/28 16:07
 * @since JDK 1.8
 */
@Slf4j
public class ScreenshotUtil {

    /**
     * 屏幕截图
     *
     * @param url
     * @return
     */
    public static File getScreenshotAs(String url) {
        return getScreenshotAs(url, new ScreenshotHandler());
    }

    /**
     * 屏幕截图
     *
     * @param url
     * @return
     */
    public static File getScreenshotAs(String url, String loadFinishXpath) {
        return getScreenshotAs(url, new ScreenshotHandler() {

            @Override
            public void setProperties(WebDriverProperties properties) {
                getProperties().setLoadFinishXpath(loadFinishXpath);
            }

        });
    }

    /**
     * 屏幕截图
     *
     * @param url
     * @return
     */
    public static File getScreenshotAs(String url, String loadFinishXpath, long loadFinishWaitTime) {
        return getScreenshotAs(url, new ScreenshotHandler() {

            @Override
            public void setProperties(WebDriverProperties properties) {
                getProperties().setLoadFinishXpath(loadFinishXpath);
                getProperties().setLoadFinishWaitTime(loadFinishWaitTime);
            }

        });
    }

    /**
     * 屏幕截图
     *
     * @param url
     * @return
     */
    public static File getScreenshotAs(String url, WebDriverProperties properties) {
        if (properties == null) {
            return getScreenshotAs(url, new ScreenshotHandler());
        }
        return getScreenshotAs(url, new ScreenshotHandler(properties));
    }

    /**
     * 屏幕截图
     *
     * @param url
     * @param handler
     * @return
     */
    public static File getScreenshotAs(String url, CallbackHandler<WebDriver> handler) {
        return getScreenshotAs(url, OutputType.FILE, handler);
    }

    /**
     * 屏幕截图
     *
     * @param url
     * @param handler
     * @return
     */
    public static <X> X getScreenshotAs(String url, OutputType<X> outputType, CallbackHandler<WebDriver> handler) {
        WebDriver webDriver = null;
        try {
            webDriver = WebDriverFactory.get();
            if (log.isDebugEnabled()) {
                log.debug("url={}, outputType={}", url, outputType);
            }
            Assert.hasText(url, "url not has text");
            if (log.isDebugEnabled()) {
                log.debug(" ==> Start ScreenShot html...");
            }
            handler.pre(webDriver);
            webDriver.get(url);
            handler.next(webDriver);
            X x = ((TakesScreenshot) webDriver).getScreenshotAs(outputType);
            if (log.isDebugEnabled()) {
                log.debug(" ==> End ScreenShot html...");
            }
            handler.post(webDriver);
            return x;
        } finally {
            handler.finalFun(webDriver);
        }
    }

    /**
     * 使用JS脚本执行
     *
     * @param jsPath
     * @param command
     */
    public static Process getScreenshotByJsAs(String jsPath, String[] command) {
        try {
            WebDriverCoreProperties webDriverCoreProperties = WebDriverCoreProperties.get();
            @NonNull String exePath = webDriverCoreProperties.getPath();
            if (log.isDebugEnabled()) {
                log.debug(" ==> exePath:{}", exePath);
            }
            command = ArrayUtil.add0ByString(exePath, command);
            return SimpleExecClient.exec(new File(jsPath), command);
        } catch (IOException e) {
            log.error("{}", e);
            return null;
        }

    }


}
