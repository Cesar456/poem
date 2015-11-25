package com.cesar.poem.fragment;

import com.example.poem.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//该fragment用于显示作者详细信息，诗歌赏析以及解析等
public class TextFragment extends Fragment{
	
	private TextView textView;
	private String content;
	
	public TextFragment() {
	}
	
	public TextFragment(String content) {
		if(content==null||content.trim().equals("")){
			this.content="该首诗歌暂无解析/赏析";
		}else {
			this.content = content.trim();
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_text, container ,false);
		textView = (TextView) view.findViewById(R.id.fragment_text);
		textView.setText(content.replace(" ", "\n"));
		return view;
	}
}
