package com.cesar.poem.activity;

import java.util.ArrayList;
import java.util.List;

import com.cesar.poem.adapter.MyFragmentPagerAdapter;
import com.cesar.poem.fragment.ClassfyFragment;
import com.cesar.poem.fragment.LatestFragment;
import com.cesar.poem.fragment.ListPoemFragment;
import com.cesar.poem.fragment.MineFragment;
import com.cesar.poem.util.CommonUtil;
import com.cesar.poem.view.IconView;
import com.example.poem.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {

	private List<Fragment> fragments;
	private List<IconView> iconViews;
	
	private boolean isExit = false;//用于重写退出当前程序的代码，即需要两次点击才能退出程序

	private ViewPager viewPager;
	private SearchView searchView;

	private static LatestFragment latestFragment;
	private ClassfyFragment classfyFragment;
	private MineFragment mineFragment;

	private CommonUtil commonUtil = CommonUtil.getInstance();

	public static LatestFragment getLatestFragment() {
		return latestFragment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setTitle("首页");
		initView();
		initData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem search = menu.findItem(R.id.action_search);
		searchView = (SearchView) search.getActionView();
		searchView.setQueryHint(getResources().getString(R.string.searchHint));
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
				intent.putExtra("classfy", "SEARCH");
				intent.putExtra("keyword", query);
				startActivity(intent);
				return true;
			}
			@Override
			public boolean onQueryTextChange(String newText) {
				return true;
			}
		});
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals(getResources().getString(R.string.random))){
			Intent intent = new Intent(getApplicationContext(),ShowListPoemActivity.class);
			intent.putExtra("classfy", ListPoemFragment.RANDOM);
			startActivity(intent);
		}
		
		return super.onOptionsItemSelected(item);
	}

	private void initView() {

		iconViews = new ArrayList<IconView>();
		iconViews.add((IconView) findViewById(R.id.lastet_poem_fragment_IconView));
		iconViews.add((IconView) findViewById(R.id.classfy_fragment_IconView));
		iconViews.add((IconView) findViewById(R.id.mine_fragment_IconView));
		iconViews.get(0).setOnClickListener(this);
		iconViews.get(1).setOnClickListener(this);
		iconViews.get(2).setOnClickListener(this);
		// 将所有颜色清空
		CommonUtil.getInstance().resetAllAlpha(iconViews);
		latestFragment = new LatestFragment();
		classfyFragment = new ClassfyFragment();
		mineFragment = new MineFragment();
	}

	private void initData() {
		fragments = new ArrayList<Fragment>();
		fragments.add(latestFragment);
		fragments.add(classfyFragment);
		fragments.add(mineFragment);
		viewPager = (ViewPager) findViewById(R.id.activity_main_pager);
		MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
				fragments);
		viewPager.setAdapter(myFragmentPagerAdapter);
		viewPager.setOnPageChangeListener(this);
		iconViews.get(viewPager.getCurrentItem()).setIconAlpha(1.0f);
	}

	@Override
	public void onClick(View v) {
		commonUtil.onTtabClick(v, iconViews, viewPager);
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			//第1次点击
			if(!isExit){
				isExit=true;
				Toast.makeText(getApplicationContext(), "再次点击退出当前程序", Toast.LENGTH_SHORT).show();
				return true;
			} else {
				isExit=false;
				return super.onKeyDown(keyCode, event);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
