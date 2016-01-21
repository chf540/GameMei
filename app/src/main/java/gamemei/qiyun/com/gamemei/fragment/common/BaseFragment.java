package gamemei.qiyun.com.gamemei.fragment.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.DefaultHttpRedirectHandler;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.apache.http.impl.client.DefaultHttpClient;

import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;

import gamemei.qiyun.com.gamemei.utils.MyHttpUtils;
import gamemei.qiyun.com.gamemei.widget.dialog.LoadingDialog;

/**
 * 主界面fragment的公共基类
 *
 * @author hfcui
 */
public abstract class BaseFragment extends Fragment {
    public View view;
    /**
     * 上下文环境
     */
    public Context context;
    /**
     * 正在加载
     */
    private LoadingDialog loadingDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 上下文环境
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = initView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public abstract void initData();

    public abstract View initView();

    /**
     * 请求网络数据
     *
     * @param httpMethod
     * @param url
     * @param params
     * @param callBack
     */
    public void requestData(HttpMethod httpMethod, String url,
                            RequestParams params, RequestCallBack<String> callBack) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(httpMethod, url, params, callBack);
        //保存Cookies
        // DefaultHttpClient httpCilent = (DefaultHttpClient) httpUtils.getHttpClient();
        // MyHttpUtils.cookieStore = httpCilent.getCookieStore();
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

    /**
     * 打开loading界面
     */
    public void showLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(getActivity());
            loadingDialog.setCancelable(false);
            loadingDialog.setCanceledOnTouchOutside(false);
        }
        loadingDialog.show();
    }
}