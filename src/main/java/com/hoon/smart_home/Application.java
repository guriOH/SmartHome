package com.hoon.smart_home;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hoon.smart_home.serial.IDataService;

public class Application {
	 	
	    public static void main(String[] args) {
	        Application server = new Application();
	    }
	    
	    public static boolean startService(){
	    	boolean l_result = true;
	    	IDataService l_service = null;
	    	
	    	String l_serviceName = "SocketService";
	    	try{
	    		if(l_serviceName != null && l_serviceName.length()>0){
	    			l_service = (IDataService)Class.forName(l_serviceName).newInstance();
					l_service.initialize();
					l_service.start();
					System.out.println("Service is started!!");
	    		}
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		l_result = false;
	    	}
			return l_result;
	    	
	    }
}
