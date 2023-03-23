package com.sunmi.sunmit2demo.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.sunmi.sunmit2demo.utils.SharePreferenceUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Copyright (C), 2018-2019, 商米科技有限公司
 * FileName: BackupTask
 *
 * @author: liuzhicheng
 * Date: 19-5-8
 * Description: ${DESCRIPTION}
 * History:
 */
public class BackupTask extends AsyncTask<String, Void, Integer> {
    public static final String COMMAND_BACKUP = "backupDatabase";
    public static final String COMMAND_RESTORE = "restroeDatabase";
    public static final String isRecover = "BackHelperIsRecover";

    private Context mContext;
    BackHelper.OnBackCallBack onBackCallBack;

    public BackupTask(Context context, BackHelper.OnBackCallBack onBackCallBack) {
        this.mContext = context;
        this.onBackCallBack = onBackCallBack;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (onBackCallBack != null) {
            switch (integer) {
                case 0:
                    Log.e("@@@", "BackupTask==success");
                    onBackCallBack.onSuccess();
                    break;
                case -1:
                    Log.e("@@@", "BackupTask==fail");
                    onBackCallBack.onFail();
                    break;
            }
        }

    }

    @Override
    protected Integer doInBackground(String... params) {
        // TODO Auto-generated method stub

        // 获得正在使用的数据库路径，我的是 sdcard 目录下的 /dlion/db_dlion.db
        // 默认路径是 /data/data/(包名)/databases/*.db
        File dbFile = mContext.getDatabasePath(params[2]);
        mContext.getExternalFilesDirs(null);
        File exportDir = new File(params[1]);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        File backup = new File(exportDir, dbFile.getName());
        Log.d("@@@", "BackupTask=="+params[0]);

        String command = params[0];
        if (command.equals(COMMAND_BACKUP)) {
            try {
                backup.createNewFile();
                fileCopy(dbFile, backup);
                return 0;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                return -1;
            }
        } else if (command.equals(COMMAND_RESTORE)) {
            try {
                fileCopy(backup, dbFile);
                SharePreferenceUtil.setParam(mContext, isRecover, true);
                return 0;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                return -1;
            }
        } else {
            return null;
        }
    }

    private void fileCopy(File dbFile, File backup) throws IOException {
        // TODO Auto-generated method stub
        FileChannel inChannel = new FileInputStream(dbFile).getChannel();
        FileChannel outChannel = new FileOutputStream(backup).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }
}
