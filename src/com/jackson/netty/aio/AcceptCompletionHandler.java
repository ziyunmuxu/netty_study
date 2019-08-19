package com.jackson.netty.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptCompletionHandler
		implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler> {

	@Override
	public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
		// TODO Auto-generated method stub
		//���⣺��Ȼ�Ѿ����ܿͻ��˳ɹ���Ϊʲô��Ҫ�ٴε���accept����
		//ԭ�򣺵���AsynchronousServerSocketChannel��accept������������µĿͻ������ӽ��룬ϵͳ���ص����Ǵ����CompletionHandler��ʵ����completed
		//��������ʾ�µĿͻ��˽���ɹ�����Ϊһ��AsynchronousServerSocketChannel���Խ��ܳ�ǧ������ͻ��ˣ�������Ҫ������������accept���������������Ŀͻ������ӣ������γ�һ��ѭ��
		attachment.asynchronousServerSocketChannel.accept(attachment, this);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		//��������
		//����1�����ܻ����������ڴ��첽Channel�ж�ȡ���ݰ�
		//����2���첽ChannelЯ���ĸ�����֪ͨ�ص���ʱ����Ϊ���ʹ��
		//����3������֪ͨ�ص�ҵ��handler
		result.read(buffer, buffer, new ReadCompletionHandler(result));
		
	}

	@Override
	public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
		// TODO Auto-generated method stub
		exc.printStackTrace();
		attachment.latch.countDown();
	}
	
	

}
