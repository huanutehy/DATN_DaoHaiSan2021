package com.example.datn_daohaisan2021.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.datn_daohaisan2021.R;
import com.example.datn_daohaisan2021.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DangKy extends AppCompatActivity {
    EditText TK, MK,  hoten;
    Button bt_register;
    String str_tk, str_mk, str_hoten;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_dang_ky);
        TK = (EditText) findViewById(R.id.email);
        MK = (EditText) findViewById(R.id.password);
        hoten = findViewById(R.id.name);

        bt_register =  findViewById(R.id.btn_DangKyy);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    public void Register() {
        if (TK.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Username", Toast.LENGTH_SHORT).show();
        } else if (MK.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else {

            str_hoten = hoten.getText().toString().trim();
            str_tk = TK.getText().toString().trim();
            str_mk = MK.getText().toString().trim();
            StringRequest request = new StringRequest(Request.Method.POST, Server.REGISTER_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e( "aaaaaaa: ", response.toString());
                    if (response.contains("register")) {
                        Toast.makeText(DangKy.this, "ok", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DangNhap.class));
                    } else if (response.trim().contains("tontai")) {
                        Toast.makeText(DangKy.this, "Tên tài khoản đã được sử dụng !", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DangKy.this, error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("bbbb",error.toString());
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("TaiKhoan", str_tk);
                    params.put("MatKhau", str_mk);
                    params.put("HoTen", str_hoten);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(DangKy.this);
            requestQueue.add(request);
        }

    }


}