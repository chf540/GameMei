package gamemei.qiyun.com.gamemei.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;
import gamemei.qiyun.com.gamemei.app.AppApplication;
import gamemei.qiyun.com.gamemei.fragment.menuFragment.GameGiftFragment;
import gamemei.qiyun.com.gamemei.fragment.menuFragment.HomeFragment;
import gamemei.qiyun.com.gamemei.fragment.menuFragment.PersonalCenterFragment;
import gamemei.qiyun.com.gamemei.fragment.menuFragment.PlayGameFragment;
import gamemei.qiyun.com.gamemei.utils.AppUtils;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class HomeActivity extends BaseActivity {
    /**
     * 日志标记
     */
    private final String TAG = "HomeActivity";
    /**
     * 底部导航按钮
     */
    private RadioGroup menu_radiogroup;
    /**
     * 首页界面
     */
    private HomeFragment rb_home;
    /**
     * 游戏界面
     */
    private PlayGameFragment rb_game;
    /**
     * 礼包界面
     */
    private GameGiftFragment rb_gift;
    /**
     * 个人中心界面
     */
    private PersonalCenterFragment rb_my;

    /**
     * Fragment管理器
     */
    private FragmentManager fragmentManager = this.getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;

    /**
     * token 的主要作用是身份授权和安全，因此不能通过客户端直接访问融云服务器获取 token，
     * 您必须通过 Server API 从融云服务器 获取 token 返回给您的 App，并在之后连接时使用。
     */
    private String token = "GPFay7xc4kpgxy2ID7euB2yU5Y7Kahofn38JU+5pBuSuTg10j/Hh8rxkxOj8mb5j5YSWVg0JXPvVru1Gc+L3hw==";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "hfcui =======MainActivity===onCreate");
        setContentView(R.layout.activity_home);
        connect(token);
    }

    /**
     * 建立与融云服务器的连接
     */
    private void connect(String token) {
        //测试用
        if (getApplicationInfo().packageName.equals(AppApplication.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess" + userid);

                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("LoginActivity", "--onError" + errorCode);

                }
            });
        }
    }

    /**
     * 初始化组件
     */
    protected void initView() {
        menu_radiogroup = (RadioGroup) findViewById(R.id.menu_radiogroup);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        //TODO
    }

    /**
     * 设置各种监听事件
     */
    @Override
    protected void setListener() {
        //底部导航点击切换
        menu_radiogroup
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rb_home:
                                linpager1();
                                break;
                            case R.id.rb_game:
                                linpager2();
                                break;
                            case R.id.rb_gift:
                                linpager3();
                                break;
                            case R.id.rb_my:
                                linpager4();
                                break;
                            case R.id.rb_search:
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                break;
                        }
                    }
                });
        // 默认选择的底部导航按钮
        menu_radiogroup.check(R.id.rb_home);
    }

    /**
     * 选择要显示的fragment
     */
    private void setSelected() {
        if (rb_home != null) {
            fragmentTransaction.hide(rb_home);
        }
        if (rb_game != null) {
            fragmentTransaction.hide(rb_game);
        }
        if (rb_gift != null) {
            fragmentTransaction.hide(rb_gift);
        }
        if (rb_my != null) {
            fragmentTransaction.hide(rb_my);
        }
    }

    private void linpager1() {
        // 每次点击时都需要重新开始事务
        fragmentTransaction = fragmentManager.beginTransaction();
        // 把显示的Fragment隐藏
        setSelected();
        // 跳转到游戏资讯界面
        if (rb_home == null) {
            rb_home = new HomeFragment();
            fragmentTransaction.add(R.id.fl_content, rb_home);
        } else {
            fragmentTransaction.show(rb_home);
        }
        fragmentTransaction.commit();
    }

    private void linpager2() {
        // 每次点击时都需要重新开始事务
        fragmentTransaction = fragmentManager.beginTransaction();
        // 把显示的Fragment隐藏
        setSelected();
        // 跳转到玩游戏界面
        if (rb_game == null) {
            rb_game = new PlayGameFragment();
            fragmentTransaction.add(R.id.fl_content, rb_game);

        } else {
            fragmentTransaction.show(rb_game);
        }
        fragmentTransaction.commit();
    }

    private void linpager3() {
        // 每次点击时都需要重新开始事务
        fragmentTransaction = fragmentManager.beginTransaction();
        // 把显示的Fragment隐藏
        setSelected();
        // 跳转到游戏礼包的界面
        if (rb_gift == null) {
            rb_gift = new GameGiftFragment();
            fragmentTransaction.add(R.id.fl_content, rb_gift);
        } else {
            fragmentTransaction.show(rb_gift);
        }

        fragmentTransaction.commit();
    }

    private void linpager4() {
        // 每次点击时都需要重新开始事务
        fragmentTransaction = fragmentManager.beginTransaction();
        // 把显示的Fragment隐藏
        setSelected();
        //跳转到个人中心界面
        if (rb_my == null) {
            rb_my = new PersonalCenterFragment();
            fragmentTransaction.add(R.id.fl_content, rb_my);
        } else {
            fragmentTransaction.show(rb_my);
        }
        fragmentTransaction.commit();
    }

    /**
     * 退出按钮事件
     */
    @SuppressWarnings("deprecation")
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框消息
            isExit.setMessage("确定要退出吗");
            // 添加选择按钮并注册监听
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            // 显示对话框
            isExit.show();
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 弹框选择是否退出应用
     */
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 连续双击两次退出程序
     */
    // public boolean onKeyDown(int keyCode, KeyEvent event) {
    // if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
    // if ((System.currentTimeMillis() - mExitTime) > 2000 ) {
    // Toast.makeText(this, "再按一次退出应用程序", Toast.LENGTH_SHORT).show();
    // mExitTime = System.currentTimeMillis();
    // }else{
    // SysApplication.getInstance().exit();
    // }
    //
    // return true;
    // }
    // return super.onKeyDown(keyCode, event);
    // }
}
