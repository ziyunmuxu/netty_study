package com.jackson.netty.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * �����̳߳غ��������ʵ��һ��α�첽��I/Oͨ�ſ��
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
				//����Ĭ��ֵ
				port = 8080;
			}
		}
		
		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
		new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
	}
}
