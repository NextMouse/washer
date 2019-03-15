package com.github.litaiqing.washer.server.screenshot.headler;

import com.github.litaiqing.washer.html.handler.ScreenshotHandler;
import com.github.litaiqing.washer.html.util.ThreadUtils;
import com.github.litaiqing.washer.server.pool.ThreadBill;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 22:42
 * @since JDK 1.8
 */
@Slf4j
public class ScreenshotKillHandler extends ScreenshotHandler {

    @Getter
    @Setter
    private String threadId;

    public ScreenshotKillHandler(String threadId) {
        this.threadId = threadId;
    }

    @Override
    public void sleep(long time) {
        LocalDateTime startTime = LocalDateTime.now();
        log.info(" ==> [{}] sleep start time: {}, continue time: {}", getThreadId(), startTime, time);
        LocalDateTime endTime = null;
        long betweenTime = 0L;
        do {
            if (time > 100l) { // 防止做过多计算
                ThreadUtils.sleepNoException(100l);
            }
            ThreadBill.tryKill(getThreadId());
            endTime = LocalDateTime.now();
            betweenTime = Duration.between(startTime, endTime).toMillis();
        } while (betweenTime < time);
        log.info(" ==> [{}] sleep end time: {}", getThreadId(), endTime);
    }

    @Override
    public void pre(WebDriver webDriver) {
        ThreadBill.tryKill(getThreadId());
        super.pre(webDriver);
        ThreadBill.tryKill(getThreadId());
    }

    @Override
    public void next(WebDriver webDriver) {
        ThreadBill.tryKill(getThreadId());
        super.next(webDriver);
        ThreadBill.tryKill(getThreadId());
    }

    @Override
    public void post(WebDriver webDriver) {
        ThreadBill.tryKill(getThreadId());
    }
}
