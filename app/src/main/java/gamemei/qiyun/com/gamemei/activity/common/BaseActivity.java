package gamemei.qiyun.com.gamemei.activity.common;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.utils.AppUtils;
import gamemei.qiyun.com.gamemei.widget.dialog.LoadingDialog;

/**
 * Created by hfcui on 2015/12/30.
 */
public abstract class BaseActivity extends FragmentActivity {
    /**
     * Loading界面
     */
    private LoadingDialog loadingDialog;
    /**
     * 启动的activity名称
     */
    ComponentName activityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkNet(); // 检测网络连接
    }

    /**
     * 初始化组件
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 设置监听
     */
    protected abstract void setListener();

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initView();
        initData();
        setListener();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        initData();
        setListener();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initView();
        initData();
        setListener();
    }

    /**
     * 检查网络连接状态
     */
    private void checkNet() {
        if (!AppUtils.isNetworkConnected(this)) {
            AppUtils.showTips(this, R.drawable.tips_error, "网络未连接，请先连接网络...");
        }
    }

    /**
     * Loading界面打开
     */
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCancelable(false);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        loadingDialog.show();
    }

    /**
     * 关闭loading界面
     */
    public void dismissLoading() {
        //延時关闭loading
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                loadingDialog.dismiss();
            }
        };
        timer.schedule(task, 1000);
    }
}
