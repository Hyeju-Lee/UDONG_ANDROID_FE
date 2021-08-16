package org.techtown.club;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends BaseAdapter {

    private Context mContext1;
    private ArrayList<String> array_mountain1, array_mountain2, array_mountain3, noduplicate;
    List<Integer> cnt = new ArrayList<>();
    private ViewHolder mViewHolder1;
    int count=0;

    public Adapter(Context mContext1, ArrayList<String> array_mountain1, ArrayList<String> array_mountain2, ArrayList<String> array_mountain3) {
        this.mContext1 = mContext1;

        this.array_mountain1 = array_mountain1;
        this.array_mountain2 = array_mountain2;
        this.array_mountain3 = array_mountain3;

        noduplicate = new ArrayList<>();
        int a = 0;

        
        for(String item: array_mountain1){
            if(!noduplicate.contains(item)) {
                cnt.add(a);
                a = 0;
                noduplicate.add(item);
            }
            else
                a++;
        }
        System.out.println(array_mountain1);
        System.out.println(noduplicate);
        System.out.println(cnt);
    }



    @Override
    public int getCount() {
        return array_mountain1.size();
    }

    @Override
    public Object getItem(int position) {
        return array_mountain1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView1, ViewGroup parent) {
        // ViewHoldr 패턴
        if (convertView1 == null) {
            convertView1 = LayoutInflater.from(mContext1).inflate(R.layout.item_list_detailmoneyactivity, parent, false);
            mViewHolder1 = new ViewHolder(convertView1);
            convertView1.setTag(mViewHolder1);

        } else {
            mViewHolder1 = (ViewHolder) convertView1.getTag();
        }

        // View에 Data 세팅
        System.out.println(cnt.get(0));



            if(cnt.get(count)==0){
                mViewHolder1.txt_item.setText(array_mountain1.get(position));
                count++;

            }else {
                cnt.set(count, cnt.get(count)-1);
                //mViewHolder1.txt_item.
                mViewHolder1.txt_item.findViewById(R.id.txt_item).setVisibility(View.GONE);
            }


        mViewHolder1.txt_name.setText(array_mountain2.get(position));
        mViewHolder1.txt_money.setText(array_mountain3.get(position));

        return convertView1;
    }


    public class ViewHolder {
        private TextView txt_name, txt_item, txt_money;



        public ViewHolder(View convertView) {
            txt_item = (TextView) convertView.findViewById(R.id.txt_item);
            txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            txt_money = (TextView) convertView.findViewById(R.id.txt_money);
        }
    }
}
