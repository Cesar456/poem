package com.cesar.poem.activity;

import java.util.ArrayList;
import java.util.List;

import com.cesar.poem.DAO.LastPoemDAO;
import com.cesar.poem.adapter.MyFragmentPagerAdapter;
import com.cesar.poem.bean.Poem;
import com.cesar.poem.fragment.ShowPoemContentFragment;
import com.cesar.poem.fragment.TextFragment;
import com.cesar.poem.util.CommonUtil;
import com.cesar.poem.view.IconView;
import com.example.poem.R;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ContentActivity extends FragmentActivity implements OnClickListener, OnPageChangeListener {

	private List<Fragment> fragments;
	private List<IconView> iconViews;
	private ViewPager viewPager;

	private boolean isCollect;
	private Poem poem;
	private LastPoemDAO lastPoemDAO;

	private CommonUtil commonUtil = CommonUtil.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getActionBar().setTitle("内容");
		// 获取序列化对象
		poem = (Poem) getIntent().getSerializableExtra("poem");
		initView();
		initData();
	}

	private void initView() {

		lastPoemDAO = new LastPoemDAO(this);

		viewPager = (ViewPager) findViewById(R.id.activity_main_pager);
		iconViews = new ArrayList<IconView>();
		iconViews.add((IconView) findViewById(R.id.lastet_poem_fragment_IconView));
		iconViews.add((IconView) findViewById(R.id.classfy_fragment_IconView));
		iconViews.add((IconView) findViewById(R.id.mine_fragment_IconView));
		iconViews.get(0).setIconText("内容");
		iconViews.get(0).setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_content));
		iconViews.get(0).setOnClickListener(this);

		iconViews.get(1).setIconText("解析");
		iconViews.get(1).setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_any));
		iconViews.get(1).setOnClickListener(this);

		iconViews.get(2).setIconText("赏析");
		iconViews.get(2).setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_action_appreciate));
		iconViews.get(2).setOnClickListener(this);
	}

	public void initData() {

		fragments = new ArrayList<Fragment>();
		fragments.add(new ShowPoemContentFragment(poem));
		fragments.add(new TextFragment(poem.getAnalytical()));
		fragments.add(new TextFragment(poem.getAppreciate()));

		MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
				fragments);
		viewPager.setAdapter(myFragmentPagerAdapter);
		viewPager.setOnPageChangeListener(this);
		iconViews.get(viewPager.getCurrentItem()).setIconAlpha(1.0f);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		isCollect = lastPoemDAO.isCollect(poem.getId());
		getMenuInflater().inflate(R.menu.content_activity_actionbar, menu);
		MenuItem item = menu.getItem(0);
		if (isCollect) {
			item.setIcon(R.drawable.ic_action_important_light);
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("收藏")){
			if (!isCollect) {
				isCollect=true;
				item.setIcon(R.drawable.ic_action_important_light);
				lastPoemDAO.collectPoem(poem.getId());
				Toast.makeText(getApplicationContext(), poem.getAuthor()+"的诗歌"+poem.getTitle()+"已经收藏", Toast.LENGTH_SHORT).show();
			} else {
				isCollect=false;
				item.setIcon(R.drawable.ic_action_not_important);
				lastPoemDAO.donotcollectPoem(poem.getId());
				Toast.makeText(getApplicationContext(), "诗歌"+poem.getTitle()+"已经取消收藏", Toast.LENGTH_SHORT).show();
			}
		}
		else if (item.getTitle().equals("分享")) {
			Intent intent = new Intent(this , ShowImageActivity.class);
			intent.putExtra("poem", poem);
			startActivity(intent);
		}

		return true;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		commonUtil.changeIconColor(arg0, arg1, iconViews);
	}

	@Override
	public void onPageSelected(int arg0) {

	}

	@Override
	public void onClick(View v) {
		commonUtil.onTtabClick(v, iconViews, viewPager);
	}

}
