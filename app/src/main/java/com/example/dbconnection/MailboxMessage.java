package com.example.dbconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MailboxMessage extends AppCompatActivity {
    private String user_name;

    public String getName() {return user_name;}

    public MailboxMessage(String user_name){
        this.user_name = user_name;
    }
}