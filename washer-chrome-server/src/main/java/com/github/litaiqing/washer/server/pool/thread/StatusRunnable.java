package com.github.litaiqing.washer.server.pool.thread;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 17:09
 * @since JDK 1.8
 */
@Slf4j
public abstract class StatusRunnable implements Runnable {

    private String threadId;

    public String getThreadId() {
        return threadId;
    }

    public StatusRunnable setThreadId(String threadId) {
        this.threadId = threadId;
        return this;
    }

    public abstract void go();

    @Override
    public void run() {
        LocalDateTime startTime = LocalDateTime.now();
        log.info(" ==> [{}] execute start time: {}", getThreadId(), startTime);
        go();
        LocalDateTime endTime = LocalDateTime.now();
        log.info(" ==> [{}] execute end time: {}, between time(s): {}", getThreadId(), endTime, Duration.between(startTime, endTime).toMillis());
    }
}
