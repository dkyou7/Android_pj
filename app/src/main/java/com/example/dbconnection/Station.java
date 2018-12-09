package com.example.dbconnection;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class Station extends AppCompatActivity {

    private ScheduleManagement scheduleManagement;
    private YesOrNo yesOrNo;
    private RandomMatchActivity randomMatch;
    private MyInformation myInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportFragmentManager().beginTransaction().add(R.id.container,scheduleManagement).commit();

        scheduleManagement = new ScheduleManagement();
        yesOrNo = new YesOrNo();
        randomMatch = new RandomMatchActivity();
        myInformation = new MyInformation();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("매칭"));
        tabs.addTab(tabs.newTab().setText("전적"));
        tabs.addTab(tabs.newTab().setText("일정"));
        tabs.addTab(tabs.newTab().setText("내정보"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if(position ==0){
//                    selected = randomMatch;
                }else if(position==1){
                    selected = yesOrNo;
                }else if(position==2){
                    selected = scheduleManagement;
                }else if(position ==3){
                    selected = myInformation;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container,selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
