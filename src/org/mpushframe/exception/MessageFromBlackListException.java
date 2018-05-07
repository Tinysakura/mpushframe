package org.mpushframe.exception;

/**
 * 收到来自黑名单中用户发来的消息的异常
 * @author Mr.Chen
 * date: 2018年5月5日 下午2:14:23
 */
public class MessageFromBlackListException extends Exception{
	private static final long serialVersionUID = 1L;

	public MessageFromBlackListException(){
		super();
	}
	
	public MessageFromBlackListException(String message){
		super(message);
	}
	
	public MessageFromBlackListException(Throwable cause){
		super(cause);
	}
	
	public MessageFromBlackListException(String message,Throwable cause){
		super(message,cause);
	}

}
