package com.peace.personalhealthmanagementplatform;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PersonalHealthManagementPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalHealthManagementPlatformApplication.class, args);
        log.info("Personal Health Management Platform Application Started...");
    }
}
