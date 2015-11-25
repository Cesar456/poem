package com.cesar.poem.bean;

import java.io.Serializable;

public class Poem implements Serializable{
	
	private static final long serialVersionUID = -5507900518574671422L;
	
	
	private int id;
	private String title;
	private String time;
	private String author;
	private String content;
	private String appreciate;//赏析
	private String analytical;//翻译
	private String lable;
	private int authorId;
	public Poem(int id, String title, String time, String author,
			String content, String appreciate, String analytical, String lable,
			int authorId) {
		super();
		this.id = id;
		this.title = title;
		this.time = time;
		this.author = author;
		this.content = content;
		this.appreciate = appreciate;
		this.analytical = analytical;
		this.lable = lable;
		this.authorId = authorId;
	}
	public Poem() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAppreciate() {
		return appreciate;
	}
	public void setAppreciate(String appreciate) {
		this.appreciate = appreciate;
	}
	public String getAnalytical() {
		return analytical;
	}
	public void setAnalytical(String analytical) {
		this.analytical = analytical;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	@Override
	public String toString() {
		return "Poem [id=" + id + ", title=" + title + ", time=" + time
				+ ", author=" + author + ", content=" + content
				+ ", appreciate=" + appreciate + ", analytical=" + analytical
				+ ", lable=" + lable + ", authorId=" + authorId + "]";
	}
	
}
