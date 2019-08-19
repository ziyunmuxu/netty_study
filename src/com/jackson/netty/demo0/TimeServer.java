package com.jackson.netty.demo0;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {
	public void bind(int port) throws Exception{
		//配置服务端的NIO线程组
		//用于服务端接受客户端的连接
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//用于进行SocketChannel的网络读写
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childHandler(new ChildChannelHandler());
			
			//绑定端口，同步等待成功
			ChannelFuture f = b.bind(port).sync();
			
			System.out.println("成功监听端口");
			
			//等待服务端监听端口关闭，阻塞，等待服务端链路关闭之火，main才会退出
			f.channel().closeFuture().sync();
		}finally{
			//优雅退出，释放资源池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			arg0.pipeline().addLast(new TimeServerHandler());
		}	
	}
	
	public static void main(String[] args) throws Exception {
		int port = 8080;
		if(args != null && args.length > 0){
			try{
				port = Integer.valueOf(args[0]);
			}catch(NumberFormatException e){
				//采用默认值
			}
		}
		new TimeServer().bind(port);
	}
	
	
}
