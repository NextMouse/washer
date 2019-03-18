package com.github.litaiqing.washer.server.pool;

import com.github.litaiqing.washer.server.pool.thread.StatusRunnable;
import com.github.litaiqing.washer.server.pool.thread.ThreadStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h3>最简单的线程池</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 15:59
 * @since JDK 1.8
 */
@Slf4j
public class SimpleThreadPool {

    /**
     * 最大100个线程
     */
    private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

    public static ExecutorService getThreadPool() {
        return THREAD_POOL;
    }

    public static String execute(StatusRunnable statusRunnable) {
        final String threadId = getThreadId();
        ThreadBill.register(threadId, ThreadStatus.READYING);
        THREAD_POOL.execute(statusRunnable.setThreadId(threadId));
        return threadId;
    }

    private static String getThreadId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
