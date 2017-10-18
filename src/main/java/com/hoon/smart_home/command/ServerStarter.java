package com.hoon.smart_home.command;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hoon.smart_home.serial.IDataService;

@Component
public class ServerStarter {
	private final static Logger logger = Logger.getLogger(ServerStarter.class);
	
	@PostConstruct
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
