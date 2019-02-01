package com.eebbk.bfc.db.demo.basic.model;

import android.content.Context;

import com.eebbk.bfc.db.demo.util.SharedPreferencesUtil;

/**
 * Desc:
 * Author: llp
 * Create Time: 2016-11-13 19:38
 * Email: jacklulu29@gmail.com
 */

public class BasicModel {

    private static final String BASIC_CONFIG = "basic_config";
    private static final String LAST_ID = "last_id";

    private SharedPreferencesUtil mSharePfe;

    public BasicModel(Context context){
        mSharePfe = new SharedPreferencesUtil(context, BASIC_CONFIG);
    }

    public long getLastId(){
        return mSharePfe.getLong(LAST_ID, 0);
    }

    public void setLastId(long id){
        mSharePfe.putLong(LAST_ID, id);
    }
}
