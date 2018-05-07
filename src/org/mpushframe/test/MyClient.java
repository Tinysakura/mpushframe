package org.mpushframe.test;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.mpushframe.core.ClientXmppHandler;

public class MyClient {
	private IoConnector connector;
	
	public MyClient(){
		
	}
	
	public void cilentconnect(){
		//1.创建ioservice
		connector = new NioSocketConnector();
		//2.编写并添加过滤器
		connector.getFilterChain().addLast("xmppcode", 
				new ProtocolCodecFilter(
					new TextLineCodecFactory()));
		//3.注册Handler
		connector.setHandler(new ClientXmppHandler());
		//4.绑定套接字建立连接
		ConnectFuture future=connector.connect(new InetSocketAddress("127.0.0.1",998)); 
		future.awaitUninterruptibly();
		//5.获取session对象用于与服务器端进行通讯
		IoSession session=future.getSession();
		if(session!=null){
			String stanzar1="<stream:stream>";
			session.write(stanzar1);
			
			String stanzar2=
					  "<message from=\"suke@test.org\" to=\"beta@skh.whu.edu.cn\" type=\"chat\">"+
							    "<body>Who are you?</body>"+
							    "<subject>Query</subject>"+
							  "</message>";
			session.write(stanzar2);
			
			String stanzar3=
					  "<message from=\"blackuser@test2.org\" to=\"beta@skh.whu.edu.cn\" type=\"chat\">"+
							    "<body>Who are you?</body>"+
							    "<subject>Query</subject>"+
							  "</message>";
			session.write(stanzar3);
			
			String stanzar4=
					  "<message from=\"testuser@test3.org\" to=\"beta@skh.whu.edu.cn\" type=\"chat\">"+
							    "<body>Who are you?</body>"+
							    "<subject>Query</subject>"+
							  "</message>";
			session.write(stanzar4);
			
			String stanzar10=
					  "<message from=\"testuser@test3.org\" to=\"beta@skh.whu.edu.cn\" type=\"chat\">"+
							    "<body>Who are you?</body>"+
							    "<subject>Query</subject>"+
							  "</message>";
			session.write(stanzar10);
			
			//master
			String stanzar5="</stream:stream>";
			session.write(stanzar5);	
		}	
		else{
			System.out.println("session is null");
		}
	}
	
	public static void main(String []args){
		MyClient client=new MyClient();
		client.cilentconnect();
	}

}
