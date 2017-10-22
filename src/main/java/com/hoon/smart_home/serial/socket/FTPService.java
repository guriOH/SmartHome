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

import com.hoon.smart_home.common.exeception.AnalysisException;
import com.hoon.smart_home.serial.IDataService;

public class FTPService implements IDataService{

	private final static Logger logger = LogManager.getLogger(FTPService.class);
	private int _port = 5001;
	private ServerSocket _serverSocket;
	
    private DataInputStream mIn;  
    private PrintWriter mOut;  
    
    
    public FTPService() {

    }
    
    
    @Override
    public void initialize() throws AnalysisException {
    	
    }
    
	@Override
	public void start() throws AnalysisException {
		try {
			_serverSocket = new ServerSocket(_port);
			Socket _socket  = _serverSocket.accept();
			try {
	          
	            mIn = new DataInputStream(_socket.getInputStream());
	            mOut = new PrintWriter(_socket.getOutputStream());
	            
	            BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(new File("out.txt")));
	            int len = -1;
				byte[] data = new byte[1024];
				int data_size = 0;
				
				while ((len = mIn.read(data)) != -1) {
					data_size += len;
					//System.out.println(data_size);
					/*data[data_size] = (byte)len;
					data_size++;
					// �����̸� while���� ��������.
					if (len == 0x0a) {
						break;
					}*/
					if(data_size > 1024 * 1000*100){
						SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmm");
						fout = new BufferedOutputStream(new FileOutputStream(new File(date.format(new Date())+".txt")));
						data_size = len;
					}
					
					fout.write(data,0,len);
				}
				fout.flush();
				System.out.printf("data_size : %d \n", data_size);
				System.out.println(new String(data,"UTF-8").trim()); // ����Ʈ �迭�� ���ڿ��� ����� ������ ���� ��.
				
	            mOut.flush();

	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if(mIn != null) {
					mIn = null;
				}
				if(mOut != null) {
					mOut.close();
				}
	        }
		} catch (Exception e) {
		}
		
	}

}
