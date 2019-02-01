package com.eebbk.bfc.db.demo.limit.ui;

import android.app.LoaderManager;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eebbk.bfc.db.demo.R;
import com.eebbk.bfc.db.demo.baseui.BaseActivity;

public class LimitTestActivity extends BaseActivity implements View.OnClickListener {

    private Button mInsertBtn;
    private Button mUpdateBtn;
    private Button mDeleteBtn;
    private Button mQueryBtn;

    private TextView mMsgTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_limit_test);
        setContentView(R.layout.activity_other_test);
        setTitle("极限测试");
       /* mInsertBtn = findView(R.id.recycle_insert_btn);
        mUpdateBtn = findView(R.id.recycle_update_btn);
        mDeleteBtn = findView(R.id.recycle_query_btn);
        mQueryBtn = findView(R.id.recycle_delete_btn);
        mMsgTv = findView(R.id.operation_msg_tv);

        mInsertBtn.setOnClickListener(this);
        mUpdateBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
        mQueryBtn.setOnClickListener(this);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.recycle_insert_btn:
                insertBtnClick();
                break;
            case R.id.recycle_update_btn:
                updateBtnClick();
                break;
            case R.id.recycle_query_btn:
                queryBtnClick();
                break;
            case R.id.recycle_delete_btn:
                deleteBtnClick();
                break;
            default:
                break;
        }
    }

    private void insertBtnClick(){

    }

    private void updateBtnClick(){

    }

    private void queryBtnClick(){

    }

    private void deleteBtnClick(){

    }

    private void showMsg(){

    }

}
