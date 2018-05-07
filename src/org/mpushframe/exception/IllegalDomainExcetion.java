package org.mpushframe.exception;

/**
 * 非法域名的异常类
 * @author Mr.Chen
 * date: 2018年5月5日 下午1:53:25
 */
public class IllegalDomainExcetion extends Exception{
	
	private static final long serialVersionUID = 1L;

	public IllegalDomainExcetion(){
		super();
	}
	
	public IllegalDomainExcetion(String message){
		super(message);
	}
	
	public IllegalDomainExcetion(Throwable cause){
		super(cause);
	}
	
	public IllegalDomainExcetion(String message,Throwable cause){
		super(message,cause);
	}
}
