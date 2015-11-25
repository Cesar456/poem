package com.cesar.poem.adapter;

import java.util.List;

import com.cesar.poem.bean.Poem;
import com.example.poem.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PoemListAdapter extends BaseAdapter {

	private List<Poem> poems;
	private LayoutInflater mInflater;

	public PoemListAdapter(Context context, List<Poem> poems) {
		this.poems = poems;
		this.mInflater = LayoutInflater.from(context);
	}
	
	public void onDateChange(List<Poem> poems) {
		this.poems = poems;
		this.notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		return poems.size();
	}
	@Override
	public Object getItem(int position) {
		return poems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Poem poem = poems.get(position);
		ViewHolder holder = null;
		if(null == convertView){
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item, null);
			holder.title = (TextView) convertView.findViewById(R.id.item_title);
			holder.content = (TextView) convertView.findViewById(R.id.item_content);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		String title = poem.getTitle().trim().replace("　", "").replaceAll("(\\(|【)[\\s\\S]*(\\)|】)", "").replaceAll("（[\\s\\S]*）", "");
		String content = poem.getContent().trim().replace("　", "").replaceAll("(\\(|【)[\\s\\S]*(\\)|】)", "").replaceAll("（[\\s\\S]*）", "");
		holder.title.setText(title);
		holder.content.setText(content);
		return convertView;
	}
	
	class ViewHolder{
		public TextView title;
		public TextView content;
	}
}
