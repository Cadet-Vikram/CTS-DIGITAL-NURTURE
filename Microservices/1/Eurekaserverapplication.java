package com.cognizant.eurekaserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * EurekaServerApplication – doc2 MANDATORY: Eureka Discovery Server.
 *
 * @EnableEurekaServer activates the Eureka service registry.
 *
 * Once running:
 *   Dashboard : http://localhost:8761
 *   Registry  : http://localhost:8761/eureka/apps  (XML list of registered services)
 *
 * "Instances currently registered with Eureka" section on the dashboard
 * will show ACCOUNT-SERVICE and LOAN-SERVICE once those are started.
 *
 * Start this FIRST before starting any other microservice.
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
        LOGGER.info("═══════════════════════════════════════════════════");
        LOGGER.info("Eureka Discovery Server started on port 8761");
        LOGGER.info("Dashboard: http://localhost:8761");
        LOGGER.info("Start account-service and loan-service next.");
        LOGGER.info("═══════════════════════════════════════════════════");
    }
}
