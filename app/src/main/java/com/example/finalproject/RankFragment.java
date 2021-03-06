package com.example.finalproject;


import android.os.Bundle;
import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class RankFragment extends Fragment {
    private ListView rank_lv;
    private ArrayList<RankVO> data;
    private RankAdapter adapter;
    private String [] rankRegion ={"광산구","북구","서구","남구","동구"};
    private int[] img_medal = {R.drawable.medal, R.drawable.eunmedal, R.drawable.dongmedal, R.drawable.medal4, R.drawable.medal4, R.drawable.medal4, R.drawable.medal4, R.drawable.medal4};
    private TextView tv_whendate;
    private SimpleDateFormat mFormat = new SimpleDateFormat(("yyyy년 M월 d일")); //날짜포맷
    private RequestQueue requestQueue;
    String loginId,loginPw;
    Intent rank_intent;
   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_rank, container, false);



        // 생성자 초기화
        rank_lv = fragment.findViewById(R.id.rank_lv);
        data = new ArrayList<RankVO>();
        tv_whendate = fragment.findViewById(R.id.tv_whendate);

        //현재날짜 가져오기
        Date date = new Date();
        String time = mFormat.format(date);
        tv_whendate.setText(time); //현재 날짜로 설정

       adapter=new RankAdapter(getActivity(),R.layout.rank_list_item,data);
       rank_lv.setAdapter(adapter);

       //요청을 보내주는 requestQueue 객체 생성
       requestQueue = Volley.newRequestQueue(getActivity());
       requestQueue.start();

       rank_lv = fragment.findViewById(R.id.point_lv);



       String server_url="http://222.102.43.79:8088/AndroidServer/RankController";

       StringRequest request = new StringRequest(
               Request.Method.POST,
               server_url,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       Log.d("네트워크통신",response);
//                       JSONObject jsonObject = null;
                       try {
                           // 각 구별 랭킹데이터 보여주기
                           JSONArray rankRegion = new JSONArray(response);

                           for (int i = 0; i < rankRegion.length(); i++) {

                               JSONObject rank = (JSONObject)rankRegion.get(i);

//                               Log.d("volly",rank.getString("region"));

                               RankVO vo = new RankVO(
                                       img_medal[i],   //이미지 추가
                                       rank.getString("region"),
                                       rank.getString("totalPoint")+"P",
                                       rank.getString("countId")+"명"
                               );
                               data.add(vo);
                               if(data != null){
                                   Log.d("data", String.valueOf(data));

                               }else{
                                   Log.d("data","뭘까");
                               }
                           }

                           adapter.notifyDataSetChanged(); // 어댑터 갱신

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {

                   }
               });
       //요청보내기
       requestQueue.add(request);
       return fragment;

   }


}
