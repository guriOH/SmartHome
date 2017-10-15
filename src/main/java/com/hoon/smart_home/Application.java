package com.hoon.smart_home;

import org.apache.log4j.Logger;

import com.hoon.smart_home.serial.IDataService;

public class Application {
	private final static Logger logger = Logger.getLogger(Application.class);

	public static void main(String[] args) {
		startService();
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
