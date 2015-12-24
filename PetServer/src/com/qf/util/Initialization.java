package com.qf.util;

import java.io.File;

public class Initialization {
	public static final String path = "prodata.dat";

	public static void initDB() {
		File dbFile = new File(path);
		if (!dbFile.exists()) { // 文件不存在，创建数据库表
			JdbcUtil.executeUpdate(
					"CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(50), password VARCHAR(50))");
			JdbcUtil.executeUpdate("INSERT INTO users (username, password) VALUES ('xiaoming', '123456')");
			JdbcUtil.executeUpdate(
					"CREATE TABLE administrator(id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(50), password VARCHAR(50))");
			JdbcUtil.executeUpdate("INSERT INTO administrator (username, password) VALUES ('admin', 'admin')");
			JdbcUtil.executeUpdate(
					"CREATE TABLE pet(id INTEGER PRIMARY KEY AUTOINCREMENT, name varchar(30), health INTEGER, love INTEGER, strain VARCHAR(30), sex VARCHAR(30), uid INTEGER)");
		}
	}
}
