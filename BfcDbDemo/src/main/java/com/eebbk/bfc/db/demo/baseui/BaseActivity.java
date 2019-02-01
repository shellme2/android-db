package com.eebbk.bfc.db.demo.baseui;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.eebbk.bfc.db.demo.util.IdUtil;

public class BaseActivity extends AppCompatActivity {

    public <T extends View> T findView(@IdRes int resId){
        return (T)findViewById(resId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        IdUtil.saveLastId(this.getApplicationContext());
    }
}
