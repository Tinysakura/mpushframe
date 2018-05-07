package org.mpushframe.test;

import java.util.concurrent.LinkedBlockingQueue;

public class ThreadA implements Runnable{
	private LinkedBlockingQueue<Integer> queue=new LinkedBlockingQueue<Integer>();
	
	public ThreadA() {
		// TODO Auto-generated constructor stub
		for(int i=0;i<5;i++){
			try {
				queue.put(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		java.util.Iterator<Integer> iterator=queue.iterator();
		while(true){
			while (iterator.hasNext()) {
				System.out.println(iterator.next());
			}
			
			iterator=queue.iterator();
		}
	}
	
	public void add(int a){
		try {
			queue.put(a);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
