package com.jackson.netty.aio;

/**
 * 使用JDK1.7中升级提供的AIO，异步IO
 * @author ziyunmuxu
 *
 */

public class TimeServer {
	public static void main(String[] args) {
		int port = 8080;
		if(args !=  null && args.length > 0){
			try{
				port = Integer.valueOf(args[0]);
			}catch(NumberFormatException e){
				//采用默认值
			}
		}
		
		AsyncTimeServerHandler timeServer = new AsyncTimeServerHandler(port);
		new Thread(timeServer, "AIO-AsyncTimeServerHandler-001").start();
	}
}
