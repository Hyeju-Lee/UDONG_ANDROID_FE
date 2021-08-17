package org.techtown.club.notice;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.techtown.club.Notice;
import org.techtown.club.R;

import java.util.List;

public class NoticeListAdapter extends BaseAdapter {

    private Context context;
    private List<Notice> noticeList;

    public NoticeListAdapter(Context context, List<Notice> noticeList) {
        this.context = context;
        this.noticeList = noticeList;
    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_list_notice, null);
        TextView notice = (TextView) v.findViewById(R.id.notice);
        TextView name = (TextView)v.findViewById(R.id.name);
        TextView date = (TextView)v.findViewById(R.id.date);
        TextView content = (TextView)v.findViewById(R.id.content);

        notice.setText(noticeList.get(position).getNotice());
        name.setText(noticeList.get(position).getName());
        date.setText(noticeList.get(position).getDate());
        content.setText(noticeList.get(position).getContent());

        v.setTag(noticeList.get(position).getNotice());
        return v;
    }
}
