package com.github.litaiqing.washer.core.handler;

/**
 * <h3>通用回调接口</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/2/28 10:00
 * @since JDK 1.8
 */
public interface CallbackHandler<R> {

    /**
     * 前置处理方法
     *
     * @param r
     * @return
     */
    default void pre(R r) {
    }

    /**
     * 处理执行方法
     *
     * @param r
     * @return
     */
    default void next(R r) {
    }

    /**
     * 后置处理方法
     *
     * @param r
     * @return
     */
    default void post(R r) {
    }

    /**
     * 必处理方法
     *
     * @param r
     * @return
     */
    default void finalFun(R r) {
    }

}
