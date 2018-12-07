package com.example.dbconnection;



import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MailboxActivity extends AppCompatActivity {

    private String IP = "61.255.8.214:27922";
    ListView messages;
    MailboxAdapter mailboxAdapter;
    Intent intent;
    private String cur_ID, cur_SEX;
    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "ASK_ID";
    private static final String TAG_MSG = "MESSAGE";
    JSONArray peoples = null;

    final ArrayList<MailboxMessage> adapter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailbox);

        intent = getIntent();
        cur_ID = intent.getStringExtra("myId");
        cur_SEX = intent.getStringExtra("SEX");

        messages = (ListView)findViewById(R.id.messages);
        getData("http://" + IP + "/mp/lovecall.php?ID=" + cur_ID);

        messages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MailboxActivity.this);
                builder.setTitle("메세지");
                builder.setMessage("당신을 맘에 들어합니다.");
                builder.setPositiveButton("수락",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.setNegativeButton("별로에요",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
            }
        });
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
                String dbmsg = c.getString(TAG_MSG);

                MailboxMessage mm = new MailboxMessage(dbid);
                adapter.add(mm);
                mailboxAdapter = new MailboxAdapter(getApplication(),adapter);
                messages.setAdapter(mailboxAdapter);
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