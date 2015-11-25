package com.cesar.poem.activity;

import java.util.ArrayList;
import java.util.List;

import com.cesar.poem.DAO.LastPoemDAO;
import com.cesar.poem.adapter.PoemListAdapter;
import com.cesar.poem.bean.Poem;
import com.cesar.poem.util.JsonUtil;
import com.example.poem.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends Activity implements OnItemClickListener {

	private List<Poem> poems;
	private ListView listView;
	private JsonUtil jsonUtil;
	private PoemListAdapter poemListAdapter;

	private View progressBar;

	private String flag;// 判断该activity是从哪个activity传递过来的
	private String keyword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectAll().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_list);

		initView();
		setData();
	}

	private void initView() {
		flag = getIntent().getStringExtra("classfy");
		progressBar = findViewById(R.id.include_list_footbar);
		progressBar.setVisibility(View.VISIBLE);
		listView = (ListView) findViewById(R.id.listview);
		poems = new ArrayList<Poem>();
		listView.setOnItemClickListener(this);
		if (flag.equals("SEARCH")) {
			getActionBar().setTitle("搜索结果");
		} else {
			getActionBar().setTitle("收藏");
		}
	}

	private void setData() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (flag.equals("SEARCH")) {
					keyword = getIntent().getStringExtra("keyword");
					jsonUtil = JsonUtil.getInstance();
					List<Poem> newpoem = jsonUtil.searchByTitle(keyword);
					if (newpoem != null) {
						poems.addAll(newpoem);
					}
					newpoem = jsonUtil.searchByContent(keyword);
					if (newpoem != null) {
						poems.addAll(newpoem);
					}
				} else if (flag.equals("COLLECT")) {
					poems.addAll(new LastPoemDAO(getApplicationContext()).getAllCollectionPoem());
				}
				if (poems != null && poems.size() > 0) {
					poemListAdapter = new PoemListAdapter(getApplicationContext(), poems);
					listView.setAdapter(poemListAdapter);
				} else {
					Toast.makeText(getApplicationContext(), "没有数据", Toast.LENGTH_SHORT).show();
				}
				progressBar.setVisibility(View.GONE);
			}
		}, 1000);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Poem poem = (Poem) poemListAdapter.getItem(position);
		Intent intent = new Intent(this, ContentActivity.class);
		intent.putExtra("poem", poem);
		startActivity(intent);
	}
}
