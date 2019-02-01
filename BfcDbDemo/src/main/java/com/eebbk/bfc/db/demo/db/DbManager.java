package com.eebbk.bfc.db.demo.db;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.eebbk.bfc.db.demo.db.entity.DaoMaster;
import com.eebbk.bfc.db.demo.db.entity.DaoSession;

/**
 * Desc:
 * Author: llp
 * Create Time: 2016-11-24 15:35
 * Email: jacklulu29@gmail.com
 */

public class DbManager {

    private Context mAppContext;
    private String mDbName;
    private DaoMaster mDaoMaster;

    private static class DbManagerHandler {
        private static DbManager mInstance = new DbManager();
    }

    public static void init(@NonNull Context context, @NonNull String dbName){
        if(context == null){
            throw new IllegalArgumentException(" context must not null! ");
        }
        if(TextUtils.isEmpty(dbName)){
            throw new IllegalArgumentException(" dbName must not null! ");
        }
        DbManagerHandler.mInstance.mAppContext = context.getApplicationContext();
        DbManagerHandler.mInstance.mDbName = dbName;
        initDaoMaster(DbManagerHandler.mInstance.mAppContext, DbManagerHandler.mInstance.mDbName);
    }

    public static DbManager getInstance(){
        if(DbManagerHandler.mInstance.mAppContext == null){
            throw new IllegalArgumentException(" context must not null,please call DbManager.init(...) first! ");
        }
        return DbManagerHandler.mInstance;
    }

    /*
     * 取得DaoSession
     * @param context
     * @return
     */
    public DaoSession getDaoSession() {
        if (mDaoMaster == null) {
            throw new IllegalArgumentException(" must call init() first! ");
        }
        return mDaoMaster.newSession();
    }

    private static void initDaoMaster(Context context, String dbName) {
        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context, dbName, null);
        DbManagerHandler.mInstance.mDaoMaster = new DaoMaster(helper.getWritableDatabase());
    }
}
