package com.qf.entity;

/**
 * 管理员类
 * 
 * @author 小明
 *
 */
public class Administrator {
	private int id;
	private String username;
	private String password;

	public Administrator() {
		super();
	}

	public Administrator(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public Administrator(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Administrator [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
}
