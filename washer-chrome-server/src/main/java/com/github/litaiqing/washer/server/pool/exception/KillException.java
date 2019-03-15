package com.github.litaiqing.washer.server.pool.exception;

/**
 * <h3>杀死进程异常</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 21:40
 * @since JDK 1.8
 */
public class KillException extends RuntimeException {
    public KillException(String message) {
        super(message);
    }
}
