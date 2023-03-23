package com.sunmi.sunmit2demo.db;

import com.sunmi.sunmit2demo.MyApplication;
import com.sunmi.sunmit2demo.bean.GvBeans;
import com.sunmi.sunmit2demo.bean.GvBeansDao;
import com.sunmi.sunmit2demo.bean.blescan.BraceletUserBean;

import java.util.List;

public class GoodsDbManager {
    private GvBeansDao mDaoSession = MyApplication.getInstance().getDaoSession().getGvBeansDao();


    public List<GvBeans> getAllGoods() {
        try {
            List var1 = this.mDaoSession.queryBuilder().list();
            return var1;
        } catch (Exception var2) {
            var2.printStackTrace();
        }
        return null;
    }

    public void addGoods(GvBeans gvBeans) {
        try {
            this.mDaoSession.insertOrReplace(gvBeans);
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    public void deleteGoods(GvBeans gvBeans) {
        try {
            this.mDaoSession.delete(gvBeans);
        } catch (Exception var2) {
            var2.printStackTrace();
        }
    }

    public void updateGoods(GvBeans gvBeans) {
        this.mDaoSession.update(gvBeans);
    }
}
