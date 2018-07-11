package com.icbc.zoro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;

@SuppressWarnings("unused")
@SpringBootApplication
@RestController
@MapperScan("com.icbc.zoro.*.mapper")
@EnableScheduling
public class Application {
	private static Logger logger = LogManager.getLogger(Application.class.getName());
    @RequestMapping("/")
    public String greeting() {
        return "/index.html";
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("SpringApplication start");
    }
}
