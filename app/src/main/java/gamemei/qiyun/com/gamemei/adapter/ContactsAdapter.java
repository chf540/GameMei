package gamemei.qiyun.com.gamemei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import gamemei.qiyun.com.gamemei.R;


/**
 * hfcui
 */
public class ContactsAdapter extends BaseAdapter {

    private String[] mNameArray;
    private LayoutInflater mLayoutInflater;


    public ContactsAdapter(Context context, String[] names) {
        mNameArray = names;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mNameArray == null)
            return 0;
        return mNameArray.length;
    }

    @Override
    public Object getItem(int position) {
        if (mNameArray == null || mNameArray.length >= position)
            return null;
        return mNameArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null || convertView.getTag() == null) {

            convertView = mLayoutInflater.inflate(R.layout.item_contacts,
                    parent, false);
            viewHolder = new ViewHolder();
            viewHolder.titleTextView = (TextView) convertView
                    .findViewById(R.id.txt1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.titleTextView.setText(mNameArray[position]);

        return convertView;
    }

    static class ViewHolder {
        TextView titleTextView;
    }
}
