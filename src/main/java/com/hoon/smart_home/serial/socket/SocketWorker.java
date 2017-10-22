package com.hoon.smart_home.serial.socket;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hoon.smart_home.serial.AbstractDataWorker;

public class SocketWorker extends AbstractDataWorker {

	private Socket _socket;
	
	public SocketWorker(String p_workerName, DataWorkerPool p_workerPool) {
		super(p_workerName, p_workerPool);
		System.out.println("SocketWorker is created.");
	}
	
	
	@Override
	protected void returnResult(String p_task) {
		if(this._socket != null) {
			
			try {
				
			} catch(Exception e) {
				
			}
			
			try {
				
				//this.logger.trace("The result of eDataRealm execution is {}", p_task.getResult().toString());
			} catch(Exception e) {
			} finally {
				
			}
		}
	}
	public void setSocket(Socket p_socket) {
		_socket = p_socket;
		
		DataInputStream mIn = null;    // ������ ���
		PrintWriter mOut = null;  // ������ ���
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
				/*if(data_size > 1024 * 1000*100){
					SimpleDateFormat date = new SimpleDateFormat("yyyyMMddhhmm");
					fout = new BufferedOutputStream(new FileOutputStream(new File(date.format(new Date())+".txt")));
					data_size = len;
				}*/
				
				//fout.write(data,0,len);
			}
			fout.flush();
			System.out.printf("data_size : %d \n", data_size);
			System.out.println(new String(data,"UTF-8").trim()); // ����Ʈ �迭�� ���ڿ��� ����� ������ ���� ��.
			
			this.setTask(new String(data,"UTF-8").trim());
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
	}
	
	private void readData(InputStream p_stream, byte[] p_buffer, int p_position, int p_length) throws IOException {
		int l_readSize = -1;
		int l_totalSize = 0;
		int l_currentPosition = p_position;
		while (true) {
			l_readSize = p_stream.read(p_buffer, l_currentPosition, p_length - l_totalSize);
			if (l_readSize != -1) {
				l_totalSize += l_readSize;
				l_currentPosition += l_readSize;
				if (l_totalSize == p_length) {
					break;
				}
			} else {
				//throw new SocketException("Returned '-1'.");
			}
		}
	}
}
