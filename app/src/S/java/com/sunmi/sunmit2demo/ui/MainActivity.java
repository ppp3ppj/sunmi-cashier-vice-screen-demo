package com.sunmi.sunmit2demo.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Outline;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.sunmi.electronicscaleservice.ScaleCallback;
import com.sunmi.extprinterservice.ExtPrinterService;
import com.sunmi.peripheral.printer.InnerPrinterCallback;
import com.sunmi.peripheral.printer.InnerPrinterException;
import com.sunmi.peripheral.printer.InnerPrinterManager;
import com.sunmi.peripheral.printer.SunmiPrinterService;
import com.sunmi.scalelibrary.ScaleManager;
import com.sunmi.sunmit2demo.BaseActivity;
import com.sunmi.sunmit2demo.BasePresentationHelper;
import com.sunmi.sunmit2demo.MyApplication;
import com.sunmi.sunmit2demo.R;
import com.sunmi.sunmit2demo.adapter.GoodsAdapter;
import com.sunmi.sunmit2demo.adapter.GvAdapter;
import com.sunmi.sunmit2demo.adapter.MenusAdapter;
import com.sunmi.sunmit2demo.bean.GoodsCode;
import com.sunmi.sunmit2demo.bean.GvBeans;
import com.sunmi.sunmit2demo.bean.MenusBean;
import com.sunmi.sunmit2demo.dialog.AddFruitDialogFragment;
import com.sunmi.sunmit2demo.dialog.PayDialog;
import com.sunmi.sunmit2demo.eventbus.InstallSmileEvent;
import com.sunmi.sunmit2demo.eventbus.UpdateUnLockUserEvent;
import com.sunmi.sunmit2demo.fragment.GoodsManagerFragment;
import com.sunmi.sunmit2demo.fragment.PayModeSettingFragment;
import com.sunmi.sunmit2demo.model.AlipaySmileModel;
import com.sunmi.sunmit2demo.present.TextDisplay;
import com.sunmi.sunmit2demo.present.VideoDisplay;
import com.sunmi.sunmit2demo.present.VideoMenuDisplay;
import com.sunmi.sunmit2demo.presenter.AlipaySmilePresenter;
import com.sunmi.sunmit2demo.presenter.KPrinterPresenter;
import com.sunmi.sunmit2demo.presenter.PayMentPayPresenter;
import com.sunmi.sunmit2demo.presenter.PrinterPresenter;
import com.sunmi.sunmit2demo.presenter.ProductPresenter;
import com.sunmi.sunmit2demo.presenter.ScalePresenter;
import com.sunmi.sunmit2demo.presenter.UnionPayPreseter;
import com.sunmi.sunmit2demo.unlock.UnlockServer;
import com.sunmi.sunmit2demo.utils.InstallApkUtils;
import com.sunmi.sunmit2demo.utils.ResourcesUtils;
import com.sunmi.sunmit2demo.utils.ScreenManager;
import com.sunmi.sunmit2demo.utils.SharePreferenceUtil;
import com.sunmi.sunmit2demo.utils.Utils;
import com.sunmi.sunmit2demo.view.CustomPopWindow;
import com.sunmi.sunmit2demo.view.Input2Dialog;
import com.sunmi.sunmit2demo.view.VipPayDialog;
import com.sunmi.widget.dialog.InputDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = "MainActivity";
    private ListView lvMenus;
    private MenusAdapter menusAdapter;
    private List<MenusBean> menus = new ArrayList<>();
    private RecyclerView reDrink;
    private RecyclerView reGift;
    private RecyclerView reExperience;
    private RecyclerView reRelated;

    private RecyclerView reFruit;
    private RecyclerView re_snacks;
    private RecyclerView re_vegetables;
    private RecyclerView re_others;

    private FrameLayout flUnlockUser;
    private TextView tv_user_lock;
    private CircleImageView ivUserHeadIcon;
    private FrameLayout flShoppingCar;


    private LinearLayout scaleInfoRoot;
    private ImageView ivScaleStable;
    private ImageView ivScaleNet;
    private ImageView ivScaleZero;
    private TextView tvScaleNet;
    private TextView tvScalePnet;
    private ImageView ivScaleIcon;
    private TextView tvScalePrice;
    private TextView tvScaleTotal;
    private TextView tvScaleNetDescribe;
    private LinearLayout llScalePnet;
    private TextView tvScaleOverMax;
    private TextView btnScaleZero;
    private TextView btnScaleTare;
    private TextView btnScaleNumbTare;
    private TextView btnScaleClearTare;
    private ImageView ivScaleOverMax;

    private GoodsAdapter drinkAdapter;
    private GoodsAdapter giftAdapter;
    private GoodsAdapter experienceAdapter;
    private GoodsAdapter relatedAdapter;
    private GoodsAdapter fruitAdapter;
    private GoodsAdapter snackAdapter;
    private GoodsAdapter vegetableAdapter;
    private GoodsAdapter othersAdapter;


    private TextView tvPrice;
    private TextView btnClear;
    private RelativeLayout rtlEmptyShopcar, rl_no_goods;
    private LinearLayout llyShopcar, ll_drinks, ll_snacks, ll_fruits, ll_gift, ll_experience, ll_related, ll_vegetables, ll_others, main_ll_pay;

    private ImageView ivCar;
    private RelativeLayout rlCar;
    private TextView tvCar, tvCarMoeny;
    private TextView tvVipPay, tvVipK1Pay;

    private Button btnPay;//去付款
    private BottomSheetLayout bottomSheetLayout;
    private LinearLayout llK1ShoppingCar;


    private DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private Button btnMore;//更多功能
    private TextView tv_face_pay;//去付款
    private VideoDisplay videoDisplay = null;
    private ScreenManager screenManager = ScreenManager.getInstance();
    private VideoMenuDisplay videoMenuDisplay = null;
    public TextDisplay textDisplay = null;
    private PayDialog payDialog;
    private SunmiPrinterService woyouService = null;//商米标准打印 打印服务
    private ExtPrinterService extPrinterService = null;//k1 打印服务

    private String goods_data;
    public static PrinterPresenter printerPresenter;
    public static KPrinterPresenter kPrinterPresenter;
    public UnlockServer.Proxy mProxy = null;
    CustomPopWindow popWindow;
    private boolean willwelcome;

    public static boolean isK1 = false;
    public static boolean isVertical = false;
    SoundPool soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);


    private AlipaySmilePresenter alipaySmilePresenter;
    private AlipaySmileModel alipaySmileModel;
    private PayMentPayPresenter payMentPayPresenter;
    private ScalePresenter scalePresenter;
    private UnionPayPreseter unionPayPreseter;
    Input2Dialog mInputDialog;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics dm = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 屏幕宽度
        int height = dm.heightPixels;// 屏幕宽度
        Log.e("@@@", dm.densityDpi + "  " + dm.density);
        isVertical = height > width;

        isK1 = MyApplication.getInstance().isHaveCamera() && isVertical;

        if (isK1) {
            connectKPrintService();
        } else {
            connectPrintService();
        }
        EventBus.getDefault().register(this);
        menus.clear();
        initView();
        initData();
        initAction();

    }


    //连接打印服务
    private void connectPrintService() {

        try {
            InnerPrinterManager.getInstance().bindService(this,
                    innerPrinterCallback);
        } catch (InnerPrinterException e) {
            e.printStackTrace();
        }
    }

    private InnerPrinterCallback innerPrinterCallback = new InnerPrinterCallback() {
        @Override
        protected void onConnected(SunmiPrinterService service) {
            woyouService = service;
            printerPresenter = new PrinterPresenter(MainActivity.this, woyouService);

        }

        @Override
        protected void onDisconnected() {
            woyouService = null;

        }
    };

    //连接K1打印服务
    private void connectKPrintService() {
        Intent intent = new Intent();
        intent.setPackage("com.sunmi.extprinterservice");
        intent.setAction("com.sunmi.extprinterservice.PrinterService");
        bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection connService = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            extPrinterService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            extPrinterService = ExtPrinterService.Stub.asInterface(service);
            kPrinterPresenter = new KPrinterPresenter(MainActivity.this, extPrinterService);
        }
    };

    protected void onStop() {
        super.onStop();
        this.willwelcome = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (menus.size() == 0) {
            if (videoDisplay != null) {
                videoDisplay.show();
            }
        }
        initBleService();

        if ((Boolean) SharePreferenceUtil.getParam(this, "BLE_KEY", false)) {
            if (this.willwelcome) {
                this.welcomeUserAnim();
            }

            this.willwelcome = false;
            this.flUnlockUser.setVisibility(View.VISIBLE);
        } else {
            this.flUnlockUser.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        boolean isShowDrinks = (boolean) SharePreferenceUtil.getParam(this,
                GoodsManagerFragment.GOODSMODE_KEY1, GoodsManagerFragment.default_KEY1);

        boolean isShowScaleProduct = (boolean) SharePreferenceUtil.getParam(this,
                GoodsManagerFragment.GOODSMODE_KEY2, GoodsManagerFragment.default_KEY2);


        ll_drinks.setVisibility(isShowDrinks ? View.VISIBLE : View.GONE);
        ll_snacks.setVisibility(isShowDrinks ? View.VISIBLE : View.GONE);
        ll_fruits.setVisibility(isShowScaleProduct ? View.VISIBLE : View.GONE);
        ll_vegetables.setVisibility(isShowScaleProduct ? View.VISIBLE : View.GONE);
        scaleInfoRoot.setVisibility(isShowScaleProduct ? View.VISIBLE : View.GONE);
        rl_no_goods.setVisibility(!isShowScaleProduct && !isShowDrinks ? View.VISIBLE : View.GONE);


        for (int i : GoodsCode.MODE) {
            List<GvBeans> list = ProductPresenter.getInstance().getProductListByMode(i);
            switch (i) {
                case GoodsCode.MODE_0:
                    drinkAdapter.setNewData(list);
                    break;
                case GoodsCode.MODE_1:
                    snackAdapter.setNewData(list);
                    break;
                case GoodsCode.MODE_2:
                    vegetableAdapter.setNewData(list);
                    break;
                case GoodsCode.MODE_3:
                    fruitAdapter.setNewData(list);
                    break;
                case GoodsCode.MODE_4:
                    break;
                case GoodsCode.MODE_5:
                    othersAdapter.setNewData(list);
                    ll_others.setVisibility(list.isEmpty() ? View.GONE : View.VISIBLE);
                    break;
                case GoodsCode.MODE_6:
                    giftAdapter.setNewData(list);
                    ll_gift.setVisibility(list.isEmpty() ? View.GONE : View.VISIBLE);
                    break;
                case GoodsCode.MODE_7:
                    experienceAdapter.setNewData(list);
                    ll_experience.setVisibility(list.isEmpty() ? View.GONE : View.VISIBLE);
                    break;
                case GoodsCode.MODE_8:
                    relatedAdapter.setNewData(list);
                    ll_related.setVisibility(list.isEmpty() ? View.GONE : View.VISIBLE);
                    break;
                default:

            }
        }


        int payMode = (int) SharePreferenceUtil.getParam(this, PayDialog.PAY_MODE_KEY, 7);
        switch (payMode) {
            case PayDialog.PAY_FACE:
                tv_face_pay.setVisibility(View.VISIBLE);
                break;
            case PayDialog.PAY_FACE | PayDialog.PAY_CODE | PayDialog.PAY_CASH:
                tv_face_pay.setVisibility(View.GONE);
                break;
        }
        boolean vip = (boolean) SharePreferenceUtil.getParam(this, PayModeSettingFragment.VIP_PAY_KEY, false);
        tvVipPay.setVisibility(vip ? View.VISIBLE : View.GONE);
        tvVipK1Pay.setVisibility(vip ? View.VISIBLE : View.GONE);
    }


    private void initView() {
        TextView view = findViewById(R.id.app_name);
        view.setVisibility((!isK1 && isVertical) ? View.INVISIBLE : View.VISIBLE);
        lvMenus = (ListView) findViewById(R.id.lv_menus);
        tvPrice = (TextView) findViewById(R.id.main_tv_price);
        btnClear = (TextView) findViewById(R.id.main_btn_clear);
        llyShopcar = (LinearLayout) findViewById(R.id.lly_shopcar);
        rtlEmptyShopcar = (RelativeLayout) findViewById(R.id.rtl_empty_shopcar);
        flShoppingCar = (FrameLayout) findViewById(R.id.fl_shopping_car);
        tv_face_pay = findViewById(R.id.tv_face_pay);
        main_ll_pay = findViewById(R.id.main_ll_pay);


        btnMore = (Button) findViewById(R.id.main_btn_more);

        reGift = findViewById(R.id.gv_gift);
        reExperience = findViewById(R.id.gv_experience);
        reRelated = findViewById(R.id.gv_related);
        reDrink = findViewById(R.id.gv_drinks);
        reFruit = findViewById(R.id.gv_fruits);
        re_snacks = findViewById(R.id.gv_snacks);
        re_vegetables = findViewById(R.id.gv_vegetables);
        re_others = findViewById(R.id.gv_others);

        ll_gift = findViewById(R.id.ll_gift);
        ll_experience = findViewById(R.id.ll_experience);
        ll_related = findViewById(R.id.ll_related);
        ll_drinks = findViewById(R.id.ll_drinks);
        ll_snacks = findViewById(R.id.ll_snacks);
        ll_fruits = findViewById(R.id.ll_fruits);
        ll_vegetables = findViewById(R.id.ll_vegetables);
        ll_others = findViewById(R.id.ll_others);
        rl_no_goods = findViewById(R.id.rl_no_goods);

        bottomSheetLayout = findViewById(R.id.bottomSheetLayout);
        btnPay = findViewById(R.id.main_k1_btn_pay);
        tvCarMoeny = findViewById(R.id.tv_car_money);
        tvCar = findViewById(R.id.tv_car_num);
        ivCar = findViewById(R.id.iv_car);
        rlCar = findViewById(R.id.main_btn_car);
        llK1ShoppingCar = (LinearLayout) findViewById(R.id.ll_k1_shopping_car);

        tvVipPay = findViewById(R.id.vip_pay);
        tvVipK1Pay = findViewById(R.id.vip_k1__pay);
        if (isVertical) {
            llK1ShoppingCar.setVisibility(View.VISIBLE);
            flShoppingCar.setVisibility(View.GONE);
        } else {
            llK1ShoppingCar.setVisibility(View.GONE);
            flShoppingCar.setVisibility(View.VISIBLE);
            llyShopcar.setVisibility(View.GONE);
            rtlEmptyShopcar.setVisibility(View.VISIBLE);
        }


        scaleInfoRoot = (LinearLayout) findViewById(R.id.scale_info_root);
        ivScaleStable = (ImageView) findViewById(R.id.iv_scale_stable);
        ivScaleNet = (ImageView) findViewById(R.id.iv_scale_net);
        ivScaleZero = (ImageView) findViewById(R.id.iv_scale_zero);
        tvScaleNet = (TextView) findViewById(R.id.tv_scale_net);
        tvScalePnet = (TextView) findViewById(R.id.tv_scale_pnet);
        ivScaleIcon = (ImageView) findViewById(R.id.iv_scale_icon);
        tvScalePrice = (TextView) findViewById(R.id.tv_scale_price);
        tvScaleTotal = (TextView) findViewById(R.id.tv_scale_total);
        ivScaleIcon.setClipToOutline(true);
        ivScaleIcon.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 15);
            }
        });
        tvScaleNetDescribe = (TextView) findViewById(R.id.tv_scale_net_describe);
        llScalePnet = (LinearLayout) findViewById(R.id.ll_scale_pnet);

        ivScaleOverMax = (ImageView) findViewById(R.id.iv_scale_over_max);
        tvScaleOverMax = (TextView) findViewById(R.id.tv_scale_over_max);

        btnScaleZero = (TextView) findViewById(R.id.btn_scale_zero);
        btnScaleTare = (TextView) findViewById(R.id.btn_scale_tare);
        btnScaleNumbTare = (TextView) findViewById(R.id.btn_scale_numb_tare);
        btnScaleClearTare = (TextView) findViewById(R.id.btn_scale_clear_tare);


        flUnlockUser = (FrameLayout) findViewById(R.id.fl_unlock_user);
        tv_user_lock = (TextView) findViewById(R.id.tv_user_lock);
        ivUserHeadIcon = (CircleImageView) findViewById(R.id.iv_user_head_icon);
        this.ivUserHeadIcon.setImageResource(UnlockActivity.SHOP_ICON);
        this.tv_user_lock.setText(Utils.getPmOrAm() + UnlockActivity.SHOPNAME);
        this.myHandler.postDelayed(new Runnable() {
            public void run() {
                MainActivity.this.welcomeUserAnim();
            }
        }, 1000L);
    }


    private void initAction() {
        scalePresenter = new ScalePresenter(this, new ScalePresenter.ScalePresenterCallback() {
            @Override
            public void getData(final int net, final int pnet, final int statu) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateScaleInfo(net, pnet, statu);
                    }
                });
            }

            @Override
            public void isScaleCanUse(boolean isCan) {
                if (!isCan) {
                    tvScaleNet.setText("---");
                    tvScaleNet.setTextColor(Color.RED);
                    tvScalePnet.setText("---");
                    tvScalePnet.setTextColor(Color.RED);

                }
            }
        });
        giftAdapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GvBeans gvBeans = giftAdapter.getList().get(position);
                formatGoods(gvBeans);
            }

        });
        experienceAdapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GvBeans gvBeans = experienceAdapter.getList().get(position);
                formatGoods(gvBeans);
            }

        });
        relatedAdapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GvBeans gvBeans = relatedAdapter.getList().get(position);
                formatGoods(gvBeans);
            }

        });

        othersAdapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GvBeans gvBeans = othersAdapter.getList().get(position);
                formatGoods(gvBeans);
            }

        });
        drinkAdapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GvBeans gvBeans = drinkAdapter.getList().get(position);
                formatGoods(gvBeans);
            }

        });
        snackAdapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                GvBeans gvBeans = snackAdapter.getList().get(position);
                formatGoods(gvBeans);
            }

        });
        vegetableAdapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                checkScaleGoods(position, 0);
                addGoodsByScale(scalePresenter.formatTotalMoney(), scalePresenter.getGvBeans());

            }

            @Override
            public void onItemCarClick(View view, int position) {
                super.onItemCarClick(view, position);
            }
        });
        fruitAdapter.setOnItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                checkScaleGoods(position, 1);
                addGoodsByScale(scalePresenter.formatTotalMoney(), scalePresenter.getGvBeans());
            }

            @Override
            public void onItemCarClick(View view, int position) {
                super.onItemCarClick(view, position);
            }
        });


        btnClear.setOnClickListener(this);
        btnMore.setOnClickListener(this);
        main_ll_pay.setOnClickListener(this);
        flUnlockUser.setOnClickListener(this);

        btnPay.setOnClickListener(this);
        rlCar.setOnClickListener(this);

        tvVipPay.setOnClickListener(this);
        tvVipK1Pay.setOnClickListener(this);

        btnScaleZero.setOnClickListener(this);
        btnScaleTare.setOnClickListener(this);
        btnScaleNumbTare.setOnClickListener(this);
        btnScaleClearTare.setOnClickListener(this);
    }

    private void formatGoods(GvBeans gvBeans) {
        if (gvBeans.getNumber() <= 0) {
            Toast.makeText(MainActivity.this, "库存不足", Toast.LENGTH_LONG).show();
            return;
        }
        gvBeans.setNumber(gvBeans.getNumber() - 1);
        MenusBean bean = new MenusBean();
        bean.setId("" + (menus.size() + 1));
        bean.setMoney(gvBeans.getPrice());
        bean.setName(gvBeans.getName());
        bean.setType(0);
        bean.setCode(gvBeans.getCode());
        menus.add(bean);
        float price = 0.00f;
        for (MenusBean bean1 : menus) {
            price = price + Float.parseFloat(bean1.getMoney().substring(1));
        }
        tvPrice.setText(ResourcesUtils.getString(MainActivity.this, R.string.units_money_units) + decimalFormat.format(price));
        menusAdapter.update(menus);
        Log.e("@@@@", "code==" + gvBeans.getCode());
        buildMenuJson(menus, decimalFormat.format(price));
    }


    private void initData() {
        screenManager.init(this);
        Display[] displays = screenManager.getDisplays();
        Log.e(TAG, "屏幕数量" + displays.length);
        for (int i = 0; i < displays.length; i++) {
            Log.e(TAG, "屏幕" + displays[i]);
        }
        Display display = screenManager.getPresentationDisplays();
        if (display != null && !isVertical) {
            videoDisplay = new VideoDisplay(this, display, Environment.getExternalStorageDirectory().getPath() + "/video_02.mp4");
            videoMenuDisplay = new VideoMenuDisplay(this, display, Environment.getExternalStorageDirectory().getPath() + "/video_02.mp4");
            textDisplay = new TextDisplay(this, display);
        }
        giftAdapter = new GoodsAdapter(new ArrayList<GvBeans>(), 1);
        reGift.setLayoutManager(new GridLayoutManager(this, (isVertical && !isK1) ? 2 : 4));
        reGift.setAdapter(giftAdapter);

        experienceAdapter = new GoodsAdapter(new ArrayList<GvBeans>(), 1);
        reExperience.setLayoutManager(new GridLayoutManager(this, (isVertical && !isK1) ? 2 : 4));
        reExperience.setAdapter(experienceAdapter);

        relatedAdapter = new GoodsAdapter(new ArrayList<GvBeans>(), 1);
        reRelated.setLayoutManager(new GridLayoutManager(this, (isVertical && !isK1) ? 2 : 4));
        reRelated.setAdapter(relatedAdapter);

        drinkAdapter = new GoodsAdapter(new ArrayList<GvBeans>(), 1);
        reDrink.setLayoutManager(new GridLayoutManager(this, (isVertical && !isK1) ? 2 : 4));
        reDrink.setAdapter(drinkAdapter);


        fruitAdapter = new GoodsAdapter(new ArrayList<GvBeans>(), 2);
        reFruit.setLayoutManager(new GridLayoutManager(this, (isVertical && !isK1) ? 2 : 4));
        reFruit.setAdapter(fruitAdapter);

        snackAdapter = new GoodsAdapter(new ArrayList<GvBeans>(), 3);
        re_snacks.setLayoutManager(new GridLayoutManager(this, (isVertical && !isK1) ? 2 : 4));
        re_snacks.setAdapter(snackAdapter);


        vegetableAdapter = new GoodsAdapter(new ArrayList<GvBeans>(), 2);
        re_vegetables.setLayoutManager(new GridLayoutManager(this, (isVertical && !isK1) ? 2 : 4));
        re_vegetables.setAdapter(vegetableAdapter);

        othersAdapter = new GoodsAdapter(new ArrayList<GvBeans>(), 0);
        re_others.setLayoutManager(new GridLayoutManager(this, (isVertical && !isK1) ? 2 : 4));
        re_others.setAdapter(othersAdapter);

        tvPrice.setText(ResourcesUtils.getString(this, R.string.units_money_units) + "0.00");

        menusAdapter = new MenusAdapter(this, menus);
        lvMenus.setAdapter(menusAdapter);

        payDialog = new PayDialog();
        alipaySmileModel = new AlipaySmileModel();
        alipaySmilePresenter = new AlipaySmilePresenter(this, alipaySmileModel);
        payMentPayPresenter = new PayMentPayPresenter(this);
        unionPayPreseter = new UnionPayPreseter(this);

        payDialog.setAlipaySmilePresenter(alipaySmilePresenter);
        payDialog.setPayMentPayPresenter(payMentPayPresenter);
        payDialog.setUnionPayPreseter(unionPayPreseter);

        payDialog.setCompleteListener(new PayDialog.OnCompleteListener() {
            @Override
            public void onCancel() {
                if (menus.size() > 0) {
                    if (videoMenuDisplay != null) {
                        videoMenuDisplay.show();
                    }

                }
            }

            @Override
            public void onSuccess(final int payMode) {
                playSound(payMode);
                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        paySuccessToPrinter(payMode);
                        payDialog.setPhoneNumber("");

                    }
                }, 1000);

            }

            @Override
            public void onComplete(int payMode) {
                delectProduct();
                payCompleteToReMenu();
            }
        });

        soundPool.load(MainActivity.this, R.raw.audio, 1);// 1
        soundPool.load(MainActivity.this, isZh(this) ? R.raw.alipay : R.raw.alipay_en, 1);// 2
    }

    private void delectProduct() {
        List<String> code = new ArrayList<>();
        for (MenusBean menu : menus) {
            code.add(menu.getCode());
        }
        ProductPresenter.getInstance().useProduct(code);

    }


    private void payCompleteToReMenu() {
        if (!isVertical) {
            llyShopcar.setVisibility(View.GONE);
            rtlEmptyShopcar.setVisibility(View.VISIBLE);
            tvPrice.setText(ResourcesUtils.getString(MainActivity.this, R.string.units_money_units) + "0.00");
            menus.clear();
            menusAdapter.update(menus);

            standByTime();

        } else {
            menus.clear();
            tvCarMoeny.setText("");
            tvCar.setText("");
            tvCar.setVisibility(View.GONE);
            ivCar.setImageResource(R.drawable.car_gray);
            bottomSheetLayout.dismissSheet();
            btnPay.setBackgroundColor(Color.parseColor("#999999"));
        }
    }


    private void paySuccessToPrinter(int payMode) {
        if (isK1) {
            if (kPrinterPresenter != null) {
                kPrinterPresenter.print(goods_data, payMode);
            }
        } else {

            if (printerPresenter != null) {
                printerPresenter.print(goods_data, payMode);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_unlock_user:
                this.showDomainLock();
                return;
            case R.id.main_btn_clear:
                //还给库存
                for (MenusBean menu : menus) {
                    GvBeans gvBeans = ProductPresenter.getInstance().getProductByCode(menu.getCode());
                    if (gvBeans!=null) {
                        if (!ProductPresenter.getInstance().isScale(gvBeans)) {
                            gvBeans.setNumber(gvBeans.getNumber() + 1);
                        }
                    }

                }

                if (isVertical) {
                    menus.clear();
                    tvCarMoeny.setText("");
                    tvCar.setText("");
                    tvCar.setVisibility(View.GONE);
                    ivCar.setImageResource(R.drawable.car_gray);
                    bottomSheetLayout.dismissSheet();
                    btnPay.setBackgroundColor(Color.parseColor("#999999"));
                } else {
                    llyShopcar.setVisibility(View.GONE);
                    rtlEmptyShopcar.setVisibility(View.VISIBLE);
                    menus.clear();
                    tvPrice.setText(ResourcesUtils.getString(this, R.string.units_money_units) + "0.00");
                    menusAdapter.update(menus);
                    if (videoDisplay != null) {
                        videoDisplay.show();
                    }
                }
                break;
            case R.id.main_btn_more:
                Intent intent = new Intent(MainActivity.this, MoreActivity.class);
                startActivity(intent);
                break;
            case R.id.vip_k1__pay:
                if (menus.size() > 0) {
                    vipPay();
                }
                break;
            case R.id.vip_pay:
                vipPay();
                break;
            case R.id.main_ll_pay:
                Bundle bundle = new Bundle();
                bundle.putString("MONEY", tvPrice.getText().toString());
                bundle.putString("GOODS", goods_data);

                int payMode = (int) SharePreferenceUtil.getParam(this, PayDialog.PAY_MODE_KEY, 7);

                bundle.putInt("PAYMODE", payMode);
                payDialog.setArguments(bundle);
                payDialog.show(getSupportFragmentManager(), "payDialog");

                break;

            case R.id.main_btn_car:
                if (menus.size() > 0) {
                    if (!bottomSheetLayout.isSheetShowing()) {
                        bottomSheetLayout.showWithSheetView(createBottomSheetView());
                    } else {
                        bottomSheetLayout.dismissSheet();
                    }
                }
                break;
            case R.id.main_k1_btn_pay:
                if (menus.size() > 0) {
                    if (!isK1) {
                        payDialog.completeListener.onSuccess(PayDialog.PAY_MODE_0);
                        payDialog.completeListener.onComplete(PayDialog.PAY_MODE_0);
                        return;
                    }
                    if (bottomSheetLayout.isSheetShowing()) {
                        bottomSheetLayout.dismissSheet();
                    }
                    int payModes = (int) SharePreferenceUtil.getParam(this, PayDialog.PAY_MODE_KEY, 6);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("MONEY", tvCarMoeny.getText().toString());
                    bundle1.putString("GOODS", goods_data);
                    bundle1.putInt("PAYMODE", payModes);
                    payDialog.setArguments(bundle1);
                    payDialog.show(getSupportFragmentManager(), "payDialog");
                }
                break;

            case R.id.tv_domain_lock:
                if ((Boolean) SharePreferenceUtil.getParam(this, "BLE_KEY", false)) {
                    this.mProxy.startLockDomain();
                }

                this.popWindow.dissmiss();

            case R.id.btn_scale_zero:
                scalePresenter.zero();
                break;
            case R.id.btn_scale_tare:
                scalePresenter.tare();
                break;
            case R.id.btn_scale_numb_tare:
                if (scalePresenter.getPnet() > 0) {
                    showToast(R.string.scale_tips_havenumpent);
                } else {
                    showPresetTare();
                }
                break;
            case R.id.btn_scale_clear_tare:
                scalePresenter.clearTare();
                break;
            default:
                break;
        }
    }

    VipPayDialog vipPayDialog;

    private void vipPay() {
        vipPayDialog = new VipPayDialog(this).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.tv_cancel:
                        payDialog.setPhoneNumber("");
                        break;
                    case R.id.tv_confirm:
                        payDialog.setPhoneNumber(vipPayDialog.getNum());
                        main_ll_pay.performClick();
                        break;
                }
                vipPayDialog.dismiss();
            }
        });

        vipPayDialog.show();
    }


    private void buildMenuJson(List<MenusBean> menus, String price) {
        try {
            JSONObject data = new JSONObject();
            data.put("title", "Sunmi " + ResourcesUtils.getString(this, R.string.menus_title));
            JSONObject head = new JSONObject();
            head.put("param1", ResourcesUtils.getString(this, R.string.menus_number));
            head.put("param2", ResourcesUtils.getString(this, R.string.menus_goods_name));
            head.put("param3", ResourcesUtils.getString(this, R.string.menus_unit_price));
            data.put("head", head);
            data.put("flag", "true");
            JSONArray list = new JSONArray();
            for (int i = 0; i < menus.size(); i++) {
                JSONObject listItem = new JSONObject();
                listItem.put("param1", "" + (i + 1));
                listItem.put("param2", menus.get(i).getName());
                listItem.put("param3", menus.get(i).getMoney());
                listItem.put("type", menus.get(i).getType());
                listItem.put("code", menus.get(i).getCode());
                listItem.put("net", menus.get(i).getNet());
                list.put(listItem);
            }
            data.put("list", list);
            JSONArray KVPList = new JSONArray();
            JSONObject KVPListOne = new JSONObject();
            KVPListOne.put("name", ResourcesUtils.getString(this, R.string.shop_car_total) + " ");
            KVPListOne.put("value", price);
            JSONObject KVPListTwo = new JSONObject();
            KVPListTwo.put("name", ResourcesUtils.getString(this, R.string.shop_car_offer) + " ");
            KVPListTwo.put("value", "0.00");
            JSONObject KVPListThree = new JSONObject();
            KVPListThree.put("name", ResourcesUtils.getString(this, R.string.shop_car_number) + " ");
            KVPListThree.put("value", "" + menus.size());
            JSONObject KVPListFour = new JSONObject();
            KVPListFour.put("name", ResourcesUtils.getString(this, R.string.shop_car_receivable) + " ");
            KVPListFour.put("value", price);
            KVPList.put(0, KVPListOne);
            KVPList.put(1, KVPListTwo);
            KVPList.put(2, KVPListThree);
            KVPList.put(3, KVPListFour);
            data.put("KVPList", KVPList);
            Log.d("HHHH", "onClick: ---------->" + data.toString());
            goods_data = data.toString();
            Log.d(TAG, "buildMenuJson: ------->" + (videoMenuDisplay != null));
            if (payDialog.isVisible()) {
                return;
            }
            if (videoMenuDisplay != null && !videoMenuDisplay.isShow) {
                videoMenuDisplay.show();
                videoMenuDisplay.update(menus, data.toString());
            } else if (null != videoMenuDisplay) {
                videoMenuDisplay.update(menus, data.toString());
            }
            // 购物车有东西

            if (isVertical) {
                tvCarMoeny.setText(ResourcesUtils.getString(R.string.units_money_units) + price);
                tvCar.setText(menus.size() + "");
                tvCar.setVisibility(View.VISIBLE);
                ivCar.setImageResource(R.drawable.car_white);
                btnPay.setBackgroundColor(Color.parseColor("#FC5436"));
                if (bottomSheetLayout.isSheetShowing()) {
                    menusAdapter.notifyDataSetChanged();
                    lvMenus.setSelection(menusAdapter.getCount() - 1);
                    TextView tvPrice = bottomSheetLayout.findViewById(R.id.main_tv_price);
                    tvPrice.setText(tvCarMoeny.getText().toString());
                }
            } else {
                llyShopcar.setVisibility(View.VISIBLE);
                rtlEmptyShopcar.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StringBuilder sb = new StringBuilder();
    private Handler myHandler = new Handler();

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        switch (action) {
            case KeyEvent.ACTION_DOWN:

                if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP) {
                    return super.dispatchKeyEvent(event);
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN) {
                    return super.dispatchKeyEvent(event);
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    return super.dispatchKeyEvent(event);
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
                    return super.dispatchKeyEvent(event);
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_HOME) {
                    return super.dispatchKeyEvent(event);
                }
                if (event.getKeyCode() == KeyEvent.KEYCODE_POWER) {
                    return super.dispatchKeyEvent(event);
                }
                int unicodeChar = event.getUnicodeChar();
                sb.append((char) unicodeChar);
                Log.e("MainActivity", "dispatchKeyEvent: " + unicodeChar + " " + event.getKeyCode());
                len++;
                startScan();
                return true;
            default:
                break;
        }
        return super.dispatchKeyEvent(event);
    }

    boolean isScaning = false;
    int len = 0;
    int oldLen = 0;

    private void startScan() {
        if (isScaning) {
            return;
        }
        isScaning = true;
        timerScanCal();
    }

    private void timerScanCal() {
        oldLen = len;
        myHandler.postDelayed(scan, 100);
    }

    Runnable scan = new Runnable() {
        @Override
        public void run() {
            if (oldLen != len) {
                timerScanCal();
                return;
            }
            isScaning = false;
            if (sb.length() > 0) {
                if (payDialog.isVisible()) {
                    Log.e(TAG, "支付中");
                } else {
                    addDrink(sb.toString());
                }
                sb.setLength(0);
            }
        }
    };

    private void addDrink(String code) {
        code = code.replaceAll("[^0-9a-zA-Z]", "");
        Log.e(TAG, "扫码===" + code);
        GvBeans gvBeans = ProductPresenter.getInstance().getProductByCode(code);
        if (gvBeans == null) {
            return;
        }
        if (gvBeans.getNumber() <= 0) {
            Toast.makeText(MainActivity.this, "库存不足", Toast.LENGTH_LONG).show();
            return;
        }
        gvBeans.setNumber(gvBeans.getNumber() - 1);

        MenusBean bean = new MenusBean();
        bean.setId("" + (menus.size() + 1));
        bean.setMoney(gvBeans.getPrice());
        bean.setName(gvBeans.getName());
        bean.setCode(gvBeans.getCode());
        menus.add(bean);

        float price = 0.00f;
        for (MenusBean bean1 : menus) {
            price = price + Float.parseFloat(bean1.getMoney().substring(1));
        }
        tvPrice.setText(ResourcesUtils.getString(this, R.string.units_money_units) + decimalFormat.format(price));
        menusAdapter.update(menus);

        buildMenuJson(menus, decimalFormat.format(price));

        if (isVertical) {
            if (menus.size() > 0 && !bottomSheetLayout.isSheetShowing()) {
                if (!bottomSheetLayout.isSheetShowing()) {
                    bottomSheetLayout.showWithSheetView(createBottomSheetView());
                }
            }
        }
    }

    private void checkScaleGoods(int position, int type) {
        GvBeans gvBeans = null;
        switch (type) {
            case 0:
                gvBeans = vegetableAdapter.getList().get(position);
                scalePresenter.setGvBeans(vegetableAdapter.getList().get(position));
                fruitAdapter.setSelectPosition(-1);
                vegetableAdapter.setSelectPosition(position);
                break;
            case 1:
                gvBeans = fruitAdapter.getList().get(position);
                scalePresenter.setGvBeans(fruitAdapter.getList().get(position));
                fruitAdapter.setSelectPosition(position);
                vegetableAdapter.setSelectPosition(-1);
                break;
        }

        ivScaleIcon.setImageResource(gvBeans.getLogo());
        tvScalePrice.setText(scalePresenter.getPrice() + "");

        vegetableAdapter.notifyDataSetChanged();
        fruitAdapter.notifyDataSetChanged();
    }

    private void addGoodsByScale(String total, GvBeans gvBeans) {
        if (!scalePresenter.isStable() || ScalePresenter.net <= 0) {
            return;
        }
        MenusBean bean = new MenusBean();
        bean.setId("" + (menus.size() + 1));
        bean.setMoney(gvBeans.getPrice());
        bean.setName(gvBeans.getName());
        bean.setType(0);
        bean.setCode(gvBeans.getCode());
        menus.add(bean);
        float price = 0.00f;
        for (MenusBean bean1 : menus) {
            price = price + Float.parseFloat(bean1.getMoney().substring(1));
        }
        tvPrice.setText(ResourcesUtils.getString(MainActivity.this, R.string.units_money_units) + decimalFormat.format(price));
        menusAdapter.update(menus);
        buildMenuJson(menus, decimalFormat.format(price));
    }

    /**
     * 更新秤显示信息
     *
     * @param net
     * @param pnet
     * @param statu
     */
    private void updateScaleInfo(final int net, int pnet, int statu) {
        if (pnet == 0) {
            tvScaleNetDescribe.setText(R.string.scale_net_nopnet);
        } else {
            tvScaleNetDescribe.setText(R.string.scale_net_kg);
        }
        tvScaleNet.setText(scalePresenter.formatQuality(net));
        tvScalePnet.setText(scalePresenter.formatQuality(pnet));
        tvScaleTotal.setText(scalePresenter.formatTotalMoney(net));


        ivScaleZero.setActivated(net == 0);
        ivScaleStable.setActivated((statu & 1) == 1);
        ivScaleNet.setActivated(pnet > 0);
        if ((statu & 1) != 1) {
            //重量不稳定
            ivScaleStable.setActivated(false);
            ivScaleNet.setActivated(false);
            ivScaleZero.setActivated(false);
        }
        //超载
        if ((statu & 4) == 4) {
            ivScaleStable.setActivated(false);
            ivScaleNet.setActivated(false);
            ivScaleZero.setActivated(false);
            if (tvScaleOverMax.getVisibility() == View.GONE) {
                tvScaleOverMax.setVisibility(View.VISIBLE);
                tvScaleOverMax.setSelected(true);

                ivScaleOverMax.setVisibility(View.VISIBLE);
                ivScaleOverMax.setSelected(true);

                tvScaleNet.setVisibility(View.GONE);


                tvScaleOverMax.setText(R.string.scale_over_max);
            }
        } else if ((pnet + net) < 0) {
//            欠载
            ivScaleStable.setActivated(false);
            ivScaleNet.setActivated(false);
            ivScaleZero.setActivated(false);
            if (tvScaleOverMax.getVisibility() == View.GONE) {
                tvScaleOverMax.setVisibility(View.VISIBLE);
                tvScaleOverMax.setSelected(false);

                ivScaleOverMax.setVisibility(View.VISIBLE);
                ivScaleOverMax.setSelected(false);

                tvScaleNet.setVisibility(View.GONE);

                tvScaleOverMax.setText(R.string.scale_over_mix);
            }
        } else {
            if (tvScaleOverMax.getVisibility() == View.VISIBLE) {
                tvScaleOverMax.setVisibility(View.GONE);
                ivScaleOverMax.setVisibility(View.GONE);
            }
            if (tvScaleNet.getVisibility() == View.GONE) {
                tvScaleNet.setVisibility(View.VISIBLE);
            }
        }


    }

    private void showPresetTare() {

        mInputDialog = new Input2Dialog.Builder(this)
                .setTitle(ResourcesUtils.getString(R.string.more_num_peele) + "/kg")
                .setLeftText(ResourcesUtils.getString(R.string.cancel))
                .setRightText(ResourcesUtils.getString(R.string.confrim))
                .setHint("0.00kg")
                .setCallBack(new InputDialog.DialogOnClickCallback() {
                    @Override
                    public void onSure(String text) {
                        if (!TextUtils.isEmpty(text)) {
                            float pnet = Float.parseFloat(text);
                            scalePresenter.setNumPnet((int) (pnet * 1000.0f));
                        }
                        mInputDialog.dismiss();
                    }

                    @Override
                    public void onCancel() {
                        mInputDialog.dismiss();
                    }
                })
                .build();
        mInputDialog.show();
    }

    //待机
    private void standByTime() {
        if (videoDisplay != null && !videoDisplay.isShow) {
            videoDisplay.show();
        }
    }


    private void playSound(final int payMode) {
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                soundPool.play(1, 1, 1, 10, 0, 1);
                if (payMode == 2) {
                    soundPool.play(2, 1, 1, 10, 0, 1);
                }
            }
        }, 200);
    }

    public static boolean isZh(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (language.endsWith("zh"))
            return true;
        else
            return false;
    }

    private View createBottomSheetView() {
        View bottomSheet = LayoutInflater.from(this).inflate(R.layout.sheet_layout, bottomSheetLayout, false);
        lvMenus = bottomSheet.findViewById(R.id.lv_menus);
        TextView tvPrice = bottomSheet.findViewById(R.id.main_tv_price);
        TextView btnClear = bottomSheet.findViewById(R.id.main_btn_clear);
        btnClear.setOnClickListener(this);
        menusAdapter = new MenusAdapter(this, menus);
        lvMenus.setAdapter(menusAdapter);
        lvMenus.setSelection(menusAdapter.getCount() - 1);
        tvPrice.setText(tvCarMoeny.getText().toString());
        return bottomSheet;
    }


    @Subscribe(
            threadMode = ThreadMode.MAIN
    )
    public void Event(UpdateUnLockUserEvent var1) {
        this.ivUserHeadIcon.setImageResource(var1.getIcon());
        this.willwelcome = true;
        this.tv_user_lock.setAlpha(0.0F);
        this.tv_user_lock.setText(Utils.getPmOrAm() + var1.getName());
        if (var1.isShowAnim()) {
            this.tv_user_lock.post(new Runnable() {
                public void run() {
                    MainActivity.this.welcomeUserAnim();
                }
            });
        }

    }


    private void welcomeUserAnim() {
        this.tv_user_lock.setAlpha(1.0F);
        int var1 = this.tv_user_lock.getMeasuredWidth() + 17;
        AnimatorSet var2 = new AnimatorSet();
        ObjectAnimator var3 = ObjectAnimator.ofFloat(this.tv_user_lock, "translationX", new float[]{(float) var1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, (float) var1, (float) var1, 0.0F});
        var3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator var1) {
                if (var1.getCurrentPlayTime() > 4200L && MainActivity.this.tv_user_lock.getText().toString().contains(Utils.getPmOrAm())) {
                    MainActivity.this.tv_user_lock.setText(MainActivity.this.tv_user_lock.getText().toString().replace(Utils.getPmOrAm(), ""));
                }

            }
        });
        var2.setDuration(5000L);
        var2.setInterpolator(new LinearInterpolator());
        var2.play(var3);
        var2.start();
    }

    private void initBleService() {
        boolean isOpen = (Boolean) SharePreferenceUtil.getParam(this, "BLE_KEY", false);
        if (isOpen && mProxy == null) {
            this.bindService(new Intent(this, UnlockServer.class), this.mServiceConnection, Service.BIND_AUTO_CREATE);
        }
        if (mProxy != null) {
            if (isOpen) {
                mProxy.updateAllUser();
            } else {
                mProxy.close();
            }
        }
    }

    private void showDomainLock() {
        if (this.popWindow == null) {
            View var1 = LayoutInflater.from(this).inflate(R.layout.pop_lock, (ViewGroup) null);
            var1.setOnClickListener(this);
            this.popWindow = (new CustomPopWindow.PopupWindowBuilder(this)).setView(var1).create();
        }

        this.popWindow.showAsDropDown(this.ivUserHeadIcon, -133, 15);
    }


    @Override
    protected void onDestroy() {
        soundPool.release();
        if (extPrinterService != null) {
            unbindService(connService);
        }
        if (woyouService != null) {
            try {
                InnerPrinterManager.getInstance().unBindService(this,
                        innerPrinterCallback);
            } catch (InnerPrinterException e) {
                e.printStackTrace();
            }
        }
        if (mProxy != null) {
            unbindService(mServiceConnection);
        }

        if (scalePresenter != null && scalePresenter.isScaleSuccess()) {
            scalePresenter.onDestroy();
        }

        if (payMentPayPresenter != null) {
            payMentPayPresenter.destoryReceiver();
        }

        if (alipaySmilePresenter != null) {
            alipaySmilePresenter.destory();
        }
        if (unionPayPreseter != null) {
            unionPayPreseter.unBindService();
        }
        printerPresenter = null;
        kPrinterPresenter = null;
        EventBus.getDefault().unregister(this);
        BasePresentationHelper.getInstance().dismissAll();
        super.onDestroy();
    }

    //退出时的时间
    private long mExitTime;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(MainActivity.this, ResourcesUtils.getString(this, R.string.tips_exit), Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName var1, IBinder var2) {
            MainActivity.this.mProxy = (UnlockServer.Proxy) var2;
        }

        public void onServiceDisconnected(ComponentName var1) {
        }
    };

}
