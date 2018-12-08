package com.example.dbconnection.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dbconnection.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {
    private String IP = "61.255.8.214:27922";

    private Button back;
    private Button signIn;
    private Button regis_pic;
    private RadioButton radioButton1, radioButton2;
    private RadioGroup radioGroup;

    private EditText id, pw, name;
    String ID, PW, NAME, SEX = null;

    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "ID";
    JSONArray peoples = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        id = (EditText)findViewById(R.id.newID);
        pw = (EditText)findViewById(R.id.newPW);
        name = (EditText)findViewById(R.id.newNAME);

        radioGroup = (RadioGroup)findViewById(R.id.radioG1);
        radioButton1 = (RadioButton)findViewById(R.id.radioB1);
        radioButton2 = (RadioButton)findViewById(R.id.radioB2);

        back = (Button)findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        regis_pic = (Button)findViewById(R.id.btnRegis);
        regis_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"그림 등록해주기~",Toast.LENGTH_SHORT).show();
            }
        });

        signIn = (Button)findViewById(R.id.btnSignIn);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID = id.getText().toString();
                PW = pw.getText().toString();
                NAME = name.getText().toString();

                if(ID.equals("") || ID.equals(null) || PW.equals("") || PW.equals(null) || NAME.equals("") || NAME.equals(null) || SEX.equals("") || SEX.equals(null))
                {
                    Toast.makeText(getApplicationContext(), "정보를 입력하세요", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    getData("http://" + IP + "/mp/Signin.php?ID=" + ID + "&PW=" + PW + "&NAME=" + NAME + "&SEX=" + SEX); //수정 필요
                }
            }
        });

    }

    public void mOnClick(View v)
    {
        switch (v.getId())
        {
            case R.id.radioB1:
                SEX = "M";
                break;
            case R.id.radioB2:
                SEX = "F";
                break;
        }
    }

    protected void showList()
    {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++)
            {
                JSONObject c = peoples.getJSONObject(i);
                String dbid = c.getString(TAG_ID);

                if(dbid.equals("true"))
                {
                    Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;

                showList();
            }

        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}