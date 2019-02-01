package com.eebbk.bfc.db.demo.basic.ui;

import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.eebbk.bfc.db.demo.R;
import com.eebbk.bfc.db.demo.db.entity.UserInfo;
import com.eebbk.bfc.db.demo.db.entity.UserInfoDao;

import de.greenrobot.dao.query.LazyList;

/**
 * Desc:
 * Author: llp
 * Create Time: 2016-12-11 22:23
 * Email: jacklulu29@gmail.com
 */

public class MyAdapter extends BaseAdapter {

    private Context mContext = null;

    private LazyList<UserInfo> mData;
    private OnItemClickListener mListener;

    public MyAdapter(Context mContext, OnItemClickListener listener) {
        this.mContext = mContext;
        mListener = listener;
    }

    public void bindData(LazyList<UserInfo> data){
        mData = data;
    }

    @Override
    public int getCount() {
        if(mData != null){
            return mData.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mData != null && position < mData.size()){
            return mData.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView != null){
            viewHolder = (ViewHolder)convertView.getTag();
        }else {
            viewHolder = onCreateViewHolder(parent, position);
        }
        onBindViewHolder(viewHolder, position);
        viewHolder.rootView.setTag(viewHolder);
        return viewHolder.rootView;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        final View view = layoutInflater.inflate(R.layout.layout_basic_operation_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return viewHolder;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final UserInfo info = (UserInfo) getItem(position);
        if(info == null){
            return;
        }
        if(holder != null){
            holder.textView.setText(Html.fromHtml(getUserInfoStr(info)));
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.onItemDelete(info);
                    }
                }
            });

            holder.updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        mListener.onItemUpdate(info);
                    }
                }
            });
        }
    }

    private String getUserInfoStr(UserInfo info){
        if(info == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<br> UserId: " + info.getUserId());
        sb.append("<br> UserName: " + info.getUserName());
        sb.append("<br> UserPwd: " + info.getUserPwd());
        return sb.toString();
    }

    private static class ViewHolder {
        View rootView;
        TextView textView;
        Button deleteBtn;
        Button updateBtn;

        public ViewHolder(View view){
            rootView = view;
            textView = (TextView) view.findViewById(R.id.info_tv);
            deleteBtn = (Button) view.findViewById(R.id.delete_btn);
            updateBtn = (Button) view.findViewById(R.id.update_btn);
        }

    }

    public interface OnItemClickListener {
        void onItemDelete(UserInfo info);
        void onItemUpdate(UserInfo info);
    }
}
