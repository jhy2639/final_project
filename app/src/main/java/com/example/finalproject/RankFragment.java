package com.example.finalproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class RankFragment extends Fragment {
    private ListView rank_lv;
    private ArrayList<RankVO> data;
    private RankAdapter adapter;
    private String [] rankRegion ={"광산구","북구","서구","남구","동구"};
    private int[] rankImgs = {R.drawable.medal, R.drawable.eunmedal, R.drawable.dongmedal, R.drawable.medal4, R.drawable.medal4};
    private TextView tv_whendate;
    private SimpleDateFormat mFormat = new SimpleDateFormat(("yyyy년 M월 d일")); //날짜포맷맷

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_rank, container, false);

        tv_whendate = fragment.findViewById(R.id.tv_whendate);
        Date date = new Date();
        String time = mFormat.format(date);
        tv_whendate.setText(time); //현재 날짜로 설정

       rank_lv=fragment.findViewById(R.id.rank_lv);

       data= new ArrayList<RankVO>();

       for (int i = 0; i<rankRegion.length; i++){
           int img = rankImgs[i];
           String region = rankRegion[i];
           data.add(new RankVO(img,region,"1234명","99999p"));
       }

       adapter=new RankAdapter(getActivity(),R.layout.rank_list_item,data);

       rank_lv.setAdapter(adapter);

        return fragment;
    }
}