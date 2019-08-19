package com.jackson.netty.aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeClientHandler implements CompletionHandler<Void, AsyncTimeClientHandler>, Runnable{

	private AsynchronousSocketChannel client;
	private String host;
	private int port;
	private CountDownLatch latch;
	public AsyncTimeClientHandler(String host, int port){
	
		this.host = host;
		this.port = port;
		try{
			client = AsynchronousSocketChannel.open();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		latch = new CountDownLatch(1);
		//connect�����첽����
		//����A attachment AsynchronousSocketChannel�ĸ��������ڻص�֪ͨʱ��Ϊ��δ��ݣ������߿����Լ�����
		//����CompletionHandler���첽�����ص�֪ͨ�ӿڣ��ɵ�����ʵ��
		client.connect(new InetSocketAddress(host, port), this, this);
		try{
			latch.await();
		}catch(InterruptedException e1){
			e1.printStackTrace();
		}
		
		try{
			client.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	//�첽���ӳɹ�֮��ķ����ص�---complete����
	@Override
	public void completed(Void result, AsyncTimeClientHandler attachment) {
		// TODO Auto-generated method stub
		byte[] req = "QUERY TIME ORDER".getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
		writeBuffer.put(req);
		writeBuffer.flip();
		client.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer buffer) {
				// TODO Auto-generated method stub
				if(buffer.hasRemaining()){
					client.write(buffer, buffer, this);
				}else{
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					client.read(readBuffer,
								readBuffer,
								new CompletionHandler<Integer, ByteBuffer>() {

									@Override
									public void completed(Integer result, ByteBuffer buffer) {
										// TODO Auto-generated method stub
										buffer.flip();
										byte[] bytes = new byte[buffer.remaining()];
										buffer.get(bytes);
										String body;
										try{
											body = new String(bytes, "UTF-8");
											System.out.println("Now is :" + body);
											latch.countDown();
										}catch(UnsupportedEncodingException e){
											e.printStackTrace();
										}
									}

									@Override
									public void failed(Throwable exc, ByteBuffer attachment) {
										// TODO Auto-generated method stub
										try{
											client.close();
											latch.countDown();
										}catch(IOException e){
											
										}
									}
						
								});
				}
				
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				// TODO Auto-generated method stub
				exc.printStackTrace();
				try{
					client.close();
					latch.countDown();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void failed(Throwable exc, AsyncTimeClientHandler attachment) {
		// TODO Auto-generated method stub
		exc.printStackTrace();
		try{
			client.close();
			latch.countDown();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	
}
