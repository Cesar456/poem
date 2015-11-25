package com.cesar.poem.activity;

import com.cesar.poem.DAO.ImageDAO;
import com.example.poem.R;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;

public class AboutActivity extends Activity implements OnLongClickListener {

	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initView();
	}

	private void initView() {
		imageView = (ImageView) findViewById(R.id.give_me_money_image);
		imageView.setOnLongClickListener(this);
	}

	@Override
	public boolean onLongClick(View v) {
		ImageDAO.saveImage(BitmapFactory.decodeResource(getResources(), R.drawable.give_me_money), "weixin.png",getApplicationContext());
		return true;
	}
}
