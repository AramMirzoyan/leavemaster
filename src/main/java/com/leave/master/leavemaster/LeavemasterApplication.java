package com.leave.master.leavemaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The main application class for the LeaveMaster application. It is the entry point of the Spring
 * Boot application and enables scheduling capabilities.
 */
@SpringBootApplication
@EnableScheduling
public class LeavemasterApplication {

  /**
   * The main method that serves as the entry point of the application.
   *
   * @param args command-line arguments passed during application startup.
   */
  public static void main(String[] args) {
    SpringApplication.run(LeavemasterApplication.class, args);
  }
}
