package com.example.dbconnection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class RandomImageActivity extends AppCompatActivity {

    public static final int array[]={
            R.drawable.im1, R.drawable.im2, R.drawable.im3,
            R.drawable.im4, R.drawable.im5, R.drawable.im6,
            R.drawable.im7
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_image);

        Button button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                ImageView test = (ImageView)findViewById(R.id.imageView1);
                //test.setImageResource(array[0]);

                int random = (int)(Math.random()*7);
                test.setImageResource(array[random]);

            }
        });
    }

}
