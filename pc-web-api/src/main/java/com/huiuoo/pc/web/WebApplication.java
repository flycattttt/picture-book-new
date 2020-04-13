package com.huiuoo.pc.web;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @描述：后台管理启动类
 * @创建人：LHF
 * @创建时间：2020/4/10 14:53
 * @param 
 * @return 
 */
@SpringBootApplication
@ComponentScan(value = {"com.huiuoo.pc.db","com.huiuoo.pc.common","com.huiuoo.pc.web"})
public class WebApplication {
    public static void main(String[] args) {

        SpringApplication.run(WebApplication.class,args);
    }
}
