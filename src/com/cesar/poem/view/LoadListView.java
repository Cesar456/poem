package com.cesar.poem.view;

import com.example.poem.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class LoadListView extends ListView implements OnScrollListener {

	ILoadListener iListener;

	View footer;// 底部布局
	int totleItemCount;// 当前总数
	int lastVisiableItem;// 最后一个可见item
	boolean isLoading;
	
	private int page=1;
	

	//获取当前页
	public int getPage() {
		return page;
	}

	public LoadListView(Context context) {
		super(context);
		initView(context);
	}

	public LoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public LoadListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
		this.addFooterView(footer);
	}

	@SuppressLint("InflateParams")
	private void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		footer = inflater.inflate(R.layout.footer_layout, null);
		footer.findViewById(R.id.footer_load).setVisibility(View.GONE);
		this.addFooterView(footer);
		this.setOnScrollListener(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (totleItemCount == lastVisiableItem && scrollState == SCROLL_STATE_IDLE) {
			if (!isLoading) {
				isLoading = true;
				footer.findViewById(R.id.footer_load).setVisibility(View.VISIBLE);
				iListener.onLoad();
				page++;
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		this.lastVisiableItem = firstVisibleItem + visibleItemCount;
		this.totleItemCount = totalItemCount;
	}

	public void setILoadListener(ILoadListener iListener) {
		this.iListener = iListener;
	}

	public interface ILoadListener {
		public void onLoad();
	}

	public void loadComplete() {
		isLoading = false;
		footer.findViewById(R.id.footer_load).setVisibility(View.GONE);
	}
}
