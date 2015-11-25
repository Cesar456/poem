package com.cesar.poem.fragment;

import com.cesar.poem.activity.AboutActivity;
import com.cesar.poem.activity.SearchActivity;
import com.cesar.poem.activity.SettingActivity;
import com.example.poem.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MineFragment extends Fragment implements OnClickListener {

	private TextView classy_textview1;
	private TextView classy_textview2;
	private TextView classy_textview3;
	
	public MineFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_mine, container, false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		
		classy_textview1 = (TextView) view.findViewById(R.id.mine_textview1);
		classy_textview2 = (TextView) view.findViewById(R.id.mine_textview2);
		classy_textview3 = (TextView) view.findViewById(R.id.mine_textview3);

		classy_textview1.setOnClickListener(this);
		classy_textview2.setOnClickListener(this);
		classy_textview3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		
		switch (v.getId()) {
		case R.id.mine_textview1:
			intent.setClass(getActivity(), SearchActivity.class);
			intent.putExtra("classfy", "COLLECT");
			startActivity(intent);
			break;
		case R.id.mine_textview2:
			intent.setClass(getActivity(), SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.mine_textview3:
			intent.setClass(getActivity(), AboutActivity.class);
			startActivity(intent);
			break;
		}
	}
}
