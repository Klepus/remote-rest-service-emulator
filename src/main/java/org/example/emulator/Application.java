/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package org.example.emulator;

import org.example.emulator.controller.RestServiceEmulator;
import org.example.emulator.parser.FileParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Value("${path-to-folder}")
    private String pathToFolder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ServletRegistrationBean customServletBean() {
        FileParser fileParser = new FileParser(pathToFolder);
        ServletRegistrationBean bean = new ServletRegistrationBean(new RestServiceEmulator(fileParser), "/");
        return bean;
    }

}