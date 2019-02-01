package com.eebbk.bfc.db.demo.basic.ui;

import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.eebbk.bfc.db.demo.R;
import com.eebbk.bfc.db.demo.db.DbManager;
import com.eebbk.bfc.db.demo.db.entity.UserInfo;
import com.eebbk.bfc.db.demo.db.entity.UserInfoDao;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

/**
 * Desc:
 * Author: llp
 * Create Time: 2016-12-15 11:00
 * Email: jacklulu29@gmail.com
 */

public class BatchEditUIPanel implements View.OnClickListener {

    private Context mContext;
    private View mRootView;
    private Spinner mQueryByIdSpinner;
    private EditText mQueryByIdEtv;
    private Spinner mQueryByNameSpinner;
    private EditText mQueryByNameEtv;
    private Button mQueryPanelBtn;

    private OnEditPanelListener mListener;

    public BatchEditUIPanel(Context context, View rootView, OnEditPanelListener listener){
        mContext = context;
        mRootView = rootView;
        mListener = listener;
        mQueryByIdSpinner = findView(rootView, R.id.search_by_id_spinner);
        String[] compareArrayStr = context.getResources().getStringArray(R.array.find_types_array);
        ArrayAdapter<String> queryByIdAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                compareArrayStr);
        mQueryByIdSpinner.setAdapter(queryByIdAdapter);
        mQueryByIdEtv = findView(rootView, R.id.search_by_id_etv);
        mQueryByNameSpinner = findView(rootView, R.id.search_by_name_spinner);
        ArrayAdapter<String> queryByNameAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                compareArrayStr);
        mQueryByNameSpinner.setAdapter(queryByNameAdapter);
        mQueryByNameEtv = findView(rootView, R.id.search_by_name_etv);
        mQueryPanelBtn = findView(rootView, R.id.query_panel_btn);
        mQueryPanelBtn.setOnClickListener(this);
    }

    public boolean isShow(){
        return mRootView != null && mRootView.getVisibility() == View.VISIBLE;
    }

    public void hidePanel(){
        if(mRootView != null){
            mRootView.setVisibility(View.GONE);
        }
        final InputMethodManager inputMethodManager = (InputMethodManager) mContext.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mRootView.getWindowToken(), 0);
    }

    public void showPanel(){
        if(mRootView != null){
            mRootView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        String id = mQueryByIdEtv.getText().toString();
        String name = mQueryByNameEtv.getText().toString();

        UserInfoDao userInfoDao = DbManager.getInstance().getDaoSession().getUserInfoDao();
        QueryBuilder qb = userInfoDao.queryBuilder();

        List<WhereCondition> conditions = new ArrayList<>();
        if(!TextUtils.isEmpty(id)){
            if(mQueryByIdSpinner.getSelectedItemPosition()==0){
                conditions.add(UserInfoDao.Properties.UserId.eq(id));
            } else {
                conditions.add(UserInfoDao.Properties.UserId.like("%"+id+"%"));
            }
        }
        if(!TextUtils.isEmpty(name)){
            if(mQueryByNameSpinner.getSelectedItemPosition()==0){
                conditions.add(UserInfoDao.Properties.UserName.eq(name));
            } else {
                conditions.add(UserInfoDao.Properties.UserName.like("%"+name+"%"));
            }
        }
        if(mListener != null){
            mListener.onBatchBtnClick(conditions);
        }
    }

    public interface OnEditPanelListener {
        void onBatchBtnClick(List<WhereCondition> conditions);
    }

    public <T extends View> T findView(View view, @IdRes int resId){
        return (T)view.findViewById(resId);
    }

}
