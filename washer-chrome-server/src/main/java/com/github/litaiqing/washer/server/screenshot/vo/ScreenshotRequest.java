package com.github.litaiqing.washer.server.screenshot.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;

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
public class ScreenshotRequest {

    /**
     * 请求URL
     */
    @NotBlank(message = "url不能为空")
    private String url;

    /**
     * 回调URL
     */
    private String callbackUrl;

    /**
     * 截图本地保存位置
     */
    private String imageSavePath;

    /**
     * 加载完成XPath标记
     */
    private String loadFinishXpath;

    /**
     * 指定Xpath元素的高度
     */
    private String windowHeightByXpath;

    /**
     * 指定Xpath元素的宽度
     */
    private String windowWidthByXpath;

    /**
     * 默认窗口宽度
     */
    @Min(value = 0, message = "默认窗口宽度不能小于0")
    private Integer windowWidth;

    /**
     * 默认窗口高度
     */
    @Min(value = 0, message = "默认窗口高度不能小于0")
    private Integer windowHeight;

    /**
     * 是否最大化
     */
    private Boolean windowSizeMax = Boolean.FALSE;

    /**
     * 窗口扩大开关
     */
    private Boolean windowSizeAdd = Boolean.FALSE;

    /**
     * 窗口扩大宽度(像素)
     */
    private Integer widthAdd;

    /**
     * 窗口扩大高度(像素)
     */
    private Integer heightAdd;

    /**
     * 开始加载的等待时间(毫秒)-如果为非正数则表示不等待
     */
    @Min(value = 0L, message = "开始加载的等待时间不能小于0")
    private Long loadStartWaitTime = 0L;

    /**
     * 加载完成后的等待时间(毫秒)-如果为非正数则表示不等待
     */
    @Min(value = 0L, message = "加载完成后的等待时间不能小于0")
    private Long loadFinishWaitTime = 0L;

}
