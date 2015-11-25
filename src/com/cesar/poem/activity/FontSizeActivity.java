package com.cesar.poem.activity;

import com.example.poem.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class FontSizeActivity extends Activity implements OnSeekBarChangeListener{
	
	// 显示当前的字体大小
		private TextView title_textExample;
		private SeekBar title_seekBar;
		private TextView author_textExample;
		private SeekBar author_seekBar;
		private TextView content_textExample;
		private SeekBar content_seekBar;

		private int titleSize;
		private int authorSize;
		private int contentSize;

		private String title = "TITLE";
		private String author = "AUTHOR";
		private String content = "CONTENT";

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_set_font_size);
			initView();
		}

		private void initView() {

			title_seekBar = (SeekBar) findViewById(R.id.activity_setting_title_seekbar);
			title_textExample = (TextView) findViewById(R.id.activity_setting_title_example);
			author_seekBar = (SeekBar) findViewById(R.id.activity_setting_author_seekbar);
			author_textExample = (TextView) findViewById(R.id.activity_setting_author_example);
			content_seekBar = (SeekBar) findViewById(R.id.activity_setting_content_seekbar);
			content_textExample = (TextView) findViewById(R.id.activity_setting_content_example);

			title_textExample.setTextSize(getTitleSize());
			title_seekBar.setProgress(getTitleSize());
			author_textExample.setTextSize(getAuthorSize());
			author_seekBar.setProgress(getAuthorSize());
			content_textExample.setTextSize(getContentSize());
			content_seekBar.setProgress(getContentSize());
			title_seekBar.setOnSeekBarChangeListener(this);
			author_seekBar.setOnSeekBarChangeListener(this);
			content_seekBar.setOnSeekBarChangeListener(this);

		}

		private SharedPreferences getMySharedPreferences() {
			return getSharedPreferences("poemSetting", Activity.MODE_PRIVATE);
		}

		public int getTitleSize() {
			titleSize = getMySharedPreferences().getInt(title, 15);
			return titleSize;
		}

		public void setTitleSize(int titleSize) {
			title_textExample.setTextSize(titleSize);
			getMySharedPreferences().edit().putInt(title, titleSize).commit();
		}

		public int getAuthorSize() {
			authorSize = getMySharedPreferences().getInt(author, 15);
			return authorSize;
		}

		public void setAuthorSize(int authorSize) {
			author_textExample.setTextSize(authorSize);
			getMySharedPreferences().edit().putInt(author, authorSize).commit();
		}

		public int getContentSize() {
			contentSize = getMySharedPreferences().getInt(content, 15);
			return contentSize;
		}

		public void setContentSize(int contentSize) {
			content_textExample.setTextSize(contentSize);
			getMySharedPreferences().edit().putInt(content, contentSize).commit();
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			if (seekBar.equals(title_seekBar)) {
				setTitleSize(progress);
			} else if (seekBar.equals(author_seekBar)) {
				setAuthorSize(progress);
			} else if (seekBar.equals(content_seekBar)) {
				setContentSize(progress);
			}
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
		}
	}
