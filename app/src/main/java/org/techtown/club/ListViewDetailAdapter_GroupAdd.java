package org.techtown.club;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewDetailAdapter_GroupAdd extends BaseAdapter {

    private Context mContext;
    private ArrayList<ListItemDetail2> listItems4 = new ArrayList<>();

    public ListViewDetailAdapter_GroupAdd(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return listItems4.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems4.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // item.xml 레이아웃을 inflate해서 참조획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_list_groupadd, parent, false);
        }

        // item.xml 의 참조 획득
        TextView groupMemberName = (TextView)convertView.findViewById(R.id.groupMemberName);
        Button btn_delete = (Button)convertView.findViewById(R.id.btn_delete);

        ListItemDetail2 listItemDetail2 = listItems4.get(position);

        groupMemberName.setText(listItemDetail2.getWhat());

        // 리스트 아이템 삭제
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItems4.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public void addItem(String what){
        ListItemDetail2 listItemDetail2 = new ListItemDetail2();

        listItemDetail2.setWhat(what);

        listItems4.add(listItemDetail2);
    }
}