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
		//startService();
	}

	public static boolean startService() {
		boolean l_result = true;
		IDataService l_service = null;

		String l_serviceName = "com.hoon.smart_home.serial.socket.SocketService";
		try {
			if (l_serviceName != null && l_serviceName.length() > 0) {
				l_service = (IDataService) Class.forName(l_serviceName).newInstance();
				l_service.initialize();
				l_service.start();
				 logger.info("Service is started!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			l_result = false;
		}
		return l_result;

	}
}
