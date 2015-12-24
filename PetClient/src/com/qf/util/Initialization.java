package com.qf.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Initialization {
	private static Properties properties = new Properties();
	public static final String path = "prodata.dat";
	private static final String profile = "profile.ini";
	public static String server = "http://localhost:8080/PetServer";
	public static int healthMinus = 5;
	public static int loveMinus = 5;
	public static int updateFreq = 5;
	public static int refreshFreq = 5;
	public static int uid = 1;

	/**
	 * ��ʼ������
	 */
	public static void init() {
		initDB();
		loadProfile();
	}

	/**
	 * ��ʼ���������ݿ�
	 */
	private static void initDB() {
		File dbFile = new File(path);
		if (!dbFile.exists()) { // �ļ������ڣ��򴴽����ļ���Ȼ�󴴽����ݿ��
			JdbcUtil.executeUpdate(
					"CREATE TABLE Cat(id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(30), health INTEGER, love INTEGER, sex VARCHAR(30))");
			JdbcUtil.executeUpdate(
					"CREATE TABLE Dog(id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(30), health INTEGER, love INTEGER, strain VARCHAR(30))");
		}
	}

	/**
	 * ��ʼ����ȡ�����ļ�
	 */
	private static void loadProfile() {
		File file = new File(profile);
		if (file.exists()) {
			try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(profile))) {
				properties.load(bis);
				server = properties.getProperty("server");
				healthMinus = Integer.parseInt(properties.getProperty("healthMinus"));
				loveMinus = Integer.parseInt(properties.getProperty("loveMinus"));
				updateFreq = Integer.parseInt(properties.getProperty("updateFreq"));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
		}
	}

	/**
	 * ���������ļ�
	 */
	public static void saveProfile() {
		try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(profile))) {
			properties.put("server", server);
			properties.put("healthMinus", String.valueOf(healthMinus));
			properties.put("loveMinus", String.valueOf(loveMinus));
			properties.put("updateFreq", String.valueOf(updateFreq));
			properties.store(bos, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
