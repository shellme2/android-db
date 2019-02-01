package com.eebbk.bfc.db.demo.util;

import android.content.Context;

import com.eebbk.bfc.db.demo.basic.model.BasicModel;

/**
 * Desc:
 * Author: llp
 * Create Time: 2017-01-03 18:05
 * Email: jacklulu29@gmail.com
 */

public class IdUtil {

    private volatile static long sIdCounts = 0;

    private IdUtil(){
        // do nothings
    }

    public static synchronized long generateId(){
        return sIdCounts ++;
    }

    public static synchronized void initLastId(Context appContext){
        BasicModel basicModel = new BasicModel(appContext);
        sIdCounts = basicModel.getLastId();
    }

    public static synchronized void saveLastId(Context appContext){
        BasicModel basicModel = new BasicModel(appContext);
        basicModel.setLastId(sIdCounts);
    }

}
