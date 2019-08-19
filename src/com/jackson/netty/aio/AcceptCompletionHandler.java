package com.jackson.netty.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler
		implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {

	@Override
	public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
		// TODO Auto-generated method stub
		//问题：既然已经接受客户端成功，为什么还要再次调用accept方法
		//原因：调用AsynchronousServerSocketChannel的accept方法后，如果有新的客户端连接接入，系统将回调我们传入的CompletionHandler的实例的completed
		//方法，表示新的客户端接入成功，因为一个AsynchronousServerSocketChannel可以接受成千上万个客户端，所以需要继续调用它的accept方法，接受其它的客户端连接，最终形成一个循环
		attachment.asynchronousServerSocketChannel.accept(attachment, this);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		//参数含义
		//参数1：接受缓冲区，用于从异步Channel中读取数据包
		//参数2：异步Channel携带的附件，通知回调的时候作为入参使用
		//参数3：接受通知回调业务handler
		result.read(buffer, buffer, new ReadCompletionHandler(result));
		
	}

	@Override
	public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
		// TODO Auto-generated method stub
		exc.printStackTrace();
		attachment.latch.countDown();
	}
	
	

}
