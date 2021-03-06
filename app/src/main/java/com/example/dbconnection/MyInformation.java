package com.example.dbconnection;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.dbconnection.Activity.PackageSelectActivity;

public class MyInformation extends Fragment {

    private Button btnUpdateUserInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.activity_my_information,container,false);

        btnUpdateUserInfo = (Button) rootView.findViewById(R.id.btnUpdateInfo);

        btnUpdateUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("내 정보 수정");
                builder.setMessage("1. 데이트 날짜를 정하세요!\n2. 데이트 장소를 정하세요!\n3. 보내기~! 및 기다리기");
                builder.setPositiveButton("시작하자",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getContext(),PackageSelectActivity.class);
                                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP));
                            }
                        });
                builder.setNegativeButton("다시생각해볼게요",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
            }
        });
        return rootView;

    }
}
