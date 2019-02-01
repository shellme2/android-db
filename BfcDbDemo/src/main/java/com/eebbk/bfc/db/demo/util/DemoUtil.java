package com.eebbk.bfc.db.demo.util;

import android.os.Environment;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Desc:
 * Author: llp
 * Create Time: 2016-10-23 10:40
 * Email: jacklulu29@gmail.com
 */

public class DemoUtil {

    private DemoUtil(){
        // private construct
    }

    private static String getBooleanStr(boolean value){
        return value ? "是" : "否";
    }


    public static String getSaveLogPath(){
        String fileName = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date());
        String saveLogPath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + "BfcDbDemo" + File.separator + fileName +".log";
        return saveLogPath;
    }
}
