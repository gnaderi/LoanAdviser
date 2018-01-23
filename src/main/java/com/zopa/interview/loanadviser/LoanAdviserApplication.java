package com.zopa.interview.loanadviser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class LoanAdviserApplication {
    private static final Logger logger = LoggerFactory.getLogger(LoanAdviserApplication.class);

    public static void main(String[] args) {
        logger.info("Loading Loan Adviser application ....");
        SpringApplication.run(LoanAdviserApplication.class, args);
    }
}
