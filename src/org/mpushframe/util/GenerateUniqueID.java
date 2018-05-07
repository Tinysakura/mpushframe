package org.mpushframe.util;

/**
 * 为每一个连接生成一个唯一的ID
 * ID是由数字和小写字母组成的16位随机字符串
 * @author Mr.Chen
 * date: 2018年4月30日 下午2:22:45
 */
public class GenerateUniqueID {
	
	public static String generate(){
		StringBuilder sb=new StringBuilder();
		int random;
		
		for(int i=0;i<16;i++){
			random=(int)(Math.random()*2);
			if(random==0){
				sb.append(generateNumber());
			}else{
				sb.append(generateWord());
			}
		}
		
		return sb.toString();
	}
	
	private static String generateNumber(){
		return (int)(Math.random()*10)+"";
	}
	
	private static char generateWord(){
		char result=(char)(97+(int)(Math.random()*26));
		return result;
	}

}
