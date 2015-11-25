package com.cesar.poem.bean;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 5930043390101708709L;
	
	private String createtime;
	private String lastModifyTime;
	private int userId;
	private String userEmail;
	private String passWord;
	private int statu;
	private String userName;
	public User(String createtime, String lastModifyTime, int userId,
			String userEmail, String passWord, int statu, String userName) {
		super();
		this.createtime = createtime;
		this.lastModifyTime = lastModifyTime;
		this.userId = userId;
		this.userEmail = userEmail;
		this.passWord = passWord;
		this.statu = statu;
		this.userName = userName;
	}
	public User() {
	}

	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public int getStatu() {
		return statu;
	}
	public void setStatu(int statu) {
		this.statu = statu;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String toString() {
		return "User [createtime=" + createtime + ", lastModifyTime="
				+ lastModifyTime + ", userId=" + userId + ", userEmail="
				+ userEmail + ", passWord=" + passWord + ", statu=" + statu
				+ ", userName=" + userName + "]";
	}
}
