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
	 * 初始化数据
	 */
	public static void init() {
		initDB();
		loadProfile();
	}

	/**
	 * 初始化创建数据库
	 */
	private static void initDB() {
		File dbFile = new File(path);
		if (!dbFile.exists()) { // 文件不存在，则创建该文件，然后创建数据库表
			JdbcUtil.executeUpdate(
					"CREATE TABLE Cat(id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(30), health INTEGER, love INTEGER, sex VARCHAR(30))");
			JdbcUtil.executeUpdate(
					"CREATE TABLE Dog(id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(30), health INTEGER, love INTEGER, strain VARCHAR(30))");
		}
	}

	/**
	 * 初始化读取配置文件
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
	 * 保存配置文件
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
