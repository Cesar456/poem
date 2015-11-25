package com.cesar.poem.activity;

import java.util.ArrayList;
import java.util.List;

import com.cesar.poem.adapter.MyFragmentPagerAdapter;
import com.cesar.poem.bean.Author;
import com.cesar.poem.fragment.ListPoemFragment;
import com.cesar.poem.fragment.TextFragment;
import com.cesar.poem.util.CommonUtil;
import com.cesar.poem.util.JsonUtil;
import com.cesar.poem.view.IconView;
import com.example.poem.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

//显示诗歌列表，对于作者来说，显示作者详细信息和诗歌列表
public class ShowListPoemActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {

	private List<Fragment> fragments;
	private List<IconView> iconViews;

	private ViewPager viewPager;
	private LinearLayout linearLayout;
	private View progressBar;

	private JsonUtil jsonUtil = JsonUtil.getInstance();

	private String classfy;
	private String info;

	private CommonUtil commonUtil = CommonUtil.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_list_poem_activity);

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites()
				.detectAll().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
				.detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

		initView();
		new Handler().postDelayed(new Runnable() {
			public void run() {
				initData();
			}
		}, 100);
	}

	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.activity_show_list_poem_pager);
		progressBar = findViewById(R.id.include_show_poem_list_footbar);
		progressBar.setVisibility(View.VISIBLE);
		linearLayout = (LinearLayout) findViewById(R.id.activity_show_list_poem_linearLayout);
	}

	private void initData() {
		classfy = getIntent().getStringExtra("classfy");
		info = getIntent().getStringExtra("info");
		fragments = new ArrayList<Fragment>();
		if ("AUTHOR".equals(classfy)) {
			getActionBar().setTitle(info);
		} else if ("STYLE".equals(classfy)) {
			getActionBar().setTitle(info);
		} else if ("RANDOM".equals(classfy)) {
			getActionBar().setTitle("随机");
		}
		if ("AUTHOR".equals(classfy)) {
			linearLayout.setVisibility(View.VISIBLE);
			iconViews = new ArrayList<IconView>();
			iconViews.add((IconView) findViewById(R.id.author_list_poem_fragment));
			iconViews.add((IconView) findViewById(R.id.author_info_fragment));
			iconViews.get(0).setIconAlpha(1.0f);
			iconViews.get(0).setOnClickListener(this);
			iconViews.get(1).setOnClickListener(this);
			fragments.add(new ListPoemFragment(ListPoemFragment.AUTHOR, info));
			fragments.add(new TextFragment(getAuthorByName(info).getLife()));
		} else if ("STYLE".equals(classfy)) {
			linearLayout.setVisibility(View.GONE);
			fragments.add(new ListPoemFragment(ListPoemFragment.STYLE, info));
		} else if ("RANDOM".equals(classfy)) {
			linearLayout = (LinearLayout) findViewById(R.id.activity_show_list_poem_linearLayout);
			linearLayout.setVisibility(View.GONE);
			fragments.add(new ListPoemFragment(ListPoemFragment.RANDOM));
		}
		MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
				fragments);
		viewPager.setAdapter(myFragmentPagerAdapter);
		progressBar.setVisibility(View.GONE);
		viewPager.setOnPageChangeListener(this);
	}

	public Author getAuthorByName(String name) {
		return jsonUtil.getAuthorByName(name);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		commonUtil.changeIconColor(position, positionOffset, iconViews);
	}

	@Override
	public void onPageSelected(int arg0) {

	}

	@Override
	public void onClick(View v) {
		commonUtil.onTtabClick(v, iconViews, viewPager);
	}
}
