package gamemei.qiyun.com.gamemei.activity.rongim;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import gamemei.qiyun.com.gamemei.R;


/**
 * 聚合会话列表
 */
public class SubConversationListActivtiy extends FragmentActivity {

    private ImageView title_bar_back;
    private TextView titletext;
    /**
     * 聚合类型
     */
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subconversationlist);

        titletext = (TextView) findViewById(R.id.tv_title);
        title_bar_back = (ImageView) findViewById(R.id.title_bar_back);
        titletext.setText("聚合会话列表");
        title_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getActionBarTitle();
    }

    /**
     * 通过 intent 中的数据，得到当前的 targetId 和 type
     */
    private void getActionBarTitle() {

        Intent intent = getIntent();

        type = intent.getData().getQueryParameter("type");
        if (type.equals("group")) {
            titletext.setText("聚合群组");
        } else if (type.equals("private")) {
            titletext.setText("聚合单聊");
        } else if (type.equals("discussion")) {
            titletext.setText("聚合讨论组");
        } else if (type.equals("system")) {
            titletext.setText("聚合系统会话");
        } else {
            titletext.setText("聚合");
        }

    }
}

