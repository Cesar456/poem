package com.cesar.poem.view;

import com.example.poem.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class IconView extends View {

	// 默认颜色
	private int mcolor = 0xFF45C01A;
	private Bitmap mIconBitmap;
	private String mText = "";
	private int mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12,
			getResources().getDisplayMetrics());

	private Canvas mCanvas;
	private Bitmap mbitmap;
	private Paint mPaint;

	private float mAlpha = 0.0f;

	private Rect mIconRect;
	private Rect mTextBound;
	private Paint mTextPaint;

	public IconView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public IconView(Context context) {
		this(context, null);
	}

	public IconView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IconView);

		for (int i = 0; i < typedArray.getIndexCount(); i++) {
			int attr = typedArray.getIndex(i);
			switch (attr) {
			case R.styleable.IconView_icon:
				BitmapDrawable drawable = (BitmapDrawable) typedArray.getDrawable(attr);
				mIconBitmap = drawable.getBitmap();
				break;
			case R.styleable.IconView_color:
				mcolor = typedArray.getColor(attr, 0xFF45C01A);
				break;
			case R.styleable.IconView_text:
				mText = typedArray.getString(attr);
				break;
			case R.styleable.IconView_text_size:
				mTextSize = (int) typedArray.getDimension(attr,
						TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
				break;
			}
		}
		typedArray.recycle();

		mTextBound = new Rect();
		mTextPaint = new Paint();
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(0xff555555);
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int iconWidth = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(),
				getMeasuredHeight() - getPaddingBottom() - getPaddingTop() - mTextBound.height());
		int left = (getMeasuredWidth() - iconWidth) / 2;
		int top = (getMeasuredHeight() - mTextBound.height() - iconWidth) / 2;
		mIconRect = new Rect(left, top, left + iconWidth, top + iconWidth);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(mIconBitmap, null, mIconRect, null);

		int alpha = (int) Math.ceil(255 * mAlpha);// malpha即0-1的滑动进度,向上取整
		// 内存准备mbitmap,setAlpha,纯色，xfermode，图标
		setupTarget(alpha);
		// 1.绘制原文本 2，绘制变色文本 （设置为绿色，再设置一个alpha,即绘制两层文本，然后修改透明度
		drawSourceText(canvas, alpha);
		drawTargetText(canvas, alpha);
		canvas.drawBitmap(mbitmap, 0, 0, null);
	}
	
	// 在原有的基础上重绘一次
		private void drawTargetText(Canvas canvas, int alpha) {
			// 整体的canvas
			mTextPaint.setColor(mcolor);
			mTextPaint.setAlpha(alpha);
			canvas.drawText(mText, (getMeasuredWidth()-mTextBound.width())/2,
					mIconRect.bottom + mTextBound.height(), mTextPaint);
		}
		private void drawSourceText(Canvas canvas, int alpha) {
			mTextPaint.setColor(Color.BLACK);// 这个color应该和底色相同
			mTextPaint.setAlpha(255 - alpha);
			
			//y坐标是baselin的坐标，不是top的坐标
			canvas.drawText(mText, (getMeasuredWidth()-mTextBound.width())/2,
					mIconRect.bottom + mTextBound.height(), mTextPaint);
		}
		// 在内存中绘制克变色的icon
		private void setupTarget(int alpha) {
			// 绘制icon
			mbitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Config.ARGB_8888);
			mCanvas = new Canvas(mbitmap);
			mPaint = new Paint();
			mPaint.setColor(mcolor);
			mPaint.setAntiAlias(true);
			mPaint.setDither(true);
			mPaint.setAlpha(alpha);
			mCanvas.drawRect(mIconRect, mPaint);
			// 提供图片的十六种变换效果,DST_IN取两层绘制交集。显示下层。
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
			// 绘制图标而不是底部，所以重新设置paint
			mPaint.setAlpha(255);
			mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);
		}
		public void setIconAlpha(float alpha){
			this.mAlpha = alpha;
			invalidateView();
		}
		//重绘
		private void invalidateView() {
			//ui线程
			if(Looper.getMainLooper() == Looper.myLooper()){
				invalidate();
			}
			//非ui线程
			else {
				postInvalidate();
			}
		}
		public void setIconText(String text){
			this.mText = text;
		} 
		public void setIcon(Bitmap bitmap){
			this.mIconBitmap = bitmap;
		}
}
