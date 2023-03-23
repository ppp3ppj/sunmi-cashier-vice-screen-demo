package com.sunmi.sunmit2demo.ui;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;

import com.sunmi.sunmit2demo.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import static com.sunmi.sunmit2demo.ui.BackupTask.COMMAND_BACKUP;
import static com.sunmi.sunmit2demo.ui.BackupTask.COMMAND_RESTORE;
import static com.sunmi.sunmit2demo.ui.BackupTask.isRecover;

/**
 * Copyright (C), 2018-2019, 商米科技有限公司
 * FileName: BackHelper
 *
 * @author: liuzhicheng
 * Date: 19-5-8
 * Description: ${DESCRIPTION}
 * History:
 */
public class BackHelper {
    public interface OnBackCallBack {
        void onSuccess();

        void onFail();

    }

    static String dbPath = "ble_bracelet";
    static String savePath = "/sunmiDb/sunmiBackUp";

    // 数据恢复
    public static void dataRecover(Context context,OnBackCallBack onBackCallBack) {
        // TODO Auto-generated method stub
        if (!isRecover(context)) {
            List<String> list = getUSBPaths(context);
            if (!list.isEmpty()) {
                new BackupTask(context,onBackCallBack).execute(COMMAND_RESTORE, list.get(0) + "/Android/data/" + context.getPackageName() + savePath, dbPath);
            }
        }
    }

    // 数据备份
    public static void dataBackup(Context context,OnBackCallBack onBackCallBack) {
        // TODO Auto-generated method stub
        List<String> list = getUSBPaths(context);
        if (!list.isEmpty()) {
            new BackupTask(context,onBackCallBack).execute(COMMAND_BACKUP, list.get(0) + "/Android/data/" + context.getPackageName() + savePath, dbPath);
        }
    }

    private static boolean isRecover(Context context) {
        return (boolean) SharePreferenceUtil.getParam(context, isRecover, false);
    }

    public static List<String> getUSBPaths(Context con) {//反射获取路径
        String[] paths = null;
        List<String> data = new ArrayList();
        StorageManager storageManager = (StorageManager) con.getSystemService(Context.STORAGE_SERVICE);
        try {
            paths = (String[]) StorageManager.class.getMethod("getVolumePaths", null).invoke(storageManager, null);
            for (String path : paths) {
                String state = (String) StorageManager.class.getMethod("getVolumeState", String.class).invoke(storageManager, path);
                if (state.equals(Environment.MEDIA_MOUNTED) && !path.contains("emulated")) {
                    data.add(path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
