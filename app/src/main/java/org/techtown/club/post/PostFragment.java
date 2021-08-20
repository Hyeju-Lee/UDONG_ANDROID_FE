package org.techtown.club.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.club.PreferenceManager;
import org.techtown.club.R;
import org.techtown.club.retrofit.RetrofitClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostFragment extends Fragment {
    private View view;
    Button groupAddButton;
    ListView listView2;
    Context mContext;
    List<Integer> teamNumbers;
    HashMap<Integer, ArrayList<String>> hashMap;
    ArrayList<String> forHash;
    ArrayList<ListItemDetail_Frag3> listViewData2;
    ListAdapter customAdapter2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post,container, false);

        /*for (int key : hashMap.keySet()) {
            String team = key+"조";
            String teamPeople = "";
            ArrayList<String> value = hashMap.get(key);
            for (int i = 0 ; i < value.size(); i++) {
                teamPeople += value.get(i) + " ";
            }
            Log.d("화기",team+teamPeople);
            ListItemDetail_Frag3 listData2 = new ListItemDetail_Frag3(team,
                    teamPeople);

            listViewData2.add(listData2);
        }*/
        /*String[][] item = {{"1조", "김수경 이혜주 김예나"}, {"2조", "김수경 이혜주 김예나"},
                {"3조", "김수경 이혜주 김예나"}};


        for (int i = 0; i < item.length; i++) {
            ListItemDetail_Frag3 listData2 = new ListItemDetail_Frag3(item[i][0],
                    item[i][1]);

            listViewData2.add(listData2);
        }*/




        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        teamNumbers = new ArrayList<>();
        hashMap = new HashMap<>();
        mContext = getActivity();
        forHash = new ArrayList<>();
        listView2 = (ListView)view.findViewById(R.id.listView2);
        listViewData2 = new ArrayList<>();
        getTeam();
        testHash();
        groupAddButton = view.findViewById(R.id.groupAddButton);
        groupAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), groupAdd.class);
                startActivity(intent);
            }

        });
    }

    public void testHash() {
        String[][] name = {{"5", "이혜주"}, {"2","이나"}, {"3", "이가"}, {"1", "이다"},{"2","이라"},{"1","이마"}};
        HashMap<String, ArrayList<String>> hashMap = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            ArrayList<String> newAr = new ArrayList<>();
            String key = name[i][0];
            String na = name[i][1];
            if (hashMap.containsKey(key)) {
                names = hashMap.get(key);
                names.add(na);
                hashMap.put(key, names);
            }
            else {
                newAr.add(na);
                hashMap.put(key, newAr);
            }
        }
        //List<String> keySet = new ArrayList<>(hashMap.keySet());
        //Collections.sort(keySet);
        for(String key : hashMap.keySet()){

            ArrayList<String> value = hashMap.get(key);
            for (int i = 0 ; i < value.size(); i++) {
                System.out.println(key+" : "+value.get(i));
            }
        }
    }

    public void getTeam() {
        Long clubId = PreferenceManager.getLong(mContext, "clubId");
        Call<ResponseBody> call = RetrofitClient.getApiService().getTeam(clubId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결 비정상 get team","error code"+response.code());
                    return;
                }
                try {
                    String result = response.body().string();
                    Log.d("연결 완료 get team",result);
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        ArrayList<String> names = new ArrayList<>();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int teamNumber = jsonObject.getInt("teamNumber");
                        JSONObject object = jsonObject.getJSONObject("user");
                        String name = object.getString("name");
                        teamNumbers.add(teamNumber);
                        if (hashMap.containsKey(teamNumber)) {
                            forHash = hashMap.get(teamNumber);
                            forHash.add(name);
                            hashMap.put(teamNumber, forHash);
                        }
                        else {
                            names.add(name);
                            hashMap.put(teamNumber, names);
                        }
                        Log.d("get team 확인",teamNumber+name);
                    }
                    Collections.sort(teamNumbers);
                    List<Integer> keySet = new ArrayList<>(hashMap.keySet());
                    Collections.sort(keySet);
                    for (int key : hashMap.keySet()) {
                        String team = key+"조";
                        String teamPeople = "";
                        ArrayList<String> value = hashMap.get(key);
                        for (int i = 0 ; i < value.size(); i++) {
                            teamPeople += value.get(i) + " ";
                        }
                        Log.d("확인",team+teamPeople);
                        ListItemDetail_Frag3 listData2 = new ListItemDetail_Frag3(team,
                                teamPeople);

                        listViewData2.add(listData2);
                    }
                    customAdapter2 = new GroupListAdapter_Frag3(getContext(),listViewData2);
                    listView2.setAdapter(customAdapter2);
                    listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String click = listViewData2.get(position).groupTittle;
                            int t = click.charAt(0) - '0';
                            PreferenceManager.setInt(mContext, "teamNumber", t);
                            Intent intent= new Intent(getActivity(), GroupActivity.class);
                            startActivity(intent);
                        }
                    });

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("연결 실패 get team", t.getMessage());
            }
        });
    }
}