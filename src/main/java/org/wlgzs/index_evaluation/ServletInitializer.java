package org.wlgzs.index_evaluation;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author zsh
 * @company wlgzs
 * @create 2019-01-12 12:01
 * @Describe 序列化
 */
public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IndexEvaluationApplication.class);
    }
}
