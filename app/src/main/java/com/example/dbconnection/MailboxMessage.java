package com.example.dbconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MailboxMessage extends AppCompatActivity {
    private String user_name;
    private String ADD;

    public String getName() {return user_name;}
    public String getADD() {return ADD;}

    public MailboxMessage(String user_name, String add){
        this.user_name = user_name;
        this.ADD = add;
    }
}