package org.techtown.club;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter_Receipt extends ArrayAdapter {
    private Context context;
    private List list;

    class ViewHolder {
        public TextView plusText;
        public TextView minusText;
        public TextView useDate;
    }

    public CustomListAdapter_Receipt(Context context, ArrayList list) {
        super(context,0,list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertview == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertview = layoutInflater.inflate(R.layout.custom_listview_receipt, parent, false);
        }

        viewHolder = new ViewHolder();
        viewHolder.plusText = (TextView) convertview.findViewById(R.id.plusCost);
        viewHolder.minusText = (TextView)convertview.findViewById(R.id.minusCost);
        viewHolder.useDate = (TextView)convertview.findViewById(R.id.useDate);

        final ReceiptListData data = (ReceiptListData)list.get(position);
        viewHolder.plusText.setText(data.getPlusText());
        viewHolder.minusText.setText(data.getMinusText());
        viewHolder.useDate.setText(data.getUseDate());


        return convertview;
    }
}
