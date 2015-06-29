package com.sj.adapter;


import com.sj.viewpageauto.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapterTwo extends BaseAdapter {
	
	private LayoutInflater mInflater ;
	private Context mContext;
	
	public ListAdapterTwo(Context context){
		this.mContext = context;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null ;
        if(convertView == null){
                convertView = mInflater.inflate(R.layout.activity_main_list_adapter, parent, false);
                holder = new ViewHolder();
                
                holder.adapter_iv = (ImageView) convertView.findViewById(R.id.adapter_iv);
                holder.adapter_tv = (TextView) convertView.findViewById(R.id.adapter_tv);
         
                convertView.setTag(holder);
        }else{
                holder = (ViewHolder) convertView.getTag();
        }
        
        holder.adapter_iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.s_weixin2x));
        holder.adapter_tv.setText("Adapter Two");
        
        return convertView;
	}
	
    class ViewHolder{
    	private ImageView adapter_iv;
    	private TextView adapter_tv;

    }

}
