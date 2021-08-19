package org.techtown.club.receipt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.techtown.club.R;

import java.util.ArrayList;

public class ListViewDetailAdapter_MoneyAdd1 extends BaseAdapter {

    private Context mContext;
    public static ArrayList<ListItemDetail> listItems = new ArrayList<ListItemDetail>();

    public ListViewDetailAdapter_MoneyAdd1(Context context){
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
            convertView = inflater.inflate(R.layout.item_list_moneyadd, parent, false);
        }

        // item.xml 의 참조 획득
        TextView what_listitem = (TextView)convertView.findViewById(R.id.what_listitem);
        TextView name_listitem = (TextView)convertView.findViewById(R.id.name_listitem);
        TextView money_listitem = (TextView)convertView.findViewById(R.id.money_listitem);
        Button btn_delete = (Button)convertView.findViewById(R.id.btn_delete);

        ListItemDetail listItemDetail = listItems.get(position);

        // 가져온 데이터를 텍스트뷰에 입력
        what_listitem.setText(listItemDetail.getWhat());
        name_listitem.setText(listItemDetail.getName());
        money_listitem.setText(listItemDetail.getMoney());

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

    public void addItem(String what, String name, String money){
        ListItemDetail listItemDetail = new ListItemDetail();

        listItemDetail.setWhat(what);
        listItemDetail.setName(name);
        listItemDetail.setMoney(money);

        listItems.add(listItemDetail);
    }

    public void clear() {
        listItems.clear();
    }
}