package com.github.litaiqing.washer.server.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h3></h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/13 19:03
 * @since JDK 1.8
 */
public class DateUtils {

    public static final String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static DateTimeFormatter FORMATTERR_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern(PATTERN_YYYYMMDDHHMMSS);

    public static String simpleNow() {
        return FORMATTERR_YYYYMMDDHHMMSS.format(LocalDateTime.now());
    }


}
