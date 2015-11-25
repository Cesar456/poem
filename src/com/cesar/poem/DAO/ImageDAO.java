package com.cesar.poem.DAO;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

//保存图片的类
public class ImageDAO {

	/** 首先默认个文件保存路径 */
	private static final String SAVE_PIC_PATH = Environment.getExternalStorageDirectory().getPath();// 获取sd卡的根目录
	public static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/poem/picture/";// 保存的确切位置

	// 以多线程方式存储图片
	public synchronized static boolean saveImage(final Bitmap bitmap, final String fileName, final Context context) {
		Thread thread = new SaveImage(bitmap, fileName, context);
		thread.start();
		Toast.makeText(context, "图片" + fileName + "已经保存", Toast.LENGTH_SHORT).show();
		return true;
	}

	public synchronized static String saveImageWithNoToast(Bitmap bitmap, String fileName, Context context) {
		String subForder = ImageDAO.SAVE_REAL_PATH;
		File foder = new File(subForder);
		if (!foder.exists()) {
			foder.mkdirs();
		}
		try {
			File myCaptureFile = new File(subForder, fileName);
			if (!myCaptureFile.exists()) {
				myCaptureFile.createNewFile();
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
				bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);
				bos.flush();
				bos.close();
				Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri uri = Uri.fromFile(myCaptureFile);
				intent.setData(uri);
				context.sendBroadcast(intent);
			}
			return myCaptureFile.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}

class SaveImage extends Thread {
	private Bitmap bm;
	private String fileName;
	private Context context;

	public SaveImage(Bitmap bm, String fileName, Context context) {
		this.bm = bm;
		this.fileName = fileName;
		this.context = context;
	}

	@Override
	public void run() {
		saveImage11();
	}

	private void saveImage11() {
		String subForder = ImageDAO.SAVE_REAL_PATH;
		File foder = new File(subForder);
		if (!foder.exists()) {
			foder.mkdirs();
		}
		try {
			File myCaptureFile = new File(subForder, fileName);
			if (!myCaptureFile.exists()) {
				myCaptureFile.createNewFile();
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
				bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
				bos.flush();
				bos.close();
				Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri uri = Uri.fromFile(myCaptureFile);
				intent.setData(uri);
				context.sendBroadcast(intent);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
