package com.sj.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ChildViewPager extends ViewPager{
    /** ����ʱ���µĵ� **/
    PointF downP = new PointF();
    /** ����ʱ��ǰ�ĵ� **/
    PointF curP = new PointF(); 
    OnSingleTouchListener onSingleTouchListener;
    
    /** �Ƿ����� **/
    boolean flag = true;

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ChildViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        //�����ش����¼������λ�õ�ʱ�򣬷���true��
        //˵����onTouch�����ڴ˿ؼ�������ִ�д˿ؼ���onTouchEvent
        return true;
    }
    
    public void setFlag(boolean mflag){
    	flag = mflag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // TODO Auto-generated method stub
        //ÿ�ν���onTouch�¼�����¼��ǰ�İ��µ�����
        curP.x = arg0.getX();
        curP.y = arg0.getY();

        if(arg0.getAction() == MotionEvent.ACTION_DOWN){
            //��¼����ʱ�������
            //�мǲ����� downP = curP �������ڸı�curP��ʱ��downPҲ��ı�
            downP.x = arg0.getX();
            downP.y = arg0.getY();
            //�˾������Ϊ��֪ͨ���ĸ�ViewPager���ڽ��е��Ǳ��ؼ��Ĳ�������Ҫ���ҵĲ������и���
            getParent().requestDisallowInterceptTouchEvent(flag);
        }

        if(arg0.getAction() == MotionEvent.ACTION_MOVE){
            //�˾������Ϊ��֪ͨ���ĸ�ViewPager���ڽ��е��Ǳ��ؼ��Ĳ�������Ҫ���ҵĲ������и���
            getParent().requestDisallowInterceptTouchEvent(flag);
        }

        if(arg0.getAction() == MotionEvent.ACTION_UP){
            //��upʱ�ж��Ƿ��º����ֵ�����Ϊһ����
            //�����һ���㣬��ִ�е���¼����������Լ�д�ĵ���¼���������onclick
        	//System.out.println(downP.x+" "+curP.x+" "+downP.y+" "+curP.y);
            if(Math.abs(downP.x-curP.x) < 5 && Math.abs(downP.y-curP.y) < 5){
                onSingleTouch();
                return true;
            }
        }

        return super.onTouchEvent(arg0);
    }

    /**
     * ����
     */
    public void onSingleTouch() {
        if (onSingleTouchListener!= null) {

            onSingleTouchListener.onSingleTouch();
        }
    }

    /**
     * ��������¼��ӿ�
     *
     */
    public interface OnSingleTouchListener {
        public void onSingleTouch();
    }

    public void setOnSingleTouchListener(OnSingleTouchListener onSingleTouchListener) {
        this.onSingleTouchListener = onSingleTouchListener;
    }

}