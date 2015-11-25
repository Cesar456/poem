package com.cesar.poem.activity;

import java.util.ArrayList;
import java.util.List;

import com.cesar.poem.DAO.ImageDAO;
import com.cesar.poem.bean.Poem;
import com.cesar.poem.util.CommonUtil;
import com.example.poem.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 显示图片，并提供分享功能
 * 
 * @author Cesar
 */
public class ShowImageActivity extends Activity {
	private static int maxLength = 20;
	private static int maxHightNum = 18;
	private static float textSize = 30.0f;
	private CommonUtil commonUtil = CommonUtil.getInstance();
	private Poem poem;
	private ImageView image;
	private Bitmap bitmap1;
	private Bitmap bitmap2;
	private int height;
	private int width;
	private String abslutelyPath;//保存图片的绝对路径

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		getActionBar().setTitle("分享图片");
		initData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.share_image_activity_actionbar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String fileName = poem.getTitle() + System.currentTimeMillis() + ".png";
		if (item.getTitle().equals(getResources().getString(R.string.share_poem))) {
			abslutelyPath = ImageDAO.saveImageWithNoToast(bitmap2, fileName, getApplicationContext());
			showShare();
		} else if (item.getTitle().equals(getResources().getString(R.string.save_image))) {
			ImageDAO.saveImage(bitmap2, fileName, getApplicationContext());
		}
		return true;
	}

	private void initData() {
		poem = (Poem) getIntent().getSerializableExtra("poem");
		image = (ImageView) findViewById(R.id.share_image);
		BitmapFactory.Options options = new Options();
		options.inSampleSize = 1;
		bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.share_background,options);
		width = bitmap1.getWidth();
		height = bitmap1.getHeight();
		textSize = width*30/800;
		image.setBackground(creatDrawable(poem));
	}

	private Drawable creatDrawable(Poem poem) {
		List<String> lines = changePoem(poem);
		float left;
		float top;
		//TODO
		float linesHeight = (int) textSize/3*4;
		
		left = (width - commonUtil.getMaxLength(lines) * textSize) / 2;
		top = (height - (lines.size() * textSize - 2)) / 2;
		
		Log.e("info ", "left=" + left +" top "+ top +" width= "+width+" height"+height);
		
		if (top < height / 4) {
			top = height / 4;
		}
		
		int height1 = height + (lines.size() - maxHightNum > 0 ? (int) ((lines.size() - maxHightNum) / 1.5) : 0) * 60;
		
		bitmap2 = Bitmap.createBitmap(width, height1, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap2);
		Paint paint = new Paint();
		paint.setDither(true);
		paint.setFilterBitmap(true);
		Rect src = new Rect(0, 0, width, height1);
		Rect dst = new Rect(0, 0, width, height1);
		canvas.drawBitmap(getBitmap(lines.size()), src, dst, paint);// 将birmap1绘制上去
		Paint textpaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DEV_KERN_TEXT_FLAG);
		textpaint.setTextSize(textSize);
		textpaint.setColor(Color.BLACK);
		textpaint.setTypeface(Typeface.DEFAULT_BOLD);
		for (int t = 2; t < lines.size(); t++) {
			canvas.drawText(lines.get(t), left, top, textpaint);
			top = top + linesHeight;
		}
		float textSizeNew = textSize*7/6;
		//设置标题的显示方式
		if (lines.get(0).length() + lines.get(1).length() <= 15) {
			textpaint.setTextSize(textSizeNew);
			canvas.drawText("--《" + lines.get(0) + "》 " + lines.get(1),
					width - (lines.get(0).length() + lines.get(1).length() + textSize/6) * (textSizeNew), top + textSize*2/3, textpaint);
		} else {
			canvas.drawText(lines.get(0), width - lines.get(0).length() * textSize, top + textSize*2/3, textpaint);
			textpaint.setTextSize(textSizeNew);
			canvas.drawText(lines.get(1), width - lines.get(1).length() * (textSizeNew), top + textSize*2,
					textpaint);
		}
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return (Drawable) new BitmapDrawable(getResources(), bitmap2);
	}

	/**
	 * 如果诗歌行数太多，一页图片很明显无法全部显示，于是做一个生成图片的功能，返回一个长度适中的bitmap
	 * 
	 * @param size
	 * @return
	 */
	private Bitmap getBitmap(int size) {
		if (size <= maxHightNum) {
			return bitmap1;
		} else {
			int i = (int) ((size - maxHightNum) / 1.5);
			Bitmap top = BitmapFactory.decodeResource(getResources(), R.drawable.top_share);
			Bitmap middle = BitmapFactory.decodeResource(getResources(), R.drawable.middle_share);
			Bitmap buttom = BitmapFactory.decodeResource(getResources(), R.drawable.buttom_share);
			Bitmap result = Bitmap.createBitmap(bitmap1.getWidth(),
					bitmap1.getHeight() + (size - maxHightNum) * middle.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(result);
			canvas.drawBitmap(top, 0, 0, null);
			int totle = top.getHeight();
			for (; i > 0; i--) {
				canvas.drawBitmap(middle, 0, totle, null);
				totle += middle.getHeight();
			}
			canvas.drawBitmap(buttom, 0, totle, null);
			canvas.save(Canvas.ALL_SAVE_FLAG);
			canvas.restore();
			return result;
		}
	}

	/**
	 * 
	 * 将诗歌的内容转化为一行行文字，他的基本算法是：先按换行划分，然后每当一行字数超过maxLength，换行 同时去除掉所有的注释
	 * 得到的list的前两个值分别是标题和作者
	 * 
	 * @param poem
	 * @return
	 */
	private List<String> changePoem(Poem poem) {
		List<String> contents = new ArrayList<String>();
		contents.add(poem.getTitle());
		contents.add(poem.getAuthor());
		String[] lines = cleanString(poem.getContent()).split("\n");
		for (String s : lines) {
			if (s.length() <= maxLength) {
				contents.add(s.trim());
			} else {
				for (; s.length() > maxLength;) {
					contents.add(s.substring(0, maxLength).trim());
					s = s.substring(maxLength, s.length());
				}
				if (!"".equals(s.replaceAll("[,，。.?？！!“\"”]", "").trim())) {
					contents.add(s.trim());
				} else {
					contents.set(contents.size() - 1, contents.get(contents.size() - 1) + s);
				}
			}
		}
		return contents;
	}

	// 清除文字中的注释，将空格转化为换行
	public String cleanString(String s) {
		return s.replaceAll("\\([\\s\\S]*\\)", "").replaceAll("（[\\s\\S]*）", "").replace(" ", "\n");
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		oks.setTitle(getString(R.string.share));
		oks.setText(poem.getTitle());
		oks.setImagePath(abslutelyPath);// 确保SDcard下面存在此张图片
		Log.e("本地图片路径", abslutelyPath);
		oks.show(this);
	}
}
