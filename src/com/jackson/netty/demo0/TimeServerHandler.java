package com.jackson.netty.demo0;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

//netty5ȡ����inbound��outbound��ͳһʹ��ChannelHandlerAdapter
public class TimeServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("the time server receive order: " + body);
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.write(resp);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
		//flush�������ǽ���Ϣ���Ͷ����е���Ϣд�뵽SocketChannel�з��͸��Է��������ܵĽǶȿ��ǣ�Ϊ�˷�ֹƵ���ػ���Selector������Ϣ����
		//Netty��write��������ֱ�ӽ���Ϣд��SocketChannel�У�����write����ֻ�ǰѴ����͵���Ϣ�ŵ����ͻ��������У���ͨ������flush������
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
	


}
