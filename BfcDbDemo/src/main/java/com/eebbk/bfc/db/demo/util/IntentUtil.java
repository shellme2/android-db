package com.eebbk.bfc.db.demo.util;

import android.app.Activity;
import android.content.Intent;

import com.eebbk.bfc.db.demo.basic.ui.BasicFunctionTestActivity;
import com.eebbk.bfc.db.demo.basic.ui.BasicOperationActivity;
import com.eebbk.bfc.db.demo.basic.ui.ShowVersionInfoActivity;
import com.eebbk.bfc.db.demo.limit.ui.LimitTestActivity;
import com.eebbk.bfc.db.demo.other.OtherTestActivity;
import com.eebbk.bfc.db.demo.performance.PerformanceTestActivity;
import com.eebbk.bfc.db.demo.safe.SafeTestActivity;

/**
 * Desc: 界面跳转
 * Author: llp
 * Create Time: 2016-11-14 20:19
 * Email: jacklulu29@gmail.com
 */

public class IntentUtil {

    private static final Class<?> sBasicFunctionTest = BasicFunctionTestActivity.class;
    private static final Class<?> sSafeTest = SafeTestActivity.class;
    private static final Class<?> sPerformanceTest = PerformanceTestActivity.class;
    private static final Class<?> sLimitTest = LimitTestActivity.class;
    private static final Class<?> sOtherTest = OtherTestActivity.class;


    private static final Class<?> sShowBasicOperation = BasicOperationActivity.class;
    private static final Class<?> sShowVersionInfo = ShowVersionInfoActivity.class;

    private IntentUtil(){
        // private construct
    }

    public static void gotoBasicFunctionTest(Activity activity){
        startTestActivity(activity, sBasicFunctionTest);
    }

    public static void gotoSafeTest(Activity activity){
        startTestActivity(activity, sSafeTest);
    }

    public static void gotoPerformanceTest(Activity activity){
        startTestActivity(activity, sPerformanceTest);
    }

    public static void gotoLimitTest(Activity activity){
        startTestActivity(activity, sLimitTest);
    }

    public static void gotoOtherTest(Activity activity){
        startTestActivity(activity, sOtherTest);
    }

    public static void gotoShowBasicOperationActivity(Activity activity){
        startTestActivity(activity, sShowBasicOperation);
    }

    public static void gotoShowUpdateActivity(Activity activity){
        startTestActivity(activity, sShowVersionInfo);
    }

    public static void gotoShowQueryActivity(Activity activity){
        startTestActivity(activity, sShowVersionInfo);
    }

    public static void gotoShowDeleteActivity(Activity activity){
        startTestActivity(activity, sShowVersionInfo);
    }

    public static void gotoShowVersionInfoActivity(Activity activity){
        startTestActivity(activity, sShowVersionInfo);
    }

    public static void startTestActivity(Activity activity, Class<?> activityClass){
        Intent intent = new Intent(activity, activityClass);
        activity.startActivity(intent);
    }
}
