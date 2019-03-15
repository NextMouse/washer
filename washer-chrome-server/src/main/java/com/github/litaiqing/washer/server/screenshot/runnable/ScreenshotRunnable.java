package com.github.litaiqing.washer.server.screenshot.runnable;

import com.github.litaiqing.washer.html.handler.ScreenshotHandler;
import com.github.litaiqing.washer.html.util.ScreenshotUtil;
import com.github.litaiqing.washer.server.pool.ThreadBill;
import com.github.litaiqing.washer.server.pool.exception.KillException;
import com.github.litaiqing.washer.server.pool.thread.StatusRunnable;
import com.github.litaiqing.washer.server.pool.thread.ThreadStatus;
import com.github.litaiqing.washer.server.screenshot.headler.ScreenshotKillHandler;
import com.github.litaiqing.washer.server.screenshot.http.HttpClient;
import com.github.litaiqing.washer.server.screenshot.vo.ScreenshotRequest;
import com.github.litaiqing.washer.server.utils.DateUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.util.StringUtils;

import java.io.File;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 16:53
 * @since JDK 1.8
 */
@Slf4j
public class ScreenshotRunnable extends StatusRunnable {

    @Getter
    private String filePath;

    @Getter
    private ScreenshotRequest screenshotRequest;

    public ScreenshotRunnable(ScreenshotRequest screenshotRequest) {
        this.screenshotRequest = screenshotRequest;
    }

    public ScreenshotRunnable(String filePath, ScreenshotRequest screenshotRequest) {
        this.filePath = filePath;
        this.screenshotRequest = screenshotRequest;
    }

    public String getRandomFilePath() {
        return getFilePath()
                .replace("{threadId}", getThreadId())
                .replace("{time}", DateUtils.simpleNow());
    }

    @Override
    public void go() {
        try {
            log.info(" ==> [{}] screen shot request:{}", getThreadId(), getScreenshotRequest());
            final String url = getScreenshotRequest().getUrl();
            log.info(" ==> [{}] url:{}", getThreadId(), url);
            ThreadBill.register(getThreadId(), ThreadStatus.RUNNING);
            File file = ScreenshotUtil.getScreenshotAs(url, assembling(new ScreenshotKillHandler(getThreadId()), getScreenshotRequest()));
            // 截图本地保存位置
            String filePath = getRandomFilePath();
            if (StringUtils.hasText(getScreenshotRequest().getImageSavePath())) {
                filePath = getScreenshotRequest().getImageSavePath();
            }
            FileUtils.copyFile(file, new File(filePath));
            ThreadBill.query(getThreadId()).getData().put("filePath", filePath);
            ThreadBill.register(getThreadId(), ThreadStatus.STOP);
            // 是否有CallbackUrl
            if (StringUtils.hasText(getScreenshotRequest().getCallbackUrl())) {
                // 回调
                HttpClient.callbackByGET(getScreenshotRequest().getCallbackUrl(), getThreadId());
            }
        } catch (KillException killException) {
            log.info(" ==> {}", killException.getMessage());
        } catch (Exception e) {
            log.error(" ==> [{}], error:{}", getThreadId(), e);
            ThreadBill.register(getThreadId(), ThreadStatus.ERROR);
        }
    }

    private ScreenshotHandler assembling(ScreenshotHandler screenshotHandler, ScreenshotRequest screenshotRequest) {
        // 加载完成XPath标记
        if (StringUtils.hasText(getScreenshotRequest().getLoadFinishXpath())) {
            screenshotHandler.getProperties().setLoadFinishXpath(getScreenshotRequest().getLoadFinishXpath());
        }
        // 指定Xpath元素的高度
        if (StringUtils.hasText(getScreenshotRequest().getWindowHeightByXpath())) {
            screenshotHandler.getProperties().getWindow().getSize().setHeightByXpath(getScreenshotRequest().getWindowHeightByXpath());
        }
        // 指定Xpath元素的宽度
        if (StringUtils.hasText(getScreenshotRequest().getWindowWidthByXpath())) {
            screenshotHandler.getProperties().getWindow().getSize().setWidthByXpath(getScreenshotRequest().getWindowWidthByXpath());
        }

        // 默认窗口宽度
        if (getScreenshotRequest().getWindowWidth() != null && getScreenshotRequest().getWindowWidth() > 0) {
            screenshotHandler.getProperties().getWindow().getSize().setWidth(getScreenshotRequest().getWindowWidth());
        }
        // 默认窗口高度
        if (getScreenshotRequest().getWindowHeight() != null && getScreenshotRequest().getWindowHeight() > 0) {
            screenshotHandler.getProperties().getWindow().getSize().setHeight(getScreenshotRequest().getWindowHeight());
        }

        // 是否最大化
        if (getScreenshotRequest().getWindowSizeMax() != null) {
            screenshotHandler.getProperties().getWindow().getSize().setMax(getScreenshotRequest().getWindowSizeMax());
        }

        // 窗口扩大开关
        if (getScreenshotRequest().getWindowSizeAdd() != null) {
            screenshotHandler.getProperties().getWindow().getSize().setAdd(getScreenshotRequest().getWindowSizeAdd());
        }
        // 窗口扩大宽度(像素)
        if (getScreenshotRequest().getWidthAdd() != null) {
            screenshotHandler.getProperties().getWindow().getSize().setWidthAdd(getScreenshotRequest().getWidthAdd());
        }
        // 窗口扩大高度(像素)
        if (getScreenshotRequest().getHeightAdd() != null) {
            screenshotHandler.getProperties().getWindow().getSize().setHeightAdd(getScreenshotRequest().getHeightAdd());
        }

        // 开始加载的等待时间(毫秒)-如果为非正数则表示不等待
        if (getScreenshotRequest().getLoadStartWaitTime() != null && getScreenshotRequest().getLoadStartWaitTime() > 0) {
            screenshotHandler.getProperties().setLoadStartWaitTime(getScreenshotRequest().getLoadStartWaitTime());
        }
        // 加载完成后的等待时间(毫秒)-如果为非正数则表示不等待
        if (getScreenshotRequest().getLoadFinishWaitTime() != null && getScreenshotRequest().getLoadFinishWaitTime() > 0) {
            screenshotHandler.getProperties().setLoadFinishWaitTime(getScreenshotRequest().getLoadFinishWaitTime());
        }


        log.info(" ==> [{}] {}", getThreadId(), screenshotHandler.getProperties());
        return screenshotHandler;
    }

}
