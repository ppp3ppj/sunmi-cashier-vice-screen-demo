package com.sunmi.sunmit2demo.bean;

import android.text.TextUtils;
import android.util.Log;

import com.sunmi.sunmit2demo.MyApplication;
import com.sunmi.sunmit2demo.R;
import com.sunmi.sunmit2demo.bean.blescan.BraceletUserBean;
import com.sunmi.sunmit2demo.db.GoodsDbManager;
import com.sunmi.sunmit2demo.utils.ResourcesUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GoodsCode {
    public final static int MODE_0 = 0; //饮料
    public final static int MODE_1 = 1; //零食
    public final static int MODE_2 = 2; //水果
    public final static int MODE_3 = 3; //蔬菜
    public final static int MODE_4 = 4; //无需显示商品
    public final static int MODE_5 = 5; //其他
    public final static int MODE_6 = 6; //礼品
    public final static int MODE_7 = 7; //体验
    public final static int MODE_8 = 8; //周边
    public static int[] MODE = {MODE_0, MODE_1, MODE_2, MODE_3, MODE_4, MODE_5, MODE_6, MODE_7, MODE_8};

    //无需称重商品
    public static String[] code = {
            "6954767417684", "6954767430362", "4891028164456", "6905069998814", "6920584471017", "6921168550128",

            "5000168181578", "'4901830330687", "8994391013188", "8994391013201", "5039378000911", "5012262010531",
            "4260290263158",

//            "6901939621257", "6928804014686", "6925303721367", "6921581596048",//drinks

//            "6948939635682", "6948939611542", "4895058313542", "4895058313531", "4895058313533", "1111",//snacks
//            "6948939635686", "6948939611543", "4895058313549", "4895058313532",//snacks

            //称重商品
            "1", "2", "3", "4",
            "5", "6", "7", "8",//fruits

            "9", "10", "11", "12",//vegetables

            "6928804011142", "6902827110013", "6920202888883",//others
            "6901939621608", "6928804017304", "4901830330687"
    };

    public static int[] icon = {
            R.drawable.product8, R.drawable.product9, R.drawable.product10, R.drawable.product11, R.drawable.product12, R.drawable.product13,//drinks

//            R.drawable.goods_union_1, R.drawable.goods_union_2, R.drawable.goods_union_3, R.drawable.goods_union_4, R.drawable.goods_union_5, R.drawable.goods_union_6,//snacks
            R.drawable.product1, R.drawable.product2, R.drawable.product3, R.drawable.product4, R.drawable.product5, R.drawable.product6,
            R.drawable.product7,

            R.drawable.apple, R.drawable.pears, R.drawable.banana, R.drawable.pitaya,
            R.drawable.goods_sc_1, R.drawable.goods_sc_2, R.drawable.goods_sc_3, R.drawable.goods_sc_4,//fruits

            R.drawable.goods_scs_1, R.drawable.goods_scs_2, R.drawable.goods_scs_3, R.drawable.goods_scs_4,//vegetables

            0, 0, 0,
            0, 0, 0
//            R.drawable.coco, R.drawable.sprit, R.drawable.redbull,
    };

    public static Object[] string = {
            "可口可乐摩登罐", "雪碧纤维+", "维他柠檬茶", "信远斋桂花酸梅汤", "旺仔牛奶", "维他命水柠檬味",

            "go ahead涂层夹心草莓味饼干", "三立夹心饼干白巧克力", "哇酥咔巧克力味爆浆威化卷", "哇酥咔干酪味爆浆威化卷", "哈得斯薯片番茄味", "哈得斯薯片蜂蜜芥末味",
            "亨利低糖松脆玉米片",


//            R.string.goods_1, R.string.goods_2, R.string.goods_3, R.string.goods_4,//drinks

//            "旺仔浪味仙-蔬菜味", "旺仔小馒头-特浓牛奶味", "旺旺黑白配-香草味", "旺仔QQ糖-葡萄味", "旺仔牛奶-原味", "旺仔QQ糖-水蜜桃味",
//            R.string.goods_5, R.string.goods_6, R.string.goods_7, R.string.goods_8,//snacks

            R.string.goods_apple, R.string.goods_pear, R.string.goods_banana, R.string.goods_pitaya,
            R.string.goods_sc_1, R.string.goods_sc_2, R.string.goods_sc_3, R.string.goods_sc_4,//fruits

            R.string.goods_scs_1, R.string.goods_scs_2, R.string.goods_scs_3, R.string.goods_scs_4,//vegetables

            R.string.goods_coke, R.string.goods_sprite, R.string.goods_red_bull,//others
            "可口可乐摩登罐", "雪碧纤维+", "三立夹心饼干白巧克力",

    };


    public static  String[] unit = {
            "罐", "罐", "盒", "瓶", "罐", "瓶",

            "盒", "盒", "盒", "盒", "袋", "袋", "盒",

//            R.string.goods_1, R.string.goods_2, R.string.goods_3, R.string.goods_4,//drinks

//            "旺仔浪味仙-蔬菜味", "旺仔小馒头-特浓牛奶味", "旺旺黑白配-香草味", "旺仔QQ糖-葡萄味", "旺仔牛奶-原味", "旺仔QQ糖-水蜜桃味",
//            R.string.goods_5, R.string.goods_6, R.string.goods_7, R.string.goods_8,//snacks

            "", "", "", "",
            "", "", "", "",
            "", "", "", "",
            "", "", "", "",
            "罐", "罐", "盒"
    };
    public static float[] price = {
            3, 5, 4, 7, 6, 5,

            34.9f, 19.9f, 18.9f, 18.9f, 26.8f, 26.8f, 20.9f,

//            3.00f, 3.00f, 3.50f, 4.50f,//drinks

//            2.1f, 5.1f, 4.1f, 3.1f, 6.1f, 0.02f,
//            6.80f, 6.80f, 6.60f, 6.60f,//snacks

            9.90f, 7.00f, 12.0f, 16.0f,
            13.0f, 20.0f, 12.0f, 8.00f,//fruits

            5.50f, 3.50f, 4.70f, 9.90f,//vegetables

            5.00f, 4.00f, 6.00f,//others
            3, 5, 19.9f,

    };

    public static int[] mode = {
            MODE_0,MODE_0,MODE_0,MODE_0,MODE_0,MODE_0,
            MODE_1,MODE_1,MODE_1,MODE_1,MODE_1,MODE_1,MODE_1,
            MODE_2,MODE_2,MODE_2,MODE_2,MODE_2,MODE_2,MODE_2,MODE_2,
            MODE_3,MODE_3,MODE_3,MODE_3,
            MODE_4,MODE_4,MODE_4,MODE_4,MODE_4,MODE_4
    };
    public static int[] species = {
            6,//mode = 0
            7,// mode = 1
            8,//mode = 2
            4,// mode = 3
            6,//other mode = 4
    };

    public static  int[] dialog_logo = {
            0,0,0,0,0,0,
            0,0,0,0,0,0,0,
            R.drawable.apple_dialog, R.drawable.pears_dialog, R.drawable.banana_dialog, R.drawable.pitaya_dialog,
            R.drawable.goods_sc_icon_1, R.drawable.goods_sc_icon_2, R.drawable.goods_sc_icon_3, R.drawable.goods_sc_icon_4,

            R.drawable.goods_scs_icon_1, R.drawable.goods_scs_icon_2, R.drawable.goods_scs_icon_3, R.drawable.goods_scs_icon_4,
            0,0,0,0,0,0,
    };


    private Map<String, GvBeans> Goods = new HashMap<>();
    List<GvBeans> drinks = new ArrayList<>();
    List<GvBeans> snacks = new ArrayList<>();
    List<GvBeans> vegetables = new ArrayList<>();
    List<GvBeans> fruits = new ArrayList<>();
    List<GvBeans> others = new ArrayList<>();

    private static GoodsCode instance = null;



    public static GoodsCode getInstance() {
        if (instance == null) {
            instance = new GoodsCode();
        }
        return instance;
    }


    private GoodsCode() {



    }


    public List<GvBeans> getVegetables() {
        return vegetables;
    }

    public List<GvBeans> getFruits() {
        return fruits;
    }

    public Map<String, GvBeans> getGood() {
        return Goods;
    }

    public List<GvBeans> getSnacks() {

        return snacks;
    }

    public List<GvBeans> getDrinks() {
        return drinks;
    }

    public List<GvBeans> getOthers() {
        return others;
    }

    public void add(String code, int imageId, String resString, float price, int mode, String unit, int logo) {
        GvBeans gvBeans = new GvBeans(imageId, resString, ResourcesUtils.getString(R.string.units_money) + price, code, 10000, unit, mode);
        if (logo != 0) {
            gvBeans.setLogo(logo);
        }
        Goods.put(code, gvBeans);
        addToMode(mode, gvBeans);
    }


    public void addToDb(String code, String imageUrl, String resString, float price, int num, String unit, int mode) {
        GvBeans gvBeans = new GvBeans(imageUrl, resString, ResourcesUtils.getString(R.string.units_money) + price, code, num, unit, mode);
        addToDb(gvBeans, code, mode);
    }

    public void addToDb(String code, int imageId, String resString, float price, int num, String unit, int mode) {
        GvBeans gvBeans = new GvBeans(imageId, resString, ResourcesUtils.getString(R.string.units_money) + price, code, num, unit, mode);
        addToDb(gvBeans, code, mode);
    }

    private void addToDb(GvBeans gvBeans, String code, int mode) {
        Goods.put(code, gvBeans);
        addToMode(mode, gvBeans);
    }

    public void update(GvBeans gvBeans) {
    }

    public void deleteGoods(String code) {
        deleteForMode(Goods.get(code).getMode(), Goods.get(code));
        Goods.remove(code);
    }

    private void deleteForMode(int mode, GvBeans gvBeans) {
        switch (mode) {
            case MODE_0:
                drinks.remove(gvBeans);
                break;
            case MODE_1:
                snacks.remove(gvBeans);
                break;
            case MODE_2:
                fruits.remove(gvBeans);
                break;
            case MODE_3:
                vegetables.remove(gvBeans);
                break;
            case MODE_4:
                break;
            case MODE_5:
                others.remove(gvBeans);
                break;
        }
    }

    private void addToMode(int mode, GvBeans gvBeans) {
        switch (mode) {
            case MODE_0:
                drinks.add(gvBeans);
                break;
            case MODE_1:
                snacks.add(gvBeans);
                break;
            case MODE_2:
                fruits.add(gvBeans);
                break;
            case MODE_3:
                vegetables.add(gvBeans);
                break;
            case MODE_4:
                break;
            case MODE_5:
                others.add(gvBeans);
                break;

        }
    }


}
