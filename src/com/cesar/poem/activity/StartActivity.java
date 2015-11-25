package com.cesar.poem.activity;

import com.example.poem.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(intent);
				StartActivity.this.finish();
			}
		}, 3000);
	}
}
