package com.cesar.poem.fragment;

import java.util.ArrayList;
import java.util.List;

import com.cesar.poem.activity.ContentActivity;
import com.cesar.poem.adapter.PoemListAdapter;
import com.cesar.poem.bean.Poem;
import com.cesar.poem.util.JsonUtil;
import com.cesar.poem.view.LoadListView;
import com.cesar.poem.view.LoadListView.ILoadListener;
import com.example.poem.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class ListPoemFragment extends Fragment {

	private JsonUtil jsonUtil = JsonUtil.getInstance();
	private List<Poem> poems = new ArrayList<Poem>();
	private LoadListView listView;
	private PoemListAdapter poemListAdapter = null;
	private String classfy; // 分类，表示是从哪里取得数据，包括随机，按诗人姓名，按种类等
	private String info;// 附加信息，包括诗人姓名，朝代等

	// 分类
	public static String RANDOM = "RANDOM";// 随机
	public static String AUTHOR = "AUTHOR";// 作者姓名
	public static String STYLE = "STYLE";// 风格
	
	public ListPoemFragment() {
	}

	public ListPoemFragment(String classfy) {
		this.classfy = classfy;
	}

	public ListPoemFragment(String classfy, String info) {
		this.classfy = classfy;
		this.info = info;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectAll().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_loadlist, container, false);
		listView = (LoadListView) view.findViewById(R.id.load_listview);
		
		initData();
		
		listView.setOnItemClickListener(new poemItemClickLlistener());
		return view;
	}

	private void initData() {
		List<Poem> newpoems = getData();
		if (newpoems == null) {
			Toast.makeText(getActivity(), "没有数据", Toast.LENGTH_LONG).show();
		} else {
			poems.addAll(newpoems);
			showList(poems);
		}
	}

	public void showList(List<Poem> poems) {
		// 第一次加载
		if (poemListAdapter == null) {
			poemListAdapter = new PoemListAdapter(getActivity(), poems);
			listView.setILoadListener(new LoadListener());
			listView.setAdapter(poemListAdapter);
		} else {
			poemListAdapter.onDateChange(poems);
		}

	}

	public class poemItemClickLlistener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Poem poem = (Poem) poemListAdapter.getItem(position);
			Intent intent = new Intent(getActivity(), ContentActivity.class);
			intent.putExtra("poem", poem);
			startActivity(intent);
		}
	}

	public class LoadListener implements ILoadListener {
		@Override
		public void onLoad() {
			Handler handler = new Handler();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					List<Poem> newpoem = getData();
					if (newpoem == null) {
						Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_LONG).show();
						listView.loadComplete();
					} else {
						poems.addAll(getData());
						showList(poems);
						listView.loadComplete();
					}
				}
			}, 1000);
		}
	}

	public List<Poem> getData() {
		if (RANDOM.equals(classfy)) {
			return jsonUtil.getRandomPoem();
		} else if (AUTHOR.equals(classfy)) {
			List<Poem> poems = jsonUtil.getPoemByAuthorName(info, listView.getPage());
			if (poems.size() >= 1) {
				return poems;
			}
			return null;
		} else if (STYLE.equals(classfy)) {
			List<Poem> poems = jsonUtil.getPoemByTag(info, listView.getPage());
			if(poems.size()>=1){
				return poems;
			}
			return null;
		}
		// TODO还没有做完，分类没有全部完成
		return jsonUtil.getRandomPoem();
	}
}
