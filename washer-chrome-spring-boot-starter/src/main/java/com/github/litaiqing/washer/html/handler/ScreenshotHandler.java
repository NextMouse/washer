package com.github.litaiqing.washer.html.handler;

import com.github.litaiqing.washer.core.handler.CallbackHandler;
import com.github.litaiqing.washer.core.utils.WebDriverUtils;
import com.github.litaiqing.washer.core.webdriver.HeadlessWebDriver;
import com.github.litaiqing.washer.html.config.properties.WebDriverProperties;
import com.github.litaiqing.washer.html.util.ThreadUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * <h3>屏幕截图Handler</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/2/28 11:49
 * @since JDK 1.8
 */
@Slf4j
public class ScreenshotHandler implements CallbackHandler<WebDriver> {

    @Getter
    @Setter
    private WebDriverProperties properties = null;

    public ScreenshotHandler() {
        properties = WebDriverProperties.get();
    }

    public ScreenshotHandler(WebDriverProperties properties) {
        this.properties = properties;
    }

    public void sleep(long time) {
        ThreadUtils.sleepNoException(time);
    }

    @Override
    public void pre(WebDriver webDriver) {
        WebDriver.Options manage = webDriver.manage();
        // 设置默认隐形等待时间
        manage.timeouts().implicitlyWait(getProperties().getTimeoutImplicitlyWait(), TimeUnit.SECONDS);
        WebDriver.Window window = manage.window();
        // 窗口是否最大化
        boolean maxFlag = getProperties().getWindow().getSize().getMax();
        if (maxFlag) {
            window.maximize();
        }
        // 设置窗口大小
        Integer widthPro = getProperties().getWindow().getSize().getWidth();
        Integer heightPro = getProperties().getWindow().getSize().getHeight();
        WebDriverUtils.setWindowSize(webDriver, widthPro, heightPro);
    }

    @Override
    public void next(WebDriver webDriver) {
        // 等待，如果为非正数则表示不等待
        Long loadStartWaitTime = getProperties().getLoadStartWaitTime();
        if (loadStartWaitTime != null && loadStartWaitTime > 0) {
            sleep(loadStartWaitTime);
        }
        String loadFinishXpath = getProperties().getLoadFinishXpath();
        if (StringUtils.hasText(loadFinishXpath)) {
            webDriver.findElement(By.xpath(loadFinishXpath));
        }
        if (log.isDebugEnabled()) {
            log.debug(" ==> html load finish ! xpath={}", loadFinishXpath);
        }
        String heightByXpath = getProperties().getWindow().getSize().getHeightByXpath();
        if (StringUtils.hasText(heightByXpath)) {
            WebDriverUtils.setWindowHeightByXpath(webDriver, heightByXpath);
        }
        String widthByXpath = getProperties().getWindow().getSize().getWidthByXpath();
        if (StringUtils.hasText(widthByXpath)) {
            WebDriverUtils.setWindowWidthByXpath(webDriver, widthByXpath);
        }
        boolean addFlag = getProperties().getWindow().getSize().getAdd();
        if (addFlag) {
            // 设置窗口扩大
            Integer widthAdd = getProperties().getWindow().getSize().getWidthAdd();
            Integer heightAdd = getProperties().getWindow().getSize().getHeightAdd();
            WebDriverUtils.windowSizeAdd(webDriver, widthAdd, heightAdd);
        }
        // 等待，如果为非正数则表示不等待
        Long loadFinishWaitTime = getProperties().getLoadFinishWaitTime();
        if (loadFinishWaitTime != null && loadFinishWaitTime > 0) {
            sleep(loadFinishWaitTime);
        }
    }

    @Override
    public void finalFun(WebDriver webDriver) {
        HeadlessWebDriver.close();
    }


}
