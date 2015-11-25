package com.cesar.poem.bean;

import java.io.Serializable;

public class Author implements Serializable{
	
	private static final long serialVersionUID = -2642666719822265575L;

	private int id;
	private String authorName;
	private String life;
	private String time;
	public Author(int id, String authorName, String life, String time) {
		super();
		this.id = id;
		this.authorName = authorName;
		this.life = life;
		this.time = time;
	}
	public Author(){
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public String getLife() {
		return life;
	}
	public void setLife(String life) {
		this.life = life;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "Author [id=" + id + ", authorName=" + authorName + ", life="
				+ life + ", time=" + time + "]";
	}
}
