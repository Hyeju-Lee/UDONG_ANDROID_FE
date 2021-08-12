package org.techtown.club;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewDetailAdapter3 extends BaseAdapter {

    private Context mContext;
    private ArrayList<ListItemDetail2> listItems = new ArrayList<>();

    public ListViewDetailAdapter3(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
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
            convertView = inflater.inflate(R.layout.detail_role_item_list, parent, false);
        }

        // item.xml 의 참조 획득
        TextView role_name = (TextView)convertView.findViewById(R.id.role_name);
        CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.checkBox);
        Button btn_delete = (Button)convertView.findViewById(R.id.btn_delete);

        ListItemDetail2 listItemDetail2 = listItems.get(position);

        // 가져온 데이터를 텍스트뷰에 입력
        role_name.setText(listItemDetail2.getWhat());

        // 리스트 아이템 삭제
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItems.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public void addItem(String what){
        ListItemDetail2 listItemDetail2 = new ListItemDetail2();

        listItemDetail2.setWhat(what);

        listItems.add(listItemDetail2);
    }
}