package com.bsy.business.user.bean;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class User {
	private String id;
	private String account;
	private String pwd;
	private String name;
	private String sex;
	private String age;
	private String email;
	private byte[] photo;
	private String role_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", pwd=" + pwd + ", name=" + name + ", sex=" + sex + ", age="
				+ age + ", email=" + email + ", photo=" + Arrays.toString(photo) + "]";
	}
	
}
