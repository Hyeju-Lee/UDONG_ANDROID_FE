package org.techtown.club.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.techtown.club.R;

import java.util.ArrayList;
import java.util.List;

public class GroupListAdapter_Frag3 extends ArrayAdapter {
    private Context context;
    private List list;

    class ViewHolder {
        public TextView groupTittle;
        public TextView groupPeople;
    }

    public GroupListAdapter_Frag3(Context context, ArrayList list) {
        super(context,0,list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertview2, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertview2 == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            convertview2 = layoutInflater.inflate(R.layout.item_list_post, parent, false);
        }
        viewHolder = new ViewHolder();
        viewHolder.groupTittle = (TextView) convertview2.findViewById(R.id.groupTittle);
        viewHolder.groupPeople = (TextView)convertview2.findViewById(R.id.groupPeople);

        final ListItemDetail_Frag3 data = (ListItemDetail_Frag3)list.get(position);
        viewHolder.groupTittle.setText(data.getGroupTittle());
        viewHolder.groupPeople.setText(data.getGroupPeople());
        return convertview2;

    }
}
