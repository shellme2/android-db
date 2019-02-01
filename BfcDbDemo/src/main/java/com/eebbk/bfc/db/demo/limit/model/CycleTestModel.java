package com.eebbk.bfc.db.demo.limit.model;

import android.content.Context;

import com.eebbk.bfc.db.demo.util.SharedPreferencesUtil;

/**
 * Desc:
 * Author: llp
 * Create Time: 2016-11-13 19:38
 * Email: jacklulu29@gmail.com
 */

public class CycleTestModel {

    private static final String CYCLE_TEST_CONFIG = "cycle_test_config";
    private static final String KEY_OPERATION = "key_operation";
    private static final String KEY_PROCESS_TIME = "key_process_time";
    private static final String KEY_TOTAL_TIME = "key_total_time";
    private static final String KEY_COUNTS = "key_counts";
    private static final String KEY_START_TIME = "key_start_time";
    private static final String KEY_END_TIME = "key_end_time";

    private SharedPreferencesUtil mSharePfe;

    public CycleTestModel(Context context){
        mSharePfe = new SharedPreferencesUtil(context, CYCLE_TEST_CONFIG);
    }

    public void saveOperation(String operation){
        mSharePfe.putString(KEY_OPERATION, operation);
    }

    public String getOperation(){
        return mSharePfe.getString(KEY_OPERATION, null);
    }

    public void saveProcessTime(long time){
        mSharePfe.putLong(KEY_PROCESS_TIME, time);
    }

    public long getProcessTime(){
        return mSharePfe.getLong(KEY_PROCESS_TIME, -1);
    }

    public void saveTotalTime(long time){
        mSharePfe.putLong(KEY_TOTAL_TIME, time);
    }

    public long getTotalTime(){
        return mSharePfe.getLong(KEY_TOTAL_TIME, -1);
    }

    public void saveCounts(int counts){
        mSharePfe.putInt(KEY_COUNTS, counts);
    }

    public int getCounts(){
        return mSharePfe.getInt(KEY_COUNTS);
    }

    public void saveStartTime(long time){
        mSharePfe.putLong(KEY_START_TIME, time);
    }

    public long getStartTime(){
        return mSharePfe.getLong(KEY_START_TIME, -1);
    }

    public void saveEndTime(long time){
        mSharePfe.putLong(KEY_END_TIME, time);
    }

    public long getEndTime(){
        return mSharePfe.getLong(KEY_END_TIME, -1);
    }
}
