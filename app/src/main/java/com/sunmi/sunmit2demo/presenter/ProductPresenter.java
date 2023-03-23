package com.sunmi.sunmit2demo.presenter;

import android.text.TextUtils;

import com.sunmi.sunmit2demo.MyApplication;
import com.sunmi.sunmit2demo.R;
import com.sunmi.sunmit2demo.bean.GoodsCode;
import com.sunmi.sunmit2demo.bean.GvBeans;
import com.sunmi.sunmit2demo.db.GoodsDbManager;
import com.sunmi.sunmit2demo.utils.ResourcesUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Copyright (C), 2018-2019, 商米科技有限公司
 * FileName: ProductPresenter
 *
 * @author: liuzhicheng
 * Date: 19-8-29
 * Description:
 * History:
 */
public class ProductPresenter {
    private List<GvBeans> gvBeansList = new ArrayList<>();
    private GoodsDbManager goodsDbManager;

    private ProductPresenter() {
        init();
    }


    public static ProductPresenter getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private void init() {
        if (this.goodsDbManager == null) {
            this.goodsDbManager = new GoodsDbManager();
        }
        updateAllGoods();
    }

    private void updateAllGoods() {
        List<String> codeList = new ArrayList<>();
        if (this.goodsDbManager.getAllGoods().size() > 0) {
            Iterator<GvBeans> iterator = this.goodsDbManager.getAllGoods().iterator();
            while (iterator.hasNext()) {
                GvBeans value = iterator.next();
                codeList.add(value.getCode());
                //有图片才显示
                if (value.getImgId() != 0 || !TextUtils.isEmpty(value.getImgUrl())) {
                    gvBeansList.add(value);
                }
            }
        }
        for (int i = 0; i < GoodsCode.code.length; i++) {
            //添加默认商品
            if (!codeList.contains(GoodsCode.code[i])) {
                GvBeans gvBeans;
                if (GoodsCode.string[i] instanceof String) {
                    gvBeans = new GvBeans(GoodsCode.icon[i], (String) GoodsCode.string[i], ResourcesUtils.getString(R.string.units_money) + GoodsCode.price[i]
                            , GoodsCode.code[i], 10000, GoodsCode.unit[i], GoodsCode.mode[i]);
                } else {
                    gvBeans = new GvBeans(GoodsCode.icon[i], ResourcesUtils.getString(MyApplication.getInstance(),
                            (Integer) GoodsCode.string[i]), ResourcesUtils.getString(R.string.units_money) + GoodsCode.price[i]
                            , GoodsCode.code[i], 10000, GoodsCode.unit[i], GoodsCode.mode[i]);
                }
                int logo = 0;
                //称重商品 需要在选中时 显示Logo
                if (GoodsCode.mode[i] == GoodsCode.MODE_2) {
                    logo = GoodsCode.dialog_logo[i];
                }
                if (GoodsCode.mode[i] == GoodsCode.MODE_3) {
                    logo = GoodsCode.dialog_logo[i];
                }
                if (logo != 0) {
                    gvBeans.setLogo(logo);
                }
                addProduct(gvBeans);
            }
        }

    }


    public boolean isScale(GvBeans gvBeans) {
        if (gvBeans == null) {
            return false;
        }
        return gvBeans.getMode() == GoodsCode.MODE_2 || gvBeans.getMode() == GoodsCode.MODE_3;
    }

    public List<GvBeans> getAllProduct() {
        return gvBeansList;
    }

    public List<GvBeans> getProductListByMode(int mode) {
        List<GvBeans> list = new ArrayList<>();
        for (GvBeans gvBean : gvBeansList) {
            if (gvBean.getMode() == mode) {
                list.add(gvBean);
            }
        }
        return list;
    }

    public GvBeans getProductByCode(String code) {
        if (code.length() > 2) {
            for (GvBeans gvBeans : gvBeansList) {
                if (code.equals(gvBeans.getCode())) {
                    return gvBeans;
                }
            }
        }
        return null;
    }

    public void addProduct(GvBeans gvBeans) {
        if (!gvBeansList.contains(gvBeans)) {
            gvBeansList.add(gvBeans);
        }
        goodsDbManager.addGoods(gvBeans);
    }

    public void delectProduct(GvBeans gvBeans) {
        gvBeansList.remove(gvBeans);
        goodsDbManager.deleteGoods(gvBeans);
    }

    public void useProduct(List<String> code) {
        for (GvBeans gvBean : gvBeansList) {
            if (code.equals(gvBean.getCode())) {
                if (!isScale(gvBean)) {
                    addProduct(gvBean);
                }
            }

        }

    }

    private static class SingletonHolder {
        private static final ProductPresenter INSTANCE = new ProductPresenter();
    }
}
