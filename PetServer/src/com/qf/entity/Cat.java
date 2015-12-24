package com.qf.entity;

/**
 * ����è
 * 
 * @author С��
 *
 */
public class Cat extends Pet {
	private String sex;

	public Cat() {
		super();
	}

	public Cat(String name, int health, int love, String sex) {
		super(name, health, love);
		this.sex = sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return sex;
	}
}
