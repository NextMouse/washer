package com.github.litaiqing.washer.server.pool;

import com.github.litaiqing.washer.server.pool.exception.KillException;
import com.github.litaiqing.washer.server.pool.thread.ThreadStatus;
import com.github.litaiqing.washer.server.pool.vo.ThreadData;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 17:03
 * @since JDK 1.8
 */
@Slf4j
public class ThreadBill {

    private static Map<String, ThreadData> THREAD_BILL = new ConcurrentHashMap<>(16);

    public static Map<String, ThreadData> getThreadBill() {
        return THREAD_BILL;
    }

    public static void register(String threadId, ThreadStatus threadStatus) {
        log.info(" ==> [{}] register thread bill [threadStatus:{}]", threadId, threadStatus);
        if (query(threadId) != null) {
            query(threadId).setStatus(threadStatus);
        } else {
            getThreadBill().put(threadId, new ThreadData(threadId, threadStatus));
        }
    }

    /**
     * 清空所有数据
     *
     * @return
     */
    public static Map<String, ThreadData> clearAll() {
        Map<String, ThreadData> oldBill = THREAD_BILL;
        THREAD_BILL = new ConcurrentHashMap<>(16);
        log.info(" ==> clear all thread bill, size:{}", oldBill.size());
        return oldBill;
    }

    /**
     * 删除数据
     *
     * @param threadId
     * @return
     */
    public static ThreadData clear(String threadId) {
        ThreadData threadData = getThreadBill().remove(threadId);
        log.info(" ==> [{}] remove thread data:{}", threadId, threadData);
        return threadData;
    }


    /**
     * 查询数据
     *
     * @param threadId
     * @return
     */
    public static ThreadData query(String threadId) {
        return getThreadBill().get(threadId);
    }


    /**
     * 判断线程是否杀死
     *
     * @param threadId
     * @return
     */
    public static boolean isKill(String threadId) {
        ThreadData threadData = query(threadId);
        if (threadData == null) {
            return true;
        }
        return ThreadStatus.KILL.equals(threadData.getStatus());
    }

    /**
     * 尝试杀死线程
     *
     * @param threadId
     */
    public static void tryKill(String threadId) {
        if (isKill(threadId)) {
            throw new KillException(String.format("[%s] is kill！", threadId));
        }
    }

}
