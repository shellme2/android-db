package com.eebbk.bfc.db.demo.basic.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eebbk.bfc.db.demo.R;
import com.eebbk.bfc.db.demo.baseui.BaseActivity;
import com.eebbk.bfc.db.demo.util.IntentUtil;

public class BasicFunctionTestActivity extends BaseActivity implements View.OnClickListener {

    private Button mOperationBtn;
    private Button mShowVersionInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_function_test);
        setTitle("基本功能测试");
        initView();
    }

    private void initView(){
        mOperationBtn = findView(R.id.operation_btn);
        mShowVersionInfoBtn = findView(R.id.show_version_info_btn);

        mOperationBtn.setOnClickListener(this);
        mShowVersionInfoBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.operation_btn:
                IntentUtil.gotoShowBasicOperationActivity(BasicFunctionTestActivity.this);
                break;
            case R.id.show_version_info_btn:
                IntentUtil.gotoShowVersionInfoActivity(BasicFunctionTestActivity.this);
                break;
            default:
                break;
        }

    }

}
