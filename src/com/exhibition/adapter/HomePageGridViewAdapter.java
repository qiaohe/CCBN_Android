package com.exhibition.adapter;

import java.util.HashMap;
import java.util.List;
import com.exhibition.R; 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class HomePageGridViewAdapter extends BaseAdapter {

	private List<HashMap<String, String>> data;
	private LayoutInflater mInflater;

	public HomePageGridViewAdapter(Context context, List<HashMap<String, String>> data) {
		this.data = data;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = this.mInflater.inflate(R.layout.home_page_gridview_item, null);
            holder.mGridViewImg = (ImageView) convertView.findViewById(R.id.gridview_img);
            holder.mGridViewText = (TextView) convertView.findViewById(R.id.gridview_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
 
    public class ViewHolder {
        ImageView mGridViewImg;
        TextView mGridViewText;
    }
}
