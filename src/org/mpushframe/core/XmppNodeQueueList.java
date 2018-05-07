package org.mpushframe.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 维护一个解析XmppNode队列的集合
 * 每当有新用户与推送框架连接就为该用户创建一个XmppNode队列并加入到集合中
 * 这个类是单例的，即一个推送服务中始终只有一个队列集合
 * 使用map作为实现集合的底层数据结构，key即为系统为首次登陆用户生成的随机id也是用户对于消息处理队列的id
 * 这个id会在session中设置用于区分不同客户端的会话
 * 为了提高并发性可以设计相关的线程调度算法
 * @author Mr.Chen
 * date: 2018年4月28日 下午8:36:25
 */
public class XmppNodeQueueList {
	public static volatile XmppNodeQueueList xmppNodeQueueList;
	
	Map<String, XmppNodeQueue> queueMap;
	Map<String, Thread> threadMap;
	
	private XmppNodeQueueList(){
		queueMap=new HashMap<String, XmppNodeQueue>();
		threadMap=new HashMap<String,Thread>();
	}
	
	public static XmppNodeQueueList getInstance(){
		if(xmppNodeQueueList==null){
			synchronized (XmppNodeQueueList.class) {
				if(xmppNodeQueueList==null){
					xmppNodeQueueList=new XmppNodeQueueList();
				}
			}
		}
		
		return xmppNodeQueueList;
	}
	
    /**
     * 向QueueList中添加一个queue
     */
	public synchronized void put(XmppNodeQueue xmppNodeQueue){
		queueMap.put(xmppNodeQueue.getId(), xmppNodeQueue);
		Thread thread=new Thread(xmppNodeQueue);
		threadMap.put(xmppNodeQueue.getId(),thread);
		thread.start();
	}
	
	/**
	 * 从QueueList中获取一个queue
	 * @return 
	 */
	public synchronized XmppNodeQueue get(String id){
		return queueMap.get(id);
	}
	
	/**
	 * 停止一个线程
	 */
	public synchronized void stop(String id){
		System.out.println("尝试销毁线程");
		queueMap.get(id).destory();
		/**
		 * 这里interrupt是为了唤醒因为阻塞队列为空而陷入等待的线程
		 */
		threadMap.get(id).interrupt();
		System.out.println(threadMap.get(id).isAlive());
	}
}
