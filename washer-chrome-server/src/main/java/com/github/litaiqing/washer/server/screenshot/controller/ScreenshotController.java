package com.github.litaiqing.washer.server.screenshot.controller;

import com.github.litaiqing.washer.server.pool.SimpleThreadPool;
import com.github.litaiqing.washer.server.pool.ThreadBill;
import com.github.litaiqing.washer.server.pool.vo.ThreadData;
import com.github.litaiqing.washer.server.screenshot.runnable.ScreenshotRunnable;
import com.github.litaiqing.washer.server.screenshot.vo.ScreenshotRequest;
import com.github.litaiqing.washer.server.screenshot.vo.ScreenshotResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <h3>屏幕截图</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 13:48
 * @since JDK 1.8
 */
@Slf4j
@RestController
@RequestMapping("/screenshot")
@Api(tags = "ScreenshotController|网页屏幕截图")
public class ScreenshotController {

    /**
     * 请求屏幕截图
     *
     * @param screenshotRequest
     * @return
     */
    @ApiOperation(value = "请求截图", notes = "可自定义配置，请求任务taskCode会立即返回",
            httpMethod = "GET"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "请求URL", required = true, dataType = "String"),
            @ApiImplicitParam(name = "callbackUrl", value = "回调URL", dataType = "String"),
            @ApiImplicitParam(name = "imageSavePath", value = "截图本地保存位置", dataType = "String"),
            @ApiImplicitParam(name = "loadFinishXpath", value = "加载完成XPath标记", dataType = "String"),
            @ApiImplicitParam(name = "windowHeightByXpath", value = "指定Xpath元素的高度", dataType = "String"),
            @ApiImplicitParam(name = "windowWidthByXpath", value = "指定Xpath元素的宽度", dataType = "String"),
            @ApiImplicitParam(name = "windowWidthByXpath", value = "指定Xpath元素的宽度", dataType = "String"),
            @ApiImplicitParam(name = "windowWidth", value = "默认窗口宽度", dataType = "Integer"),
            @ApiImplicitParam(name = "windowHeight", value = "默认窗口高度", dataType = "Integer"),
            @ApiImplicitParam(name = "windowSizeMax", value = "是否最大化", dataType = "Boolean"),
            @ApiImplicitParam(name = "windowSizeAdd", value = "是否扩大窗口", dataType = "Boolean"),
            @ApiImplicitParam(name = "widthAdd", value = "窗口扩大宽度(像素)", dataType = "Integer"),
            @ApiImplicitParam(name = "heightAdd", value = "窗口扩大高度(像素)", dataType = "Integer"),
            @ApiImplicitParam(name = "loadStartWaitTime", value = "开始加载的等待时间(毫秒)-如果为非正数则表示不等待", dataType = "Long"),
            @ApiImplicitParam(name = "loadFinishWaitTime", value = "加载完成后的等待时间(毫秒)-如果为非正数则表示不等待", dataType = "Long")
    })
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<ScreenshotResponse> start(@Valid ScreenshotRequest screenshotRequest) {
        try {
            log.info(" ==> filePath:%s", filePath);
            final String taskCode = SimpleThreadPool.execute(new ScreenshotRunnable(filePath, screenshotRequest));
            // 查询数据
            ThreadData threadData = ThreadBill.query(taskCode);
            return ResponseEntity.ok().body(
                    ScreenshotResponse.builder()
                            .taskCode(taskCode)
                            .threadStatus(threadData.getStatus())
                            .build());
        } catch (Exception e) {
            log.error("{}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Value("${headless-browser.file-path:/data/%s.png}")
    private String filePath;

    @ApiOperation(value = "下载图片", notes = "获取指定任务编号下生成的图片",
            httpMethod = "GET"
    )
    @ApiImplicitParam(name = "taskCode", value = "任务流水号", required = true, dataType = "String")
    @RequestMapping(value = "/img", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE})
    @ResponseBody
    public ResponseEntity<byte[]> img(String taskCode) throws IOException {
        try {
            ThreadData threadData = ThreadBill.query(taskCode);
            if (threadData != null
                    && !CollectionUtils.isEmpty(threadData.getData())
                    && threadData.getData().get("filePath") != null) {
                return ResponseEntity.ok(fileToBytes(threadData.getData().get("filePath").toString()));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("{}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private byte[] fileToBytes(String filePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(new File(filePath));
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

}
