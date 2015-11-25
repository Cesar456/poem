package com.cesar.poem.fragment;

import com.cesar.poem.activity.SortActivity;
import com.example.poem.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class ClassfyFragment extends Fragment implements OnClickListener {

	private TextView classy_textview1;
	private TextView classy_textview2;
	private TextView classy_textview3;
	
	public ClassfyFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_classfy, container, false);
		initView(view);
		initData();
		return view;
	}

	private void initView(View view) {
		classy_textview1 = (TextView) view.findViewById(R.id.classy_textview1);
		classy_textview2 = (TextView) view.findViewById(R.id.classy_textview2);
		classy_textview3 = (TextView) view.findViewById(R.id.classy_textview3);
	}

	private void initData() {
		classy_textview1.setOnClickListener(this);
		classy_textview2.setOnClickListener(this);
		classy_textview3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.equals(classy_textview1)) {
			Intent intent = new Intent(getActivity(), SortActivity.class);
			intent.putExtra("CLASSFY", "author");
			startActivity(intent);
		} else if (v.equals(classy_textview2)) {
			Intent intent = new Intent(getActivity(), SortActivity.class);
			intent.putExtra("CLASSFY", "style");
			startActivity(intent);
		} else if (v.equals(classy_textview3)) {
			Intent intent = new Intent(getActivity(), SortActivity.class);
			intent.putExtra("CLASSFY", "time");
			startActivity(intent);
		}
	}
}
