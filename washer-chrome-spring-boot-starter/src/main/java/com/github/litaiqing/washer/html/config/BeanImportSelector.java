package com.github.litaiqing.washer.html.config;

import com.github.litaiqing.washer.core.webdriver.impl.ChromeHeadlessWebDriver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;

/**
 * <h3>动态注册Bean</h3><br/>
 * <br/>
 *
 * @author litaiqing
 * @version 1.0
 * @date: 2019/2/28 15:42
 * @since JDK 1.8
 */
@Slf4j
public class BeanImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        String[] beanNameArray = new String[]{
                ChromeHeadlessWebDriver.class.getName()
        };
        if (log.isDebugEnabled()) {
            log.debug("Import bean: {}", Arrays.toString(beanNameArray));
        }
        return beanNameArray;
    }
}
