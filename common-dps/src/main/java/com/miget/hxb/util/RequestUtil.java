package com.miget.hxb.util;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class RequestUtil {

	public static String readData(HttpServletRequest request) {
		BufferedReader br = null;
		try {
			br = request.getReader();
			return IOUtils.toString(br);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			IOUtils.closeQuietly(br);
		}
	}
}
