package com.qf.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * HttpUrlConnection ������
 * 
 * @author С��
 *
 */
public final class HttpUrlConnectionUtil {
	private HttpUrlConnectionUtil() {
	}

	/**
	 * ����ָ�� url�������ݲ���
	 * 
	 * @param url
	 *            URL
	 * @param param
	 *            ����
	 * @return ��Ӧ����
	 */
	public static byte[] connect(String url, String param) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(3000);
			conn.setDoOutput(true);
			conn.getOutputStream().write(param.getBytes());
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedOutputStream bos = new BufferedOutputStream(baos);
				int b;
				while ((b = bis.read()) != -1) {
					bos.write(b);
				}
				bos.flush();

				return baos.toByteArray();
			}
			return null;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
