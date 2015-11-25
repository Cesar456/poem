package com.cesar.poem.fragment;

import java.util.ArrayList;
import java.util.List;

import com.cesar.poem.DAO.LastPoemDAO;
import com.cesar.poem.activity.ContentActivity;
import com.cesar.poem.adapter.PoemListAdapter;
import com.cesar.poem.bean.Poem;
import com.example.poem.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LatestFragment extends Fragment implements OnItemClickListener {

	private List<Poem> poems = new ArrayList<Poem>();
	private ListView listView;
	private LastPoemDAO lastPoemDAO;
	private PoemListAdapter poemListAdapter;
	
	public LatestFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_list, container, false);
		listView = (ListView) view.findViewById(R.id.listview);
		
		initData();
		return view;
	}

	private void initData() {
		lastPoemDAO = new LastPoemDAO(getActivity());
		poems = lastPoemDAO.getAll();
		poemListAdapter = new PoemListAdapter(getActivity(), poems);
		listView.setAdapter(poemListAdapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Poem poem = (Poem) poemListAdapter.getItem(position);
		Intent intent = new Intent(getActivity(), ContentActivity.class);
		intent.putExtra("poem", poem);
		startActivity(intent);
	}
	
	/**
	 * 浏览时数据改变，调用此方法实时更新
	 */
	public void dataChanged(Poem poem){
		int location = isexit(poem);
		if(location != -1){
			poems.remove(location);
		}
		poems.add(0, poem);
		poemListAdapter.notifyDataSetChanged();
	}
	private int isexit(Poem poem){
		int location = 0;
		for(Poem poem2:poems){
			if(poem2.getContent().equals(poem.getContent())){
				return location;
			}
			location++;
		}
		//-1表示列表中无此诗
		return -1;
	}
}
