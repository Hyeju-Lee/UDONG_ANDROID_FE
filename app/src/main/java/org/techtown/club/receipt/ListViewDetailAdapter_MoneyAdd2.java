package org.techtown.club.receipt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.techtown.club.ListItemDetail;
import org.techtown.club.R;

import java.util.ArrayList;

public class ListViewDetailAdapter_MoneyAdd2 extends BaseAdapter {
    private Context mContext2;
    private ArrayList<ListItemDetail> listItems2 = new ArrayList<ListItemDetail>();

    public ListViewDetailAdapter_MoneyAdd2(Context context2){
        this.mContext2 = context2;
    }

    @Override
    public int getCount() {
        return listItems2.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems2.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position2, View convertView2, ViewGroup parent2) {

        // item.xml 레이아웃을 inflate해서 참조획득
        if(convertView2 == null){
            LayoutInflater inflater = (LayoutInflater) mContext2.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView2 = inflater.inflate(R.layout.item_list_moneyadd, parent2, false);
        }

        // item.xml 의 참조 획득
        TextView what_listitem = (TextView)convertView2.findViewById(R.id.what_listitem);
        TextView name_listitem = (TextView)convertView2.findViewById(R.id.name_listitem);
        TextView money_listitem = (TextView)convertView2.findViewById(R.id.money_listitem);
        Button btn_delete = (Button)convertView2.findViewById(R.id.btn_delete);

        ListItemDetail listItemDetail2 = listItems2.get(position2);

        // 가져온 데이터를 텍스트뷰에 입력
        what_listitem.setText(listItemDetail2.getWhat());
        name_listitem.setText(listItemDetail2.getName());
        money_listitem.setText(listItemDetail2.getMoney());

        // 리스트 아이템 삭제
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItems2.remove(position2);
                notifyDataSetChanged();
            }
        });

        return convertView2;
    }

    public void addItem(String what2, String name2, String money2){
        ListItemDetail listItemDetail2= new ListItemDetail();

        listItemDetail2.setWhat(what2);
        listItemDetail2.setName(name2);
        listItemDetail2.setMoney(money2);

        listItems2.add(listItemDetail2);
    }
}
