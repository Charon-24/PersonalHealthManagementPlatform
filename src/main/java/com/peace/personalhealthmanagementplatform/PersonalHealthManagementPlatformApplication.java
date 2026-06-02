package com.peace.personalhealthmanagementplatform;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@MapperScan("com.peace.personalhealthmanagementplatform.module.*.mapper")
@EnableScheduling
public class PersonalHealthManagementPlatformApplication {

    public static void main(String[] args) {
        log.info("Personal Health Management Platform Application Starting...");
        SpringApplication.run(PersonalHealthManagementPlatformApplication.class, args);
        log.info("Personal Health Management Platform Application Started...");
    }
}
