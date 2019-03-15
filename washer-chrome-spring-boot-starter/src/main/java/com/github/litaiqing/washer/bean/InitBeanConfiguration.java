package com.github.litaiqing.washer.bean;

import com.github.litaiqing.washer.html.config.BeanImportSelector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * <h3>初始化Bean配置</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/3/11 15:29
 * @since JDK 1.8
 */
@Slf4j
@Configuration
@Import({BeanImportSelector.class})
public class InitBeanConfiguration {


}



