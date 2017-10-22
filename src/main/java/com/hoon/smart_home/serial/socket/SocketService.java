package com.hoon.smart_home.serial.socket;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hoon.smart_home.command.ServerStarter;
import com.hoon.smart_home.common.exeception.AnalysisException;
import com.hoon.smart_home.serial.IDataService;
import com.hoon.smart_home.serial.ThreadWorker;

public class SocketService implements IDataService{
	
	private final static Logger logger = LogManager.getLogger(SocketService.class);
	private int _port = 5000;
	private ServerSocket mServerSocket;
	private Thread _serviceWorker;
	private DataWorkerPool _workerPool;
	private ServerSocket _serverSocket;
	
    private Socket mSocket;

    private DataInputStream mIn;   
    private PrintWriter mOut;  
    
    public SocketService(){
    	 
    }

	@Override
	public void start() throws AnalysisException {
		try {
			_serviceWorker = new Thread(new ThreadWorker(this, "TListen"));
			_serviceWorker.setName("DataService:Listener");
			_serviceWorker.start();
			logger.info("Service is started and listening");
		} catch(Exception e) {
		}	
	}

	@Override
	public void initialize() throws AnalysisException {
		this._workerPool = new DataWorkerPool(10, SocketWorker.class);
		logger.info("Maximum # of working thread for SocketService is 10");
		
		
	}
	
	public void TListen() {
		try {
			_serverSocket = new ServerSocket(_port);
			Socket l_socket;
			while(true) {
				l_socket = _serverSocket.accept();
				logger.info("Waitting");
				SocketWorker l_worker = (SocketWorker) _workerPool.getWorker();
				l_worker.setSocket(l_socket);
			}
		} catch (Exception e) {
		}
	}
}
