package com.sunmi.sunmit2demo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.sunmi.sunmit2demo.BaseFragment;
import com.sunmi.sunmit2demo.ui.MoreActivity;
import com.sunmi.sunmit2demo.R;
import com.sunmi.sunmit2demo.utils.SharePreferenceUtil;

public class GoodsManagerFragment extends BaseFragment {

    Switch scale, face;
    public final static String GOODSMODE_KEY1 = "GOODSMODE_KEY1";
    public final static String GOODSMODE_KEY2 = "GOODSMODE_KEY2";

    public final static boolean default_KEY1 = true;
    public final static boolean default_KEY2 = false;

    @Override
    protected int setView() {
        return R.layout.fragment_goods_setting;
    }

    @Override
    protected void init(View view) {
        scale = view.findViewById(R.id.sw_scale);
        face = view.findViewById(R.id.sw_face);
        boolean isShowDrinks = (boolean) SharePreferenceUtil.getParam(getContext(),
                GoodsManagerFragment.GOODSMODE_KEY1, GoodsManagerFragment.default_KEY1);

        boolean isShowScaleProduct = (boolean) SharePreferenceUtil.getParam(getContext(),
                GoodsManagerFragment.GOODSMODE_KEY2, GoodsManagerFragment.default_KEY2);

        face.setChecked(isShowDrinks);
        scale.setChecked(isShowScaleProduct);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        face.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharePreferenceUtil.setParam(getContext(), GOODSMODE_KEY1, isChecked);
            }
        });

        scale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharePreferenceUtil.setParam(getContext(), GOODSMODE_KEY2, isChecked);
            }
        });
    }
}
