package com.huiuoo.pc.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @项目名称：picture-book-new
 * @类描述：
 * @创建人：lhf
 * @创建时间：2020/4/9
 * @version：V1.0
 */
@SpringBootApplication
@ComponentScan(value = {"com.huiuoo.pc.db","com.huiuoo.pc.common","com.huiuoo.pc.app"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
