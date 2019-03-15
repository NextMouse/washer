package com.github.litaiqing.washer.server.pool.thread;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 17:04
 * @since JDK 1.8
 */
public enum ThreadStatus {

    /**
     * 准备
     */
    READYING("READYING"),
    /**
     * 运行
     */
    RUNNING("RUNNING"),
    /**
     * 停止
     */
    STOP("STOP"),
    /**
     * 错误
     */
    ERROR("ERROR"),
    /**
     * 未知、找不到
     */
    NO_FOUND("NO_FOUND"),
    /**
     * 杀死
     */
    KILL("KILL");

    private String value;

    ThreadStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }


}
