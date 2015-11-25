package com.cesar.poem.DAO;

import java.util.ArrayList;
import java.util.List;

import com.cesar.poem.bean.Poem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class LastPoemDAO {

	private SQLiteDatabase database;
	public String[] columns = { "id", "title", "time", "author", "content", "appreciate", "analytical", "lable",
			"authorId", "isCollect", "collectTime" };
	public String TABLENAME = "latest_poem";

	public LastPoemDAO(Context context) {
		database = DBManager.getInstance(context);
	}

	public List<Poem> getAll() {
		Cursor cursor = database.query(false, TABLENAME, columns, null, null, null, null, "-lastTime", null, null);
		return getPoemsByCursor(cursor);
	}

	public void saveOrUpdate(Poem poem) {
		ContentValues values = new ContentValues();
		values.put("id", poem.getId());
		values.put("title", poem.getTitle());
		values.put("time", poem.getTime());
		values.put("author", poem.getAuthor());
		values.put("content", poem.getContent());
		values.put("appreciate", poem.getAppreciate());
		values.put("analytical", poem.getAnalytical());
		values.put("lable", poem.getLable());
		values.put("authorId", poem.getAuthorId());
		values.put("lastTime", System.currentTimeMillis());

		Cursor cursor = database.query(false, TABLENAME, columns, "id=" + poem.getId(), null, null, null, null, null,
				null);
		// 若之前已经存在过，则更新时间，否则直接插入
		if (getPoemsByCursor(cursor).size() >= 1) {
			database.update(TABLENAME, values, "id=" + poem.getId(), null);
		} else {
			database.insert(TABLENAME, null, values);
		}
	}

	public List<Poem> getPoemsByCursor(Cursor cursor) {
		List<Poem> poems = new ArrayList<Poem>();
		while (cursor.moveToNext()) {
			Poem poem = new Poem();
			poem.setId(cursor.getInt(0));
			poem.setTitle(cursor.getString(1));
			poem.setTime(cursor.getString(2));
			poem.setAuthor(cursor.getString(3));
			poem.setContent(cursor.getString(4));
			poem.setAppreciate(cursor.getString(5));
			poem.setAnalytical(cursor.getString(6));
			poem.setLable(cursor.getString(7));
			poem.setAuthorId(cursor.getInt(8));
			poems.add(poem);
		}
		cursor.close();
		return poems;
	}

	public List<Poem> getAllCollectionPoem() {
		Cursor cursor = database.query(false, TABLENAME, columns, "isCollect=1", null, null, null, "-collectTime", null,
				null);
		return getPoemsByCursor(cursor);
	}

	/**
	 * 数据库中0代表没有被收藏，1代表被收藏
	 * 
	 * @param poemId
	 */
	public void collectPoem(int poemId) {
		ContentValues values = new ContentValues();
		values.put("isCollect", 1);
		values.put("collectTime", System.currentTimeMillis());
		database.update(TABLENAME, values, "id=" + poemId, null);
	}

	public void donotcollectPoem(int poemId) {
		ContentValues values = new ContentValues();
		values.put("isCollect", 0);
		database.update(TABLENAME, values, "id=" + poemId, null);
	}

	public boolean isCollect(int poemId) {
		Cursor cursor = database.query(false, TABLENAME, columns, "id=" + poemId + " and isCollect=1", null, null, null,
				null, null, null);
		if (getPoemsByCursor(cursor).size() >= 1) {
			return true;
		}
		return false;
	}
	
	public void deleteAllFromCollection() {
		ContentValues values = new ContentValues();
		values.put("isCollect", 0);
		database.update(TABLENAME, values, null, null);
	}
	public void deleteAllFromLatest() {
		database.delete(TABLENAME, null, null);
	}

}
