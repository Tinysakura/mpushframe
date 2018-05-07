package org.mpushframe.test;

import org.junit.Test;
import org.mpushframe.util.GenerateUniqueID;

public class TestGeneration {
	@Test
	public void testGeneration(){
		for(int i=0;i<10;i++){
			System.out.println(GenerateUniqueID.generate());
		}
	}

}
