package com.github.litaiqing.washer.core.utils;

import java.lang.reflect.Array;

/**
 * <h3>数组工具类</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/1 14:00
 * @since JDK 1.8
 */
public class ArrayUtil {


    /**
     * 将t元素添加到新数组的0位置，并将args按序放在后面
     *
     * @param first
     * @param array
     * @return
     */
    public static String[] add0ByString(String first, String[] array) {
        String[] tmp = new String[array.length + 1];
        tmp[0] = first;
        for (int i = 1; i < tmp.length; i++) {
            tmp[i] = array[i - 1];
        }
        return tmp;
    }

}
