package org.techtown.club;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
        View v = View.inflate(context, R.layout.layout_notice_item, null);
        TextView notice = (TextView)convertView.findViewById(R.id.notice);
        TextView name = (TextView)convertView.findViewById(R.id.name);
        TextView date = (TextView)convertView.findViewById(R.id.date);
        TextView content = (TextView)convertView.findViewById(R.id.content);

        notice.setText(noticeList.get(position).getNotice());
        name.setText(noticeList.get(position).getName());
        date.setText(noticeList.get(position).getDate());
        content.setText(noticeList.get(position).getContent());

        v.setTag(noticeList.get(position).getNotice());
        return v;
    }
}
