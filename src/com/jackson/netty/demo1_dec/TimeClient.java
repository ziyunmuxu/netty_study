package com.jackson.netty.demo1_dec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeClient {
	public void connect(int port, String host) throws Exception{
		//配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			//之前错误的bug，Socket类型选错b.group(group).channel(NioServerSocketChannel.class)
			b.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>(){

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						// TODO Auto-generated method stub
						ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
						ch.pipeline().addLast(new StringDecoder());
						ch.pipeline().addLast(new TimeClinetHandler());
					}
				});
			
			//发起异步连接操作
			ChannelFuture f = b.connect(host, port).sync();
			
			//等待客户端链路关闭
			f.channel().closeFuture().sync();
		}finally{
			//优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		int port = 8080;
		if(args != null && args.length >0){
			try{
				port = Integer.valueOf(args[0]);
			}catch(NumberFormatException e){
				//采用默认值
			}
		}
		new TimeClient().connect(port, "127.0.0.1");
	}
}
