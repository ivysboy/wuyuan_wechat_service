package com.happylifeplat.wechat.util;

import java.util.Random;

public class RandomUtils {
	 public static String getRandom(int num){
			//源始的内容
			String str = "0123456789";
			//存放随机数
			char[] rands = new char[num];
			StringBuffer sbu = new StringBuffer();
			Random  random   = new Random();
			for (int i=0;i<num;i++)
			{
				int index = random.nextInt(10);
				rands[i] = str.charAt(index);
				sbu.append(str.charAt(index));
			}
			return sbu.toString();
	 }
}
