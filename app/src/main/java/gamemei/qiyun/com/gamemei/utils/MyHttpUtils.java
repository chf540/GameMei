package gamemei.qiyun.com.gamemei.utils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.client.CookieStore;

public class MyHttpUtils {
    // http://gamemei.com/qiyun/games/latest.js
    // http://gamemei.com/qiyun/turretLegend_compiled.zip

    //网络连接地址
    public final static String BASE_URL = "http://gamemei.com/qiyun/";

    public final static String PLAY_GAME_LIST = BASE_URL + "games/latest.js";

    public final static String PHOTOS_URL = "http://gamemei.com/";

    public final static String DOWNLOAD_URL = "http://gamemei.com/";

    /**
     * 静态类保存Cookies
     * Created by hfcui on 2015/12/29.
     */
    public static CookieStore cookieStore = null;


    /**
     * 请求网络数据
     *
     * @param httpMethod
     * @param url
     * @param params
     * @param callBack
     */
    public static void requestData(HttpRequest.HttpMethod httpMethod, String url,
                                   RequestParams params, RequestCallBack<String> callBack) {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(httpMethod, url, params, callBack);
        //保存Cookies
        // DefaultHttpClient httpCilent = (DefaultHttpClient) httpUtils.getHttpClient();
        // MyHttpUtils.cookieStore = httpCilent.getCookieStore();
    }
}
