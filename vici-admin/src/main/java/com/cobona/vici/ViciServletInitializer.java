package com.cobona.vici;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Vici Web程序启动类
 *
 * @author cobona
 * @date 2017-05-21 9:43
 */
public class ViciServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ViciApplication.class);
    }

}
