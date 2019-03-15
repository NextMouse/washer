package com.github.litaiqing.washer.core.exec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <h3>简单的exec调用客户端</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/1 10:41
 * @since JDK 1.8
 */
@Slf4j
public class SimpleExecClient {

    /**
     * 可执行文件调用
     *
     * @param command
     * @return
     */
    public static Process exec(String[] command) throws IOException {
        return exec(null, command);
    }

    /**
     * 可执行文件调用, 指定工作目录
     *
     * @param command
     * @return
     */
    public static Process exec(File workPath, String[] command) throws IOException {
        Assert.notEmpty(command, "command is null!");
        String cmd = Stream.of(command).collect(Collectors.joining(" "));
        if (log.isDebugEnabled()) {
            log.debug("exec cmd:{}", cmd);
        }
        Process process = Runtime.getRuntime().exec(cmd, null, workPath);
        if (log.isDebugEnabled()) {
            log.debug("exec exitValue:{}", process.exitValue());
        }
        return process;
    }

}
