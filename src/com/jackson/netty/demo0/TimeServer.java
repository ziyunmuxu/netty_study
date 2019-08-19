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
		//���÷���˵�NIO�߳���
		//���ڷ���˽��ܿͻ��˵�����
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		//���ڽ���SocketChannel�������д
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childHandler(new ChildChannelHandler());
			
			//�󶨶˿ڣ�ͬ���ȴ��ɹ�
			ChannelFuture f = b.bind(port).sync();
			
			System.out.println("�ɹ������˿�");
			
			//�ȴ�����˼����˿ڹرգ��������ȴ��������·�ر�֮��main�Ż��˳�
			f.channel().closeFuture().sync();
		}finally{
			//�����˳����ͷ���Դ����Դ
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
				//����Ĭ��ֵ
			}
		}
		new TimeServer().bind(port);
	}
	
	
}
