package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyPageFragment extends Fragment {

    public static MyPageFragment newInstance(){
        return new MyPageFragment();
    }


    TextView tv_mypage_id, tv_mypage_point, tv_update_reg;
    EditText edit_mypage_pw, edit_mypage_phone, edit_mypage_region;
    Button btn_mypage_point, btn_mypage_update;
    String loginId, loginPoint, loginPw,loginPhone,loginRegion;
    ImageView img_mypage_logout;
    Intent point_intent;
    RequestQueue requestQueue;
    Context activity;

    String loginReg;
    String region = "";
    Spinner spinner_update_region;
    ArrayList<String> regions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragment = inflater.inflate(R.layout.fragment_my_page, container, false);

        activity = container.getContext();
        tv_mypage_id = fragment.findViewById(R.id.tv_mypage_id);
        tv_mypage_point = fragment.findViewById(R.id.tv_mypage_point);
        tv_update_reg = fragment.findViewById(R.id.tv_update_reg);
        edit_mypage_pw = fragment.findViewById(R.id.edit_mypage_pw);
        edit_mypage_phone = fragment.findViewById(R.id.edit_mypage_phone);

        spinner_update_region=fragment.findViewById(R.id.spinner_update_region);

        //?????? ?????? ?????????
        regions = new ArrayList<>();
        regions.add("????????? ???????????????");
        regions.add("?????????");
        regions.add("??????");
        regions.add("??????");
        regions.add("??????");
        regions.add("??????");

        btn_mypage_point = fragment.findViewById(R.id.btn_mypage_point);
        btn_mypage_update = fragment.findViewById(R.id.btn_mypage_update);
        img_mypage_logout = fragment.findViewById(R.id.img_mypage_logout);

        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.start();

        Bundle extra = getArguments();
        if(extra != null){
            // ?????? ?????????, ?????????
            loginId = extra.getString("loginId");
            login_reset();
            
            // ?????? ??????, ??????, ??????
            String loginPw = extra.getString("loginPw");
            String loginPhone = extra.getString("loginPhone");
            loginReg = extra.getString("loginRegion");

            tv_mypage_id.setText(loginId);
            tv_mypage_point.setText(loginPoint);

            edit_mypage_pw.setText(loginPw);
            edit_mypage_phone.setText(loginPhone);

        }



        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,regions);
        spinner_update_region.setAdapter(arrayAdapter);

        switch (loginReg) {
            case "?????????":  spinner_update_region.setSelection(1);
                break;
            case "??????":  spinner_update_region.setSelection(2);
                break;
            case "??????":  spinner_update_region.setSelection(3);
                break;
            case "??????":  spinner_update_region.setSelection(4);
                break;
            case "??????":  spinner_update_region.setSelection(5);
                break;
        }
        spinner_update_region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                region = parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_mypage_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String UpdateId = loginId;
                String UpdatePw = edit_mypage_pw.getText().toString();
                String UpdateRegion = spinner_update_region.getSelectedItem().toString();
                String UpdatePhone = edit_mypage_phone.getText().toString();

                String server_url="http://222.102.43.79:8088/AndroidServer/UpdateController";

                StringRequest request = new StringRequest(
                        Request.Method.POST,
                        server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // ???????????? ?????? ????????? response ????????? "1"?????? ????????? ????????? "0"?????? ?????????

                                String success = "???????????? ?????? ??????! ???????????? ????????????.";
                                String fail = "???????????? ?????? ??????";

                                if(response.equals("1")){
                                    Toast.makeText(activity, success , Toast.LENGTH_SHORT).show();
                                    Log.d("???????????? ??????","??????");
                                    Intent login_intent = new Intent(getActivity(),LoginActivity.class);
                                    startActivity(login_intent);
                                    getActivity().finish();

                                }else if(response.equals("0")){
                                    Toast.makeText(activity, fail , Toast.LENGTH_SHORT).show();
                                    Log.d("???????????? ??????","??????");
                                }
                            }

                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }

                ){
                    @Nullable
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("login_id", UpdateId);
                        params.put("login_pw",UpdatePw);
                        params.put("login_region",UpdateRegion);
                        params.put("login_phone",UpdatePhone);

                        return params; //???????????????
                    }
                };
                requestQueue.add(request);
            }
        });


        // ????????? ???????????? ?????? ??? ????????? ?????????????????? ?????????
        btn_mypage_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bundle bundle = new Bundle(2);
//                bundle.putString("loginId", loginId);
//                bundle.putString("loginPoint", loginPoint);
//
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                PointFragment pointFragment = new PointFragment();
//                fragmentTransaction.replace(R.id.frame, pointFragment);
//                fragmentTransaction.commit();
               MainActivity.executeMove(R.id.item_point);
                PointFragment pointFragment = new PointFragment();
                executeFragment(pointFragment);
            }
        });

        //????????????
        img_mypage_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"???????????? ???????????????..",Toast.LENGTH_SHORT).show();
                Intent login_intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(login_intent);
            }
        });

        return fragment;

    }

    private void executeFragment(PointFragment pointFragment) {
    }




    public void login_reset(){

        point_intent = getActivity().getIntent();

        //??????????????? ????????????

        loginId = point_intent.getStringExtra("loginId");  //??????????????? ????????????
        loginPw = point_intent.getStringExtra("loginPw");
        Log.d("??????????????? ",loginId);

        String server_url="http://222.102.43.79:8088/AndroidServer/LoginController";

        StringRequest request2 = new StringRequest(
                Request.Method.POST,
                server_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String loginId=null;
                        String loginPw=null;
                        String loginPhone=null;
                        String loginRegion=null;
                        int loginPoint = 0;

                        try {
                            JSONArray loginInfos = new JSONArray(response);


                            JSONObject loginInfo = (JSONObject)loginInfos.get(0);
                            loginId = loginInfo.getString("member_id");
                            loginPw = loginInfo.getString("member_pw");
                            loginPhone = loginInfo.getString("member_phone");
                            loginRegion = loginInfo.getString("member_region");
                            loginPoint = loginInfo.getInt("member_point");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if(response.equals("")){
                            Log.d("???????????????",response);
                        } else{
                            Log.d("???????????????",loginId);
                            Log.d("???????????????", String.valueOf(loginPoint));
                            //Toast.makeText(PopupActivity.this,"???????????????^^",Toast.LENGTH_SHORT).show();

                            tv_mypage_point.setText(loginPoint + "P");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(PopupActivity.this,"????????? ??????",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("login_id", loginId);
                params.put("login_pw", loginPw);

                return params; //???????????????
            }
        };
        requestQueue.add(request2);

    }
}
