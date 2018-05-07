package org.mpushframe.test;

import org.junit.Test;

public class ThreadTest {
	@Test
	public void testThreadA(){
		ThreadA a=new ThreadA();
		a.add(6);
		new Thread(a).start();
		a.add(9);
	}

}
