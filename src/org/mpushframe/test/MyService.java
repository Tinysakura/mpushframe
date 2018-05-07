package org.mpushframe.test;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.mpushframe.core.XmppNodeHandler;

public class MyService {
	private SocketAcceptor acceptor;
	
	public MyService(){
		
	}
	
	public  void startserver(){
		System.out.println("starservice");
		try {
			//创建服务器监听
			acceptor=new NioSocketAcceptor();
			//编写并添加过滤器
			acceptor.getFilterChain().addLast("xmppcodecode", 
					new ProtocolCodecFilter(new TextLineCodecFactory()));
			
			//  1)设置缓存大小
			acceptor.getSessionConfig().setReadBufferSize( 2048 );
			//  2)设置回话空闲时间,即多长时间服务器与客户端未进行消息的收发便执行handler类中的idle方法
			acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 ); 
			//  3)绑定handler，handler中有各种事件的回调
			acceptor.setHandler(new XmppNodeHandler());
			//  4)绑定端口号
			try {
				acceptor.bind(new InetSocketAddress(998));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
    public static void main(String[] args){
    	MyService myService=new MyService();
    	myService.startserver();
    }

}
