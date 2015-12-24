package com.qf.entity;

/**
 * ���ﹷ
 * 
 * @author С��
 *
 */
public class Dog extends Pet {
	private String strain;

	public Dog() {
		super();
	}

	public Dog(String name, int health, int love, String strain) {
		super(name, health, love);
		this.strain = strain;
	}

	public void setStrain(String strain) {
		this.strain = strain;
	}

	public String getStrain() {
		return strain;
	}
}
