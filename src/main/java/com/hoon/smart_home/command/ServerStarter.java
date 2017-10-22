package com.hoon.smart_home.command;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.hoon.smart_home.serial.IDataService;

@Component
public class ServerStarter {
	private final static Logger logger = LogManager.getLogger(ServerStarter.class);
	
	@PostConstruct
	public static boolean startService() {
		boolean l_result = true;
		IDataService l_service = null;
		IDataService l_dataService = null;

		String l_serviceName = "com.hoon.smart_home.serial.socket.SocketService";
		String l_dataServiceName = "com.hoon.smart_home.serial.socket.FTPService";
		try {
			if (l_serviceName != null && l_serviceName.length() > 0) {
				l_service = (IDataService) Class.forName(l_serviceName).newInstance();
				l_service.initialize();
				l_service.start();
				 logger.info("Smarthome service is started!!");
				 try {
					 l_dataService = (IDataService) Class.forName(l_dataServiceName).newInstance();
					 l_dataService.initialize();
					 l_dataService.start();
					 logger.info("ftp service is started!!");
				 }catch(Exception e) {
					 logger.fatal("Could not execute ftp service",e);
				 }
			}
		} catch (Exception e) {
			logger.fatal("Could not execute smarthome service",e);
			l_result = false;
		}
		return l_result;

	}
}
