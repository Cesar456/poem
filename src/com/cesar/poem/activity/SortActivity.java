package com.cesar.poem.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cesar.poem.DAO.AllDAO;
import com.cesar.poem.adapter.SortAdapter;
import com.cesar.poem.util.CharacterParser;
import com.cesar.poem.util.SortModel;
import com.cesar.poem.view.ClearEditText;
import com.cesar.poem.view.SideBar;
import com.cesar.poem.view.SideBar.OnTouchingLetterChangedListener;
import com.example.poem.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class SortActivity extends Activity {
	
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;

	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	private AllDAO allDAO;
	private String classfy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sort);
		getActionBar().setTitle("分类");
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				initViews();
			}
		}, 50);
	}

	private void initViews() {

		allDAO = new AllDAO(getApplicationContext());
		classfy = getIntent().getStringExtra("CLASSFY");

		characterParser = CharacterParser.getInstance();
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			@Override
			public void onTouchingLetterChanged(String s) {
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//获取当前点击的文字
				String string = ((SortModel) adapter.getItem(position)).getName();
				if ("author".equals(classfy)) {
					Intent intent = new Intent(getApplicationContext(),ShowListPoemActivity.class);
					intent.putExtra("classfy", "AUTHOR");
					intent.putExtra("info", string.trim());
					startActivity(intent);
				} else if ("style".equals(classfy)) {
					Intent intent = new Intent(getApplicationContext(),ShowListPoemActivity.class);
					intent.putExtra("classfy", "STYLE");
					intent.putExtra("info", string.trim());
					startActivity(intent);
				} else if("time".equals(classfy)){//按朝代分类
					Intent intent = new Intent(getApplicationContext(),SortActivity.class);
					intent.putExtra("CLASSFY", string.trim());
					startActivity(intent);
				} else {  
					Intent intent = new Intent(getApplicationContext(),ShowListPoemActivity.class);
					intent.putExtra("classfy", "AUTHOR");
					intent.putExtra("info", string.trim());
					startActivity(intent);
				}
			}
		});

		if ("author".equals(classfy)) {
			SourceDateList = filledData(allDAO.getAuthorName());
		} else if ("style".equals(classfy)) {
			SourceDateList = filledData(allDAO.getAllStyle());
		} else if("time".equals(classfy)){//按朝代分类
			SourceDateList = filledData(allDAO.getAllTime());
		} else {  //否则就传过来朝代名，通过朝代名获得所有作者
			SourceDateList = filledData(allDAO.getAuthorNameBytime(classfy));
		}

		Collections.sort(SourceDateList, new PinYinComparator());
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);

		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				filterData(s.toString());
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {}
		});
	}

	/**
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(List<String> date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.size(); i++) {
			String s = date.get(i);
			if(s.trim().equals("")){
				continue;
			}
			SortModel sortModel = new SortModel();
			sortModel.setName(date.get(i));
			String pinyin = characterParser.getSelling(s);
			String sortString = pinyin.substring(0, 1).toUpperCase();
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}
			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		Collections.sort(filterDateList, new PinYinComparator());
		adapter.updateListView(filterDateList);
	}
}

class PinYinComparator implements Comparator<SortModel> {
	@Override
	public int compare(SortModel o1, SortModel o2) {
		if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}
}
