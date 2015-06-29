package com.sj.viewpageauto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;





import com.sj.widget.ChildViewPager;
import com.sj.widget.ChildViewPager.OnSingleTouchListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;



public class HeadViewPage extends LinearLayout {
    
    //轮播图图片数量
    //private final static int IMAGE_COUNT = 5;
    //自动轮播的时间间隔
    //private final static int TIME_INTERVAL = 5;
    //自动轮播启用开关
    private final static boolean isAutoPlay = true; 
    
    //自定义轮播图的资源ID
    private ArrayList<Integer> dataListResponse;
    //放轮播图片的ImageView 的list
    private List<View> imageViewsList;
    //放圆点的View的list
    private List<View> dotViewsList;
    
    private ChildViewPager viewPager;
    //当前轮播页
    private int currentItem  = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;
    
    private LayoutInflater mInflater;
    
    private LinearLayout m_ll_img_point;
    private static Context mContext;
    
    //Handler
    @SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){

		@Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }
        
    };
    
    public HeadViewPage(Context context) {
        super(context);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflate(context, R.layout.view_home_head, this);  
        
        mContext = context;
        
    }
    
    public void setData(ArrayList<Integer> dataList){
    	dataListResponse = dataList;
    	
        initData();
        initUI(mContext);
        if(isAutoPlay){
            startPlay();
        }
    }

    /**
     * 开始轮播图切换
     */
    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
    }
    /**
     * 停止轮播图切换
     */
    @SuppressWarnings("unused")
	private void stopPlay(){
        scheduledExecutorService.shutdown();
    }
    /**
     * 初始化相关Data
     */
    private void initData(){
        imageViewsList = new ArrayList<View>();
        dotViewsList = new ArrayList<View>();
        
    }
    /**
     * 初始化Views等UI
     */
    private void initUI(Context context){
    	m_ll_img_point = (LinearLayout) findViewById(R.id.ll_img_point);
    	
    	/*图片 描述文字 展示*/
    	for (int i = 0; i < dataListResponse.size(); i++) {
    		View view =  mInflater.inflate(R.layout.view_home_head_adapter, null);
    		ImageView imageView = (ImageView) view.findViewById(R.id.view_home_head_adapter_img);
            
        	imageView.setImageResource(dataListResponse.get(i));
            imageViewsList.add(view);
		}
        
    	/*动态添加轮播小图标*/
    	LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);  
        lp.setMargins(5, 0, 0, 0);
    	if(dataListResponse.size() > 1){
    		
    		for (int i = 0; i < dataListResponse.size(); i++) {
    			ImageView imageView=new ImageView(getContext()); 
    			if(i == 0){    
       			 imageView.setBackgroundResource(R.drawable.dot_black);    
                }else{    
               	 imageView.setBackgroundResource(R.drawable.dot_white);    
                }
    			imageView.setLayoutParams(lp);
    			dotViewsList.add(imageView); 
    			m_ll_img_point.addView(imageView);
    		}
    		 
    		m_ll_img_point.setVisibility(View.VISIBLE);
    	}else{
    		m_ll_img_point.setVisibility(View.GONE);
    	}

        viewPager = (ChildViewPager) findViewById(R.id.view_home_head_viewpage);
        viewPager.setFocusable(true);
        if(!(dataListResponse.size() > 1)){
        	viewPager.setFlag(false);
        }
        
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        
        viewPager.setOnSingleTouchListener(new OnSingleTouchListener() {
			
			@Override
			public void onSingleTouch() {
				// TODO Auto-generated method stub
				Toast.makeText(HeadViewPage.mContext, "position:"+currentItem, Toast.LENGTH_SHORT).show();
			}
		});
    }
    
    /**
     * 填充ViewPager的页面适配器
     */
    private class MyPagerAdapter  extends PagerAdapter{

        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager)container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            // TODO Auto-generated method stub
            ((ViewPager)container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub
            
        }
        
    }
    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     */
    private class MyPageChangeListener implements OnPageChangeListener{

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
            case 1:// 手势滑动，空闲中
                isAutoPlay = false;
                break;
            case 2:// 界面切换中
                isAutoPlay = true;
                break;
            case 0:// 滑动结束，即切换完毕或者加载完毕
                // 当前为最后一张，此时从右向左滑，则切换到第一张
                if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                    viewPager.setCurrentItem(0);
                }
                // 当前为第一张，此时从左向右滑，则切换到最后一张
                else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                    viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                }
                break;
        }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub
        	currentItem = pos;
        	for(int i=0;i < dotViewsList.size();i++){
        		if(i == pos){
        			dotViewsList.get(pos).setBackgroundResource(R.drawable.dot_black);
        		}else{
        			dotViewsList.get(i).setBackgroundResource(R.drawable.dot_white);
        		}
        	}
        	
        }
        
    }
    
    /**
     *执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewPager) {
                currentItem = (currentItem+1)%imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
        
    }
    /**
     * 销毁ImageView资源，回收内存
     
    private void destoryBitmaps() {

        for (int i = 0; i < IMAGE_COUNT; i++) {
            ImageView imageView = (ImageView) imageViewsList.get(i).findViewById(R.id.view_home_head_adapter_bc);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                //解除drawable对view的引用
                drawable.setCallback(null);
            }
        }
    }*/
}

