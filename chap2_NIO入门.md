# NIO入门 #  
***
## NIO类库简介 ##
**与Socket类和ServerSocket类相对应，NIO也提供了SocketChannel和ServerSocketChannel两种不同的套接字。这两种新增的通道都支持阻塞和非阻塞两种模式**

* **缓冲区Buffer**  
在NIO库中，*所有数据都是用缓冲区处理的*  在读取数据时，它是直接读取到缓冲区中的；在写入数据时，写入到缓冲区。任何时候访问NIO中的数据，都是通过缓冲区进行操作  
缓冲区实质上是一个**数组**。通常它是一个字节数组（ByteBuffer），也可以使用其他种类的数组。每一种Java基本类型（除了Boolean类型）都有对应的缓冲区。 
  
* **通道Channel**  
Channel是一个通道，可以通过它读取和写入数据；通道与流的不同之处在于*通道是双向的*，流只是在一个方向上移动（一个流必须是InputStream或者OutputStream的子类）。  
Channel可以分成两大类：用于网络读写的SelectableChannel和用于文件操作的FileChannel。其中ServerSocketChannel和SocketChannel都是SelectableChannel的子类	

* **多路复用器Selector**  
多路复用器是JavaNIO编程的基础，Selector会不断地轮询注册在其上的Channel，如果某个Channel上面有新的TCP连接接入、读和写事件，这个Channel就处于就绪状态，会被Selector轮询出来，然后通过SelectinKey可以获取就绪Channel的集合。进行后续的I/O操作。  
*由于JDK使用了epoll()替代传统的select实现*，并没有最大连接句柄的限制。

----------
