package com.github.litaiqing.washer.server.screenshot.vo;

import com.github.litaiqing.washer.server.pool.thread.ThreadStatus;
import lombok.Builder;
import lombok.Data;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 13:53
 * @since JDK 1.8
 */
@Data
@Builder
public class ScreenshotResponse {

    private String taskCode;

    private ThreadStatus threadStatus;

    private Object data;

}
