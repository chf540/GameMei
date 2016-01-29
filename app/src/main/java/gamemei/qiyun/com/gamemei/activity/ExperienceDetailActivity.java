package gamemei.qiyun.com.gamemei.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.activity.common.BaseActivity;
import gamemei.qiyun.com.gamemei.utils.AppUtils;
import gamemei.qiyun.com.gamemei.widget.xlistview.XListView;

/**
 * 经验详情界面
 * Created by hfcui on 2016/1/28.
 */
public class ExperienceDetailActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 日志标记
     */
    private String TAG = "ExperienceDetailActivity";
    /**
     * 评论列表项
     */
    private XListView xListView;
    /**
     * listview适配器
     */
    private MyAdapter madapter;
    /**
     * ListView顶部的View
     */
    private View activity_head_experience_view;
    /**
     * 视频缩略图
     */
    private ImageView iv_experience_picture;
    /**
     * 返回按钮
     */
    private ImageView title_bar_back;

    private Button btn_issue;

    private EditText et_comment_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_detail);
        //设置XListView
        setXListView();
    }

    /**
     * 设置XListView
     */
    private void setXListView() {
        xListView = (XListView) findViewById(R.id.experience_detail_listview);
        xListView.setAdapter(madapter);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(false); // 设置不用下拉刷新和加载更多
        // 设置条目可以被点击
        xListView.addHeaderView(activity_head_experience_view); // listView添加头布局
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        madapter = new MyAdapter();
        activity_head_experience_view = View.inflate(getApplicationContext(), R.layout.activity_head_experience_view, null);
        ViewUtils.inject(this, activity_head_experience_view);
        iv_experience_picture = (ImageView) activity_head_experience_view.findViewById(R.id.iv_experience_picture);
        title_bar_back = (ImageView) findViewById(R.id.title_bar_back);
        btn_issue = (Button) findViewById(R.id.btn_issue);
        et_comment_content = (EditText) findViewById(R.id.et_comment_content);
        btn_issue.setOnClickListener(this);
        title_bar_back.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        //设置展示图片
        iv_experience_picture.setImageResource(R.drawable.legend);
    }

    /**
     * 设置监听
     */
    @Override
    protected void setListener() {
        //设置输入框变化监听
        et_comment_content.addTextChangedListener(new TextWatcher() {
            //初始化状态
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_issue.setBackgroundResource(R.drawable.bg_edittext);
            }

            //输入框变时状态
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_issue.setBackgroundColor(Color.parseColor("#0ad9b2"));
                btn_issue.setTextColor(Color.parseColor("#ffffff"));
            }

            //输入后的状态
            @Override
            public void afterTextChanged(Editable editable) {
                String string = et_comment_content.getText().toString().trim();
                if (TextUtils.isEmpty(string)) {
                    btn_issue.setBackgroundResource(R.drawable.bg_edittext);
                    btn_issue.setTextColor(Color.parseColor("#969696"));
                }
            }
        });
    }

    /**
     * 处理点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_play:
                Intent playVideo = new Intent(this, PlayVideoActivity.class);
                startActivity(playVideo);
                break;
            case R.id.title_bar_back:
                finish();
                break;
            case R.id.btn_issue:
                //获取到输入框内容
                String string = et_comment_content.getText().toString().trim();
                if (!TextUtils.isEmpty(string)) {
                    AppUtils.showTips(this, R.mipmap.tips_smile, "发布成功");
                    //清空输入框
                    et_comment_content.setText("");
                } else {
                    AppUtils.showTips(this, R.mipmap.tips_error, "不能发布空白评论");
                }
                break;

            default:
                break;
        }
    }

    /**
     * ListView 适配器
     *
     * @author hfcui
     */
    public class MyAdapter extends BaseAdapter {
        private LayoutInflater mInflater = null;

        private MyAdapter() {
            this.mInflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        // 获取一个在数据集中指定索引的视图来显示数据
        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.item_comment_list, null);
            return convertView;
        }
    }
}