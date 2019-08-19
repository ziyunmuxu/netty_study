# TCP粘包/拆包问题 #  
***
## TCP粘包/拆包 ##
*TCP是个“流”协议，就是没有界限的一串数据。TCP底层并不了解上层业务数据的具体含义，它会根据TCP缓冲区的实际情况进行包的划分，所以在业务上认为，一个完整的包可能会被TCP拆分为多个包进行发送，也有可能把多个小的包封装成一个大的数据包发*  
*产生原因*  
**1. 应用程序write写入的字节大小大于套接口发送缓冲区大小  
2. 进行MSS大小的TCP分段  
3. 以太网帧的payload大于MTU进行IP分片**  
*解决方式*  
**1.消息定长  
2. 包尾增加回车换行符进行分割，如FTP协议  
3. 将消息分为消息头和消息体，消息头中包含消息总长度**

* **LineBasedFrameDecoder**  
LineBaseFrameDecoder 以换行符为结束标志的解码器。工作原理是它依次遍历ByteBuf中的可读字节，判断是否有“\n”,"\r\n",如果有，就从可读索引到结束位置区间的字节组成一行。
  
* **StringDecoder**  
将接受到的对象转换成字符串。

* **DelimiterBasedFrameDecoder**  
分隔符

* **FixedLengthFrameDecoder**  
固定长度解码器