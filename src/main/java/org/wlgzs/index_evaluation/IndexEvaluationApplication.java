package org.wlgzs.index_evaluation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//注释部分打war包时需要
//@ServletComponentScan
//public class IndexEvaluationApplication extends SpringBootServletInitializer {
public class IndexEvaluationApplication {

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IndexEvaluationApplication.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(IndexEvaluationApplication.class, args);
    }

}

