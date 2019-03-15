package com.github.litaiqing.washer.core.utils;

import com.github.litaiqing.washer.core.config.properties.WebDriverCoreProperties;
import com.github.litaiqing.washer.core.exec.SimpleExecClient;

import java.io.File;
import java.io.IOException;

/**
 * <h3>浏览器客户端</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/1 13:51
 * @since JDK 1.8
 */
public class BrowserExecClient {

    /**
     * 执行命令，默认是用户当前工作目录
     *
     * @param args 参数
     * @return
     */
    public static Process exec(String... args) throws IOException {
        WebDriverCoreProperties webDriverCoreProperties = WebDriverCoreProperties.get();
        return SimpleExecClient.exec(ArrayUtil.add0ByString(webDriverCoreProperties.getPath(), args));
    }

    /**
     * 执行命令，指定工作目录
     *
     * @param args 参数
     * @return
     */
    public static Process exec(File workPath, String... args) throws IOException {
        WebDriverCoreProperties webDriverCoreProperties = WebDriverCoreProperties.get();
        return SimpleExecClient.exec(workPath, ArrayUtil.add0ByString(webDriverCoreProperties.getPath(), args));
    }


}
