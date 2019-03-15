package com.github.litaiqing.washer.server.pool.vo;

import com.github.litaiqing.washer.server.pool.thread.ThreadStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 19:17
 * @since JDK 1.8
 */
@ToString
public class ThreadData {

    @Getter
    @Setter
    private String threadId;

    @Getter
    @Setter
    private ThreadStatus status;

    /**
     * 线程内数据，不允许跨线程访问，线程不安全
     */
    @Getter
    private final Map<String, Object> data = new HashMap<>();

    public ThreadData(String threadId, ThreadStatus status) {
        this.threadId = threadId;
        this.status = status;
    }

    public ThreadData(ThreadStatus status) {
        this.status = status;
    }

    public ThreadData() {
    }
}
