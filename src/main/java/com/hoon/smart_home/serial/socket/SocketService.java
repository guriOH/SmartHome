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

import com.hoon.smart_home.common.exeception.AnalysisException;
import com.hoon.smart_home.serial.IDataService;
import com.hoon.smart_home.serial.ThreadWorker;

public class SocketService implements IDataService{
	
	private int _port = Integer.MIN_VALUE;
	private ServerSocket mServerSocket;
	private Thread _serviceWorker;
	private DataWorkerPool _workerPool;
	private ServerSocket _serverSocket;
	
    private Socket mSocket;

    private DataInputStream mIn;    // ������ ���
    private PrintWriter mOut;  // ������ ���
    
    public SocketService(){
    	 try {
	            mServerSocket = new ServerSocket(5000);
	            System.out.println("���� ����!!!");
	            // �����尡 ���� �ְ�

	            // ���� ��û�� ������ ����
	            mSocket = mServerSocket.accept();
	            System.out.println("Ŭ���̾�Ʈ�� ���� ��");

	            mIn = new DataInputStream(mSocket.getInputStream());

	            mOut = new PrintWriter(mSocket.getOutputStream());
	            BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(new File("out.txt")));
	            int len = -1;
				byte[] data = new byte[1024];
				int data_size = 0;
				
				while ((len = mIn.read(data)) != -1) {
					data_size += len;
					System.out.println(data_size);
					/*data[data_size] = (byte)len;
					data_size++;
					// �����̸� while���� ��������.
					if (len == 0x0a) {
						break;
					}*/
					if(data_size > 1024 * 1000){
						SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmm");
						fout = new BufferedOutputStream(new FileOutputStream(new File(date.format(new Date())+".txt")));
						data_size = len;
					}
					
					fout.write(data,0,len);
				}
				fout.flush();
				System.out.printf("data_size : %d \n", data_size);
				System.out.println(new String(data,"UTF-8").trim()); // ����Ʈ �迭�� ���ڿ��� ����� ������ ���� ��.
				
	            // Ŭ���̾�Ʈ���� ���� ���ڿ� ���
	            System.out.println(mIn.readLine());

	            // Ŭ���̾�Ʈ�� ���ڿ� ����
	            mOut.println("���� �� �Ǿ���");
	            mOut.flush();

	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            // ���� �ݱ� (���� ����)
	            try {
	                mSocket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            try {
	                mServerSocket.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
    }

	@Override
	public void start() throws AnalysisException {
		try {
			_serviceWorker = new Thread(new ThreadWorker(this, "TListen"));
			_serviceWorker.setName("DataService:Listener");
			_serviceWorker.start();
			System.out.println("Service is started and listening");
		} catch(Exception e) {
		}	
	}

	@Override
	public void initialize() throws AnalysisException {
		this._workerPool = new DataWorkerPool(10, SocketWorker.class);
		System.out.println("Maximum # of working thread for SocketService is 10");
		
		
	}
	
	public void TListen() {
		try {
			_serverSocket = new ServerSocket(_port);
			Socket l_socket;
			while(true) {
				l_socket = _serverSocket.accept();
				SocketWorker l_worker = (SocketWorker) _workerPool.getWorker();
				l_worker.setSocket(l_socket);
			}
		} catch (Exception e) {
		}
	}
}
