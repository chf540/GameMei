package gamemei.qiyun.com.gamemei.activity.rongim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gamemei.qiyun.com.gamemei.R;
import gamemei.qiyun.com.gamemei.adapter.ContactsAdapter;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 讨论组的功能
 */
public class ContactsActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView mListView;
    private ContactsAdapter mAdapter;

    private ImageView title_bar_back;
    private TextView titletext;
    /**
     * ids 收消息人的 id
     */
    String[] ids = {"56145", "56146", "56147", "56148"};
    List mLists = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);

        mListView = (ListView) findViewById(R.id.list);
        titletext = (TextView) findViewById(R.id.tv_title);
        title_bar_back = (ImageView) findViewById(R.id.title_bar_back);
        titletext.setText("创建讨论组");
        mAdapter = new ContactsAdapter(ContactsActivity.this, ids);
        mListView.setAdapter(mAdapter);
        for (int i = 0; i < ids.length; i++) {
            mLists.add(ids[i]);
        }
        mListView.setOnItemClickListener(this);
        title_bar_back.setOnClickListener(this);
        title_bar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (RongIM.getInstance() != null)
            RongIM.getInstance().startPrivateChat(ContactsActivity.this, ids[position], "title");
    }

    @Override
    public void onClick(View v) {

        if (RongIM.getInstance() != null)
        /**
         *创建讨论组时，mLists为要添加的讨论组成员，创建者一定不能在 mLists 中
         */
            RongIM.getInstance().getRongIMClient().createDiscussion("Hello Discussion",
                    mLists, new RongIMClient.CreateDiscussionCallback() {
                        @Override
                        public void onSuccess(String s) {

                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {

                        }
                    });
    }
}
