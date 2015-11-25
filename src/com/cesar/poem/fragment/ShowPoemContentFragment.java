package com.cesar.poem.fragment;

import com.cesar.poem.DAO.LastPoemDAO;
import com.cesar.poem.activity.MainActivity;
import com.cesar.poem.activity.ShowListPoemActivity;
import com.cesar.poem.bean.Poem;
import com.example.poem.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

//显示诗歌内容的页面
public class ShowPoemContentFragment extends Fragment implements OnClickListener {

	private Poem poem;

	private TextView title;
	private TextView author;
	private TextView content;

	private LastPoemDAO lastPoemDAO;
	
	public ShowPoemContentFragment() {
	}

	public ShowPoemContentFragment(Poem poem) {
		this.poem = poem;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_show_poem, container, false);
		lastPoemDAO = new LastPoemDAO(getActivity());
		title = (TextView) view.findViewById(R.id.contentActivity_title);
		author = (TextView) view.findViewById(R.id.contentActivity_author);
		content = (TextView) view.findViewById(R.id.contentActivity_content);
		initView();
		return view;
	}

	private void initView() {
		title.setTextSize(getTitleSize());
		author.setTextSize(getAuthorSize());
		content.setTextSize(getContentSize());

		title.setText(poem.getTitle());
		author.setText(poem.getAuthor());
		content.setText(poem.getContent().replaceFirst(" ", "").replace(" ", "  \n"));
		author.setOnClickListener(this);
		// 存储
		lastPoemDAO.saveOrUpdate(poem);
		// 提醒更新
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				MainActivity.getLatestFragment().dataChanged(poem);
			}
		}, 100);
	}

	@Override
	public void onClick(View v) {
		if (v.equals(author)) {
			Intent intent = new Intent(getActivity(), ShowListPoemActivity.class);
			intent.putExtra("classfy", "AUTHOR");
			intent.putExtra("info", poem.getAuthor().trim());
			startActivity(intent);
		}
	}

	private SharedPreferences getMySharedPreferences() {
		return getActivity().getSharedPreferences("poemSetting", Activity.MODE_PRIVATE);
	}

	public int getTitleSize() {
		return getMySharedPreferences().getInt("TITLE", 15);
	}

	public int getAuthorSize() {
		return getMySharedPreferences().getInt("AUTHOR", 15);
	}

	public int getContentSize() {
		return getMySharedPreferences().getInt("CONTENT", 15);
	}
}
