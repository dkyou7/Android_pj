package com.example.dbconnection.Fragment;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dbconnection.MailboxAdapter;
import com.example.dbconnection.MailboxMessage;
import com.example.dbconnection.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class YesOrNo extends Fragment {

    private String IP = "192.168.10.150";
    private String cur_ID, cur_SEX, cur_MODE;
    private String myJSON;
    private ListView messages;
    private TextView textView;
    private String date, answer_result;
    private JSONArray peoples = null;
    boolean answer_query;
    MailboxAdapter mailboxAdapter;

    ArrayList<MailboxMessage> adapter;




    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "ASK_ID";
    private static final String TAG_ACK = "ACK_ID";
    private static final String TAG_MSG = "MESSAGE";
    private static final String TAG_ANS = "ANSWER";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup)inflater.inflate(R.layout.activity_yes_or_no,container,false);

        messages = (ListView)v.findViewById(R.id.messages);
        textView = (TextView)v.findViewById(R.id.Title);

        cur_ID = getArguments().getString("myId");
        cur_SEX = getArguments().getString("SEX");
        cur_MODE = getArguments().getString("MODE");

        long NOW = System.currentTimeMillis();
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date mDate = new Date(NOW);
        date = mFormat.format(mDate);
        adapter = new ArrayList<>();



        if(cur_MODE.equals("record"))
        {
            textView.setText("RECORD");
            getData("http://" + IP + "/mp/record.php?ID=" + cur_ID + "&NOW=" + date);
        }

        return v;
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

    protected void showList()
    {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++)
            {
                JSONObject c = peoples.getJSONObject(i);
                String dbid = c.getString(TAG_ID);
                if(answer_query)
                {
                    answer_result = dbid;
                    answer_query = false;
                    break;
                }
                MailboxMessage mm;

                String dback, dbmsg, dbans;
                if(cur_MODE.equals("mail"))
                {
                    dbmsg = c.getString(TAG_MSG);
                    mm = new MailboxMessage(dbid, dbmsg);
                }
                else if(cur_MODE.equals("record"))
                {
                    dback = c.getString(TAG_ACK);
                    dbans = c.getString(TAG_ANS);
                    mm = new MailboxMessage(dbid, dback, dbans);
                }
                else {
                    dback = c.getString(TAG_ACK);
                    dbmsg = c.getString(TAG_MSG);
                    dbans = c.getString(TAG_ANS);
                    mm = new MailboxMessage(dbid, dback, dbmsg, dbans);
                }
                adapter.add(mm);
            }
            mailboxAdapter = new MailboxAdapter(getContext(),adapter);
            messages.setAdapter(mailboxAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
