package com.cesar.poem.activity;

import com.cesar.poem.DAO.LastPoemDAO;
import com.example.poem.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity implements OnClickListener {

	private LastPoemDAO lastPoemDAO;
	
	private TextView setting_font_size;
	private TextView clean_mine;
	private TextView clean_record;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		getActionBar().setTitle("设置");
		initView();
	}

	private void initView() {
		lastPoemDAO = new LastPoemDAO(getApplicationContext());
		setting_font_size = (TextView) findViewById(R.id.setting_font_size);
		clean_mine = (TextView) findViewById(R.id.setting_clean_mine);
		clean_record = (TextView) findViewById(R.id.clean_record);
		
		setting_font_size.setOnClickListener(this);
		clean_mine.setOnClickListener(this);
		clean_record.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		if (v.equals(setting_font_size)) {
			intent.setClass(this, FontSizeActivity.class);
			startActivity(intent);
		} else if (v.equals(clean_mine)) {
			lastPoemDAO.deleteAllFromCollection();
			Toast.makeText(getApplicationContext(), "所有收藏均已经被删除", Toast.LENGTH_SHORT).show();
		} else if (v.equals(clean_record)) {
			lastPoemDAO.deleteAllFromLatest();
			Toast.makeText(getApplicationContext(), "所有浏览记录已经被删除", Toast.LENGTH_SHORT).show();
		}
	}

}