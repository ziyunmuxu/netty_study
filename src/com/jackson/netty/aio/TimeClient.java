package com.jackson.netty.aio;

public class TimeClient {
	public static void main(String[] args) {
		int port = 8080;
//		try{
//			port = Integer.valueOf(args[0]);
//		}catch(NumberFormatException e){
//			//����Ĭ��ֵ
//		}
		
		new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
	}
}
