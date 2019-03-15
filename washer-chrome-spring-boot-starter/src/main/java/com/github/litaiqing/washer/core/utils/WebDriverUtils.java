package com.github.litaiqing.washer.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;

/**
 * WebDriver懒人工具类<br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/8 10:06
 * @since JDK 1.8
 */
@Slf4j
public class WebDriverUtils {

    /**
     * 设置窗口大小
     *
     * @param webDriver
     * @param width
     * @param height
     */
    public static void setWindowSize(WebDriver webDriver, Integer width, Integer height) {
        if (width == null && height == null) {
            return;
        }
        WebDriver.Window window = webDriver.manage().window();
        int nextWidth = window.getSize().getWidth();
        int nextHeight = window.getSize().getHeight();
        if (width != null && width > 0) {
            nextWidth = width;
        }
        if (height != null && height > 0) {
            nextHeight = height;
        }
        window.setSize(new Dimension(nextWidth, nextHeight));
        if (log.isDebugEnabled()) {
            log.debug(" ==> setWindowSize=width:{}, height={}", window.getSize().getWidth(), window.getSize().getHeight());
        }
    }

    /**
     * 设置窗口扩大数
     *
     * @param widthAdd
     * @param heightAdd
     */
    public static void windowSizeAdd(WebDriver webDriver, Integer widthAdd, Integer heightAdd) {
        if (widthAdd == null && heightAdd == null) {
            return;
        }
        WebDriver.Window window = webDriver.manage().window();
        // 默认窗口大小
        int oldWidth = window.getSize().getWidth();
        int oldHeight = window.getSize().getHeight();
        if (widthAdd != null) {
            oldWidth = oldWidth + widthAdd;
        }
        if (heightAdd != null) {
            oldHeight = oldHeight + heightAdd;
        }
        window.setSize(new Dimension(oldWidth, oldHeight));
        if (log.isDebugEnabled()) {
            log.debug(" ==> windowSizeAdd=width:{}, height={}", window.getSize().getWidth(), window.getSize().getHeight());
        }
    }

    /**
     * 设置窗口高度
     *
     * @param webDriver
     * @param height
     */
    public static void setWindowHeight(WebDriver webDriver, int height) {
        setWindowSize(webDriver, null, height);
    }

    /**
     * 设置窗口宽度
     *
     * @param webDriver
     * @param width
     */
    public static void setWindowWidth(WebDriver webDriver, int width) {
        setWindowSize(webDriver, width, null);
    }

    /**
     * 获取指定元素的高度
     *
     * @param webDriver
     * @param xpath
     * @return
     */
    public static Integer getHeight(WebDriver webDriver, String xpath) {
        try {
            WebElement webElement = webDriver.findElement(By.xpath(xpath));
            Rectangle pointer = webElement.getRect();
            if (log.isDebugEnabled()) {
                log.debug(" ==> [{}].pointer.height={}", xpath, pointer.getHeight());
            }
            return pointer.getHeight();
        } catch (Exception e) {
            log.error("{}", e);
            return null;
        }
    }

    /**
     * 获取指定元素的宽度
     *
     * @param webDriver
     * @param xpath
     * @return
     */
    public static Integer getWidth(WebDriver webDriver, String xpath) {
        try {
            WebElement webElement = webDriver.findElement(By.xpath(xpath));
            Rectangle pointer = webElement.getRect();
            if (log.isDebugEnabled()) {
                log.debug(" ==> [{}].pointer.height={}", xpath, pointer.getHeight());
            }
            return pointer.getWidth();
        } catch (Exception e) {
            log.error("{}", e);
            return null;
        }
    }

    /**
     * 设置窗口高度为指定Xpath元素的高度
     *
     * @param webDriver
     * @param heightByXpath
     */
    public static void setWindowHeightByXpath(WebDriver webDriver, String heightByXpath) {
        setWindowHeight(webDriver, getHeight(webDriver, heightByXpath));
    }

    /**
     * 设置窗口宽度为指定Xpath元素的宽度
     *
     * @param webDriver
     * @param widthByXpath
     */
    public static void setWindowWidthByXpath(WebDriver webDriver, String widthByXpath) {
        setWindowWidth(webDriver, getWidth(webDriver, widthByXpath));
    }

    /**
     * 设置指定元素的元素值
     *
     * @param webDriver
     * @param xpath
     * @param attrName
     * @param attrValue
     */
    public static void setAttribute(WebDriver webDriver, String xpath, String attrName, String attrValue) {
        WebElement webElement = webDriver.findElement(By.xpath(xpath));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].setAttribute(arguments[1],arguments[2])", webElement, attrName, attrValue);
    }

}
