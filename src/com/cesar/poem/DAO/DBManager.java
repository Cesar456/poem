package com.cesar.poem.DAO;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DBManager {
	
	private final int BUFFER_SIZE = 1024;
	private static SQLiteDatabase database = null;
	private Context context;
	private static String path = "/poem/poem.sqlite";

	public DBManager(Context context) {
		this.context = context;
	}
	
	public static SQLiteDatabase getInstance(Context context){
		if(database==null){
			DBManager dbManager = new DBManager(context);
			database = dbManager.openDatabase();
		}
		return database;
	}

	private SQLiteDatabase openDatabase() {
		File sdFile = Environment.getExternalStorageDirectory();
		File poemPath = new File(sdFile.getPath() + path);
		
		if (!poemPath.exists()) {
			try {
				// 创建目录
				File pmsPaht = new File(sdFile.getPath() + "/poem/");
				pmsPaht.mkdirs();
				File poemDB = new File(sdFile.getPath() + path);
				poemDB.createNewFile();
				
				AssetManager am = this.context.getAssets();
				InputStream is = am.open("poem.sqlite");

				FileOutputStream fos = new FileOutputStream(poemPath);

				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.flush();
				fos.close();
				is.close();
//				am.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return SQLiteDatabase.openOrCreateDatabase(poemPath, null);
	}
}
