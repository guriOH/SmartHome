package com.hoon.smart_home;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hoon.smart_home.serial.IDataService;

@SpringBootApplication
public class Application {
	private final static Logger logger = LogManager.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
