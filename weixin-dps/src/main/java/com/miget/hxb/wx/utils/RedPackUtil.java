package com.miget.hxb.wx.utils;

import java.util.Random;

/**
 * 红包工具类
 */
public class RedPackUtil {

	/**
	 * 生成特定位数的随机数字
	 * @param length
	 * @return
	 */
	public static String getRandomNum(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			val += String.valueOf(random.nextInt(10));
		}
		return val;
	}

}