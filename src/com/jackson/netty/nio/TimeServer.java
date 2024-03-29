package com.jackson.netty.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * NIO编程
 * @author ziyunmuxu
 *
 */

public class TimeServer {

	public static void main(String[] args) {
		int port = 8080;
		if(args != null && args.length > 0){
			try{
				port = Integer.valueOf(args[0]);
			}catch(NumberFormatException e){
				//采用默认值
				port = 8080;
			}
		}
		
		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
		new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
	}
}
