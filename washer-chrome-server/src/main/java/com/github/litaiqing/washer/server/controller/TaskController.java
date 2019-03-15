package com.github.litaiqing.washer.server.controller;

import com.github.litaiqing.washer.server.pool.ThreadBill;
import com.github.litaiqing.washer.server.pool.thread.ThreadStatus;
import com.github.litaiqing.washer.server.pool.vo.ThreadData;
import com.github.litaiqing.washer.server.screenshot.vo.ScreenshotResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 21:52
 * @since JDK 1.8
 */
@Slf4j
@RestController
@RequestMapping("/task")
@Api(tags = "TaskController|任务管理")
public class TaskController {

    /**
     * 查看任务结果
     *
     * @param taskCode
     * @return
     */
    @ApiOperation(value = "查询任务",
            notes = "任务状态有：准备（READYING）、运行（RUNNING）、停止（STOP）、错误（ERROR）、杀死（KILL）、不存在（NO_FOUND）六种",
            httpMethod = "GET"
    )
    @ApiImplicitParam(name = "taskCode", value = "任务流水号", required = true, dataType = "String")
    @RequestMapping(value = "query")
    public ResponseEntity<ScreenshotResponse> query(String taskCode) {
        try {
            return query(taskCode, null);
        } catch (Exception e) {
            log.error("{}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 杀死任务
     *
     * @param taskCode
     * @return
     */
    @ApiOperation(value = "杀死任务", notes = "会立即杀死执行指定任务的线程",
            httpMethod = "GET"
    )
    @ApiImplicitParam(name = "taskCode", value = "任务流水号", required = true, dataType = "String")
    @RequestMapping(value = "kill")
    public ResponseEntity<ScreenshotResponse> kill(String taskCode) {
        try {
            return query(taskCode, t -> ThreadBill.register(t, ThreadStatus.KILL));
        } catch (Exception e) {
            log.error("{}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ResponseEntity<ScreenshotResponse> query(String taskCode, java.util.function.Consumer<String> consumer) {
        // 查询数据
        ThreadData threadData = ThreadBill.query(taskCode);
        if (threadData == null) {
            return ResponseEntity.notFound().build();
        }
        if (consumer != null) {
            consumer.accept(taskCode);
        }
        return ResponseEntity.ok().body(
                ScreenshotResponse.builder()
                        .taskCode(taskCode)
                        .threadStatus(threadData.getStatus())
                        .data(threadData.getData())
                        .build());
    }

    /**
     * 清空数据
     *
     * @param taskCode
     * @return
     */
    @ApiOperation(value = "清空数据", notes = "清除指定任务在服务器上保存的数据",
            httpMethod = "GET"
    )
    @ApiImplicitParam(name = "taskCode", value = "任务流水号", required = true, dataType = "String")
    @RequestMapping(value = "clear")
    public ResponseEntity<ThreadData> clear(String taskCode) {
        try {
            return ResponseEntity.ok().body(ThreadBill.clear(taskCode));
        } catch (Exception e) {
            log.error("{}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 清空所有数据
     *
     * @return
     */
    @ApiOperation(value = "清空所有数据", notes = "清除所有任务在服务器上保存的数据",
            httpMethod = "GET"
    )
    @RequestMapping(value = "clearAll", method = RequestMethod.GET)
    public ResponseEntity<Map<String, ThreadData>> clearAll() {
        try {
            return ResponseEntity.ok().body(ThreadBill.clearAll());
        } catch (Exception e) {
            log.error("{}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 查询库里任务数量
     *
     * @return
     */
    @ApiOperation(value = "查询服务器中任务数量", notes = "查询当前服务器上所有的任务数量",
            httpMethod = "GET"
    )
    @RequestMapping(value = "size", method = RequestMethod.GET)
    public ResponseEntity<Integer> size() {
        try {
            return ResponseEntity.ok().body(ThreadBill.getThreadBill().size());
        } catch (Exception e) {
            log.error("{}", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
