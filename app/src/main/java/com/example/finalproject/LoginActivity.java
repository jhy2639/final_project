package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity{

    Button btn_login_login, btn_login_join;
    EditText edit_login_id, edit_login_pw;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_login_id = findViewById(R.id.edit_login_id);
        edit_login_pw = findViewById(R.id.edit_login_pw);
        btn_login_login = findViewById(R.id.btn_login_login);
        btn_login_join = findViewById(R.id.btn_login_join);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        // 회원가입 버튼 클릭 시 회원가입 페이지로 변환
        btn_login_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join_intent = new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(join_intent);
                finish();
            }
        });

        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login_id = edit_login_id.getText().toString();
                String login_pw = edit_login_pw.getText().toString();

                if(login_id.length()==0 || login_pw.length()==0){
                    //아이디와 비밀번호는 필수 입력사항
                    Toast toast = Toast.makeText(LoginActivity.this, " 아이디와 비밀번호는 필수 입력사항 입니다.", Toast.LENGTH_SHORT);
                   toast.show();
                    return;
                }

                String server_url="http://222.102.43.79:8088/AndroidServer/LoginController";

                StringRequest request=new StringRequest(
                        Request.Method.POST,
                        server_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("")){
                                    Log.d("로그인여부",response);
                                    Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
                                } else{
                                    Log.d("로그인여부",response);
                                    Toast.makeText(LoginActivity.this,"환영합니다",Toast.LENGTH_SHORT).show();
                                    Intent login_intent = new Intent(getApplicationContext(), MainActivity.class);
                                    String login_info =response;
                                    login_intent.putExtra("login",login_info);
                                    startActivity(login_intent);
                                    finish();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this,"로그인 실패",Toast.LENGTH_SHORT).show();
                            }
                        }
                ){
                    @Nullable
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("login_id", login_id);
                        params.put("login_pw",login_pw);

                        return params; //★★★★★
                    }
                };
                requestQueue.add(request);
            }
        });
    } }
//        // 로그인 버튼
//        btn_login_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String login_id = edit_login_id.getText().toString();
//                String login_pw = edit_login_pw.getText().toString();
//
//                if(login_id.length()==0 || login_pw.length()==0){
//                    //아이디와 비밀번호는 필수 입력사항
//                    Toast toast = Toast.makeText(LoginActivity.this, " 아이디와 비밀번호는 필수 입력사항 입니다.", Toast.LENGTH_SHORT);
//                    toast.show();
//                    return;
//                }
//
//                String server_url ="http://222.102.43.79:8088/AndroidServer/LoginController";
//
//                StringRequest request = new StringRequest(
//                        Request.Method.POST,
//                        server_url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // response : 성공 시 닉네임값 저장 / 실패 시 0값 저장
//                                Toast.makeText(LoginActivity.this,"환영합니다",Toast.LENGTH_SHORT).show();
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                            }
//                        }
//                ){
//                    @Nullable
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//
//                        params.put("login_id",login_id);
//                        params.put("login_pw",login_pw);
//
//                        return params;
//                    }
//                };
//                requestQueue.add(request);
//
//            }
//        });