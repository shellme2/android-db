package com.eebbk.bfc.db.demo.performance;

import android.os.Bundle;
import android.view.View;

import com.eebbk.bfc.db.demo.R;
import com.eebbk.bfc.db.demo.baseui.BaseActivity;

public class PerformanceTestActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_test);
        setTitle("性能测试");
        initView();
    }

    private void initView(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
    }

}
