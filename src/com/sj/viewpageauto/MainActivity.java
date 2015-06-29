package com.sj.viewpageauto;


import java.util.ArrayList;

import com.sj.adapter.ListAdapterOne;
import com.sj.adapter.ListAdapterTwo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private ViewPager mViewPager;
	private RankPagerAdapter rankPagerAdapter = null;
	
	private View mViewOne;
	private View mViewTwo;
	
	private ListView mListViewOne;
	private ListView mListViewTwo;
	
	private HeadViewPage mHeadViewPageOne;
	private HeadViewPage mHeadViewPageTwo;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initlayout();
	}

	public void initlayout(){
		mViewPager = (ViewPager) this.findViewById(R.id.viewpager);
		
		mViewOne = LayoutInflater.from(this).inflate(
				R.layout.activity_main_list, null);
		mViewTwo = LayoutInflater.from(this).inflate(
				R.layout.activity_main_list, null);
		
		mListViewOne = (ListView)mViewOne.findViewById(R.id.main_list);
		mListViewTwo = (ListView)mViewTwo.findViewById(R.id.main_list);
		
		
		
		mHeadViewPageOne = new HeadViewPage(this);
		ArrayList<Integer> listOne = new ArrayList<Integer>();
		listOne.add(R.drawable.advertisement_0);
		listOne.add(R.drawable.advertisement_1);
		listOne.add(R.drawable.advertisement_2);
		listOne.add(R.drawable.advertisement_3);
		mHeadViewPageOne.setData(listOne);
		mListViewOne.addHeaderView(mHeadViewPageOne);
		mListViewOne.setAdapter(new ListAdapterOne(this));
		
		mHeadViewPageTwo = new HeadViewPage(this);
		ArrayList<Integer> listTwo = new ArrayList<Integer>();
		listTwo.add(R.drawable.advertisement_4);
		listTwo.add(R.drawable.advertisement_5);
		listTwo.add(R.drawable.advertisement_6);
		listTwo.add(R.drawable.advertisement_7);
		mHeadViewPageTwo.setData(listTwo);
		mListViewTwo.addHeaderView(mHeadViewPageTwo);
		mListViewTwo.setAdapter(new ListAdapterTwo(this));
		
		
		rankPagerAdapter = new RankPagerAdapter();	
		mViewPager.setOffscreenPageLimit(2);
		mViewPager.setOnPageChangeListener(changeListener);
		rankPagerAdapter = new RankPagerAdapter();
		mViewPager.setAdapter(rankPagerAdapter);
		changeListener.onPageSelected(0);
		
	}
	
	
	
	
	
	//滑动的监听事件 改变文字颜色添加动画
		OnPageChangeListener changeListener = new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
					case 0:
						Toast.makeText(MainActivity.this, "0", Toast.LENGTH_SHORT).show();
						break;
					case 1:
						Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();
						break;
					}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		};
		
		
		public class RankPagerAdapter extends PagerAdapter {

			@Override
			public int getCount() {
				return 2;
			}

			@Override
			public Object instantiateItem(View collection, int position) {
				View mView = null;
				if(position == 0){
					mView = mViewOne;
				}else if(position == 1){
					mView = mViewTwo;
				}
				((ViewPager) collection).addView(mView, position);
				return mView;
			}

			@Override
			public void destroyItem(View collection, int position, Object view) {
				((ViewPager) collection).removeView((View) view);
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == (object);
			}

			@Override
			public void finishUpdate(View arg0) {
			}

			@Override
			public void restoreState(Parcelable arg0, ClassLoader arg1) {
			}

			@Override
			public Parcelable saveState() {
				return null;
			}

			@Override
			public void startUpdate(View arg0) {
			}

		}

}
