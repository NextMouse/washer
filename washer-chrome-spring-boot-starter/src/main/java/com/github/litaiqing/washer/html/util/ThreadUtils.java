package com.github.litaiqing.washer.html.util;

import lombok.extern.slf4j.Slf4j;

/**
 * <h3>线程工具类</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/7 1:05
 * @since JDK 1.8
 */
@Slf4j
public class ThreadUtils {

    /**
     * 休眠
     *
     * @param time
     */
    public static void sleepNoException(long time) {
        if (time > 0) {
            try {
                if (log.isDebugEnabled()) {
                    log.debug(" ==> sleep time:{}", time);
                }
                Thread.sleep(time);
            } catch (InterruptedException e) {
                log.error("{}", e);
            }
        }
    }

}
