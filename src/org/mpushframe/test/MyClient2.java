package org.mpushframe.test;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.mpushframe.core.ClientXmppHandler;

//demo
public class MyClient2 {
	private IoConnector connector;
	
	public MyClient2(){
		
	}
	
	//demo
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
			
			String stanzar2="<presence from=\"ogg@skh.whu.edu.cn\">"+
		    " <show>xa</show>"+
		    "<status>down the rabbit hole!</status>"+
		    "</presence>";
			session.write(stanzar2);
			
			String stanzar3="</stream:stream>";
			session.write(stanzar3);	
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
