package com.cesar.poem.bean;

import java.io.Serializable;

public class PoemTagDetail implements Serializable{
	
	private static final long serialVersionUID = -7841626380368536127L;

	private int id;
	private int poemID;
	private String tag;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPoemID() {
		return poemID;
	}
	public void setPoemID(int poemID) {
		this.poemID = poemID;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}
