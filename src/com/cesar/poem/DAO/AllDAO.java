package com.cesar.poem.DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cesar.poem.bean.Author;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AllDAO {
	
	private SQLiteDatabase database;
	public String[] authorColumns = {"id","authorName","time"};
	public String AUTHORTABLENAME = "author";
	
	public String[] tagColumns = {"tag"};
	public String STYLETABLENAME = "tag";
	
	public AllDAO(Context context) {
		database = DBManager.getInstance(context);
	}
	
	/**
	 * 获取某一朝代诗人
	 * @param time
	 * @return
	 */
	public List<String> getAuthorNameBytime(String time){
		String[] s = {time+" "};
		Cursor cursor = database.query(false, AUTHORTABLENAME, authorColumns, "time=?", s, null, null, null, null, null);
		return getNameByCursor(cursor);
	}

	public List<Author> getAllAuthor() {
		Cursor cursor = database.query(false, AUTHORTABLENAME, authorColumns, null, null, null, null, null, null, null);
		return getAuthorByCursor(cursor);
	}
	
	private List<Author> getAuthorByCursor(Cursor cursor){
		List<Author> authors = new ArrayList<Author>();
		while (cursor.moveToNext()) {
			Author author = new Author();
			author.setId(cursor.getInt(0));
			author.setAuthorName(cursor.getString(1));
			author.setTime(cursor.getString(2));
			authors.add(author);
		}
		cursor.close();
		return authors;
	}
	
	public List<String> getAuthorName(){
		Cursor cursor = database.query(false, AUTHORTABLENAME, authorColumns, null, null, null, null, null, null, null);
		return getNameByCursor(cursor);
	}

	private List<String> getNameByCursor(Cursor cursor){
		List<String> names = new ArrayList<String>();
		while (cursor.moveToNext()) {
			names.add(cursor.getString(1));
		}
		cursor.close();
		return names;
	}
	
	public List<String> getAllTime(){
		String[] times = {"先秦 ","两汉 ","魏晋 ","南北朝 ","隋代 ","唐代 ","五代 ","宋代 ","金朝 ","元代 ","明代 ","清代 ","近代 ","现代 "};
		return Arrays.asList(times);
	}
	
	public List<String> getAllStyle() {
		Cursor cursor = database.query(false, STYLETABLENAME, tagColumns, null, null, null, null, null, null, null);
		return getNameByCursor1(cursor);
	}
	
	private List<String> getNameByCursor1(Cursor cursor){
		List<String> names = new ArrayList<String>();
		while (cursor.moveToNext()) {
			names.add(cursor.getString(0));
		}
		cursor.close();
		return names;
	}
}
