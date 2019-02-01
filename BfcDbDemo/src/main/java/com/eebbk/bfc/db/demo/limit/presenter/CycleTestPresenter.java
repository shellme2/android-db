package com.eebbk.bfc.db.demo.limit.presenter;

import android.content.Context;

import com.eebbk.bfc.db.demo.limit.model.CycleTestModel;

/**
 * Desc:
 * Author: llp
 * Create Time: 2016-12-29 11:25
 * Email: jacklulu29@gmail.com
 */

public class CycleTestPresenter {

    private CycleTestModel mModel;

    public CycleTestPresenter(Context context) {
        mModel = new CycleTestModel(context);
    }

    public void start(String operation){
        mModel.saveStartTime(System.currentTimeMillis());
        mModel.saveOperation(operation);
        mModel.saveProcessTime(0);
        mModel.saveTotalTime(0);
        mModel.saveCounts(0);
        mModel.saveEndTime(-1);
    }

    public void testOne(long processTime){
        mModel.saveProcessTime(processTime);
        mModel.saveTotalTime(mModel.getTotalTime()+processTime);
        mModel.saveCounts(mModel.getCounts() + 1);
    }

    public void stop(){
        mModel.saveEndTime(System.currentTimeMillis());
    }

}
