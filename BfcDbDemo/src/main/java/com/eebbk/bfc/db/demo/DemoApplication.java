package com.eebbk.bfc.db.demo;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.support.annotation.RequiresPermission;
import android.support.v4.content.ContextCompat;

import com.eebbk.bfc.db.demo.db.DbManager;
import com.eebbk.bfc.db.demo.util.BlockCanaryConfig;
import com.eebbk.bfc.db.demo.util.CrashHandler;
import com.eebbk.bfc.db.demo.util.DemoUtil;
import com.eebbk.bfc.db.demo.util.IdUtil;
import com.eebbk.bfc.db.demo.util.L;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

/**
 * Desc:
 * Author: llp
 * Create Time: 2016-10-21 16:00
 * Email: jacklulu29@gmail.com
 */

public class DemoApplication extends Application {

    private static final boolean DEVELOPER_MODE = false;
    public static final boolean sIsDebug = true;
    private String mSaveLogPath = null;

    private static Context sContext;

    @Override
    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void onCreate() {
        sContext = this;
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        CrashHandler.getInstance().init(this);
        if (DEVELOPER_MODE) {
            initStrictMode();
            initLeakCanary();
            initBlockCanary();
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            L.e(" no permission granted ");
        } else {
            mSaveLogPath = DemoUtil.getSaveLogPath();

            L.setLog(L.buildLog(sIsDebug, mSaveLogPath), sIsDebug);
            L.e(" init save log success " + mSaveLogPath);
        }

        initDb();
    }

    public void initDb(){
        IdUtil.initLastId(this);
        DbManager.init(getApplicationContext(), "bfcDbDemo");
    }
    private void initBlockCanary() {
        BlockCanary.install(this, new BlockCanaryConfig()).start();
    }

    private void initLeakCanary() {
        // leakcanary默认只监控Activity的内存泄露
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

    }


    private void initStrictMode() {
        //针对线程的监视策略
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        //针对vm
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
    public static Context getAppContext() {
        return sContext;
    }

}
