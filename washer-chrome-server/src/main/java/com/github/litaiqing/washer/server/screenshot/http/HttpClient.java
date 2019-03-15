package com.github.litaiqing.washer.server.screenshot.http;

import com.github.litaiqing.washer.server.pool.ThreadBill;
import com.github.litaiqing.washer.server.pool.thread.ThreadStatus;
import com.github.litaiqing.washer.server.pool.vo.ThreadData;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 18:50
 * @since JDK 1.8
 */
@Slf4j
public class HttpClient {

    /**
     * GET 请求回调
     *
     * @param callbackUrl
     * @param threadId
     */
    public static void callbackByGET(String callbackUrl, String threadId) {
        log.info(" ==> [{}] callback url:{}", threadId, callbackUrl);
        try (CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build()) {
            List<NameValuePair> params = new ArrayList<>();
            ThreadData threadData = ThreadBill.query(threadId);
            params.add(new BasicNameValuePair("taskCode", threadId));
            if (threadData != null) {
                params.add(new BasicNameValuePair("threadStatus", threadData.getStatus().toString()));
                Map<String, Object> data = threadData.getData();
                if (data != null) {
                    data.forEach((k, v) -> {
                        params.add(new BasicNameValuePair(k, v.toString()));
                    });
                }
            } else {
                params.add(new BasicNameValuePair("threadStatus", ThreadStatus.NO_FOUND.toString()));
            }
            URI uri = new URIBuilder(callbackUrl).setParameters(params).build();

            // 配置信息
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置连接超时时间(单位毫秒)
                    .setConnectTimeout(5000)
                    // 设置请求超时时间(单位毫秒)
                    .setConnectionRequestTimeout(5000)
                    // socket读写超时时间(单位毫秒)
                    .setSocketTimeout(5000)
                    // 设置是否允许重定向(默认为true)
                    .setRedirectsEnabled(true).build();

            HttpGet httpGet = new HttpGet(uri);
            httpGet.setConfig(requestConfig);

            try (CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet)) {
                HttpEntity httpEntity = closeableHttpResponse.getEntity();
                log.info(" ==> [{}] callback status:{}", threadId, closeableHttpResponse.getStatusLine());
                log.info(" ==> [{}] response entity:{}", threadId, EntityUtils.toString(httpEntity));
            }
            ;
        } catch (Exception e) {
            log.error("{}", e);
        }
    }

}
