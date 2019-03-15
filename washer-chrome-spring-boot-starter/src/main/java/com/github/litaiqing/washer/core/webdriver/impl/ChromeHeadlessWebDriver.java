package com.github.litaiqing.washer.core.webdriver.impl;

import com.github.litaiqing.washer.core.config.properties.CoreDefaultConfig;
import com.github.litaiqing.washer.core.config.properties.WebDriverCoreProperties;
import com.github.litaiqing.washer.core.webdriver.HeadlessWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/7 17:47
 * @since JDK 1.8
 */
@Slf4j
public class ChromeHeadlessWebDriver extends HeadlessWebDriver {

    @Override
    public String getType() {
        return "chrome";
    }

    @Override
    public WebDriver newWebDriver() {
        WebDriverCoreProperties webDriverCoreProperties = WebDriverCoreProperties.get();
        if (log.isDebugEnabled()) {
            log.debug(" ==> start chrome driver ");
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        String args = webDriverCoreProperties.getChromeArgs();
        if (StringUtils.hasText(args)) {
            String[] argsArray = args.split(CoreDefaultConfig.BLANK);
            if (log.isDebugEnabled()) {
                log.debug(" ==> chrome options add args:{}", Arrays.toString(argsArray));
            }
            chromeOptions.addArguments(argsArray);
        }
        String chromeBinaryLocation = webDriverCoreProperties.getChromeBinaryLocation();
        if (StringUtils.hasText(chromeBinaryLocation)) {
            chromeOptions.setBinary(chromeBinaryLocation);
        }
        return new ChromeDriver(chromeOptions);
    }

}
