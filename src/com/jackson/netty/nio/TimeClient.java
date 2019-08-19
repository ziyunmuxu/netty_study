package com.jackson.netty.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TimeClient {
	public static void main(String[] args) {
		int port = 8080;
		if(args != null && args.length >0){
			try{
				System.out.println("args[0]:" + args[0]);
				port = Integer.valueOf(args[0]);
			}catch(NumberFormatException e){
				//����Ĭ��ֵ
			}
		}
		
		new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();
		
	}
	
}
