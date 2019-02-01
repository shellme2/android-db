package com.eebbk.bfc.db.demo.basic.ui;

import android.content.Context;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.eebbk.bfc.db.demo.R;
import com.eebbk.bfc.db.demo.db.entity.UserInfo;

/**
 * Desc: 编辑用户信息界面
 * Author: llp
 * Create Time: 2016-12-13 21:39
 * Email: jacklulu29@gmail.com
 */

public class UserEditUIPanel implements View.OnClickListener {

    private Context mContext;
    private View mRootView;
    private EditText mUserIdEtv;
    private EditText mUserNameEtv;
    private EditText mUserPswEtv;

    private Button mInsertCancelBtn;
    private Button mInsertOkBtn;

    private OnEditPanelListener mListener;

    private UserInfo mOldUserInfo;

    public UserEditUIPanel(Context context, View rootView, OnEditPanelListener listener){
        mContext = context;
        mRootView = rootView;
        mListener = listener;

        mUserIdEtv = findView(rootView, R.id.user_id_etv);
        mUserNameEtv = findView(rootView, R.id.user_name_etv);
        mUserPswEtv = findView(rootView, R.id.user_psw_etv);

        mInsertCancelBtn = findView(rootView, R.id.insert_cancel_btn);
        mInsertCancelBtn.setOnClickListener(this);
        mInsertOkBtn = findView(rootView, R.id.insert_ok_btn);
        mInsertOkBtn.setOnClickListener(this);
    }

    public UserInfo getUserInfo(){
        String id = mUserIdEtv.getText().toString();
        String name = mUserNameEtv.getText().toString();
        String psw = mUserPswEtv.getText().toString();

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(id);
        userInfo.setUserName(name);
        userInfo.setUserPwd(psw);
        return userInfo;
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

    public void showPanel(UserInfo oldUserInfo){
        if(mRootView != null){
            mRootView.setVisibility(View.VISIBLE);
            mOldUserInfo = oldUserInfo;
            if(mOldUserInfo != null){
                mUserIdEtv.setText(mOldUserInfo.getUserId());
                mUserIdEtv.setEnabled(false);
                mUserNameEtv.setText(mOldUserInfo.getUserName());
                mUserPswEtv.setText(mOldUserInfo.getUserPwd());
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insert_cancel_btn:
                if(mListener != null){
                    mListener.onUserPanelCancelBtnClick();
                    mOldUserInfo = null;
                }
                break;
            case R.id.insert_ok_btn:
                if(mListener != null){
                    mListener.onUserPanelOkBtnClick(mOldUserInfo, getUserInfo());
                    mOldUserInfo = null;
                }
                break;
            default:
                break;
        }
    }

    public interface OnEditPanelListener {
        void onUserPanelOkBtnClick(UserInfo oldUserInfo, UserInfo newUserInfo);
        void onUserPanelCancelBtnClick();
    }

    public <T extends View> T findView(View view, @IdRes int resId){
        return (T)view.findViewById(resId);
    }

}
