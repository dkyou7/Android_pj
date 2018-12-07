package com.example.dbconnection;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class FinalMessageActivity extends AppCompatActivity {

    private String IP = "172.30.1.18"; //"61.255.4.91:27922";
    String myJSON;
    JSONArray peoples = null;

    private Button submit;
    private Button back;
    private Button update;
    private TextView final_msg;
    private String myId, partnerId;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_message);
        submit = (Button)findViewById(R.id.btnSubmit);
        back = (Button)findViewById(R.id.btnBack);
        update = (Button)findViewById(R.id.btnUpdate);
        final_msg = (TextView) findViewById(R.id.textFinal);

        String message = "";
        final String cur_SEX;
        Intent intent = getIntent();

        int temp = intent.getIntExtra("increment", 0);
        myId = intent.getStringExtra("myId");
        partnerId = intent.getStringExtra("partnerId");
        cur_SEX = intent.getStringExtra("SEX");

        message += "\n우리 이때 봬요!!\n\n";
        message += "날짜 : " + intent.getStringExtra("date") + "\n";
        message += "시간 : " + intent.getStringExtra("time") + "\n";
        message += "장소 : " + intent.getStringExtra(intent.getStringExtra("first")) + "\n\n";

        if (temp > 0){
            message += "카페 : " + intent.getStringExtra("cafe") +"\n";
        }
        if (temp > 1){
            message += "식사 : " + intent.getStringExtra("meal") + "\n";
        }
        if (temp > 2){
            message += "영화 : " + intent.getStringExtra("movie") + "\n";
        }
        if (temp > 3){
            message += "주점 : " + intent.getStringExtra("pub") + "\n\n";
        }

        final_msg.setText(message);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("http://" + IP + "/mp/Match.php?ASK_ID=" + myId + "&ACK_ID=" + partnerId + "&MESSAGE=" + final_msg.getText().toString()); //수정 필요
                Toast.makeText(getApplicationContext(),"제출 완료되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),RandomMatchActivity.class);
                intent.setAction("FINAL");
                intent.putExtra("ID", myId);
                intent.putExtra("SEX", cur_SEX);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"제출 완료되었습니다.",Toast.LENGTH_SHORT).show();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Toast.makeText(getApplicationContext(), partnerId, Toast.LENGTH_LONG).show();
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

                Toast.makeText(getApplicationContext(), myId + " " + partnerId + " " + final_msg.getText().toString(), Toast.LENGTH_SHORT).show();
                if(dbid.equals("true"))
                {
                    Toast.makeText(getApplicationContext(), "요청 성공", Toast.LENGTH_SHORT).show();
                    break;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "요청 실패", Toast.LENGTH_SHORT).show();
                    break;
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
