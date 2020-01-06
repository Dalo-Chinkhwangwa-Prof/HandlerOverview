package com.illicitintelligence.multithreadingandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    @BindView(R.id.main_textview)
    TextView mainTextView;

    @BindView(R.id.splash_texview)
    TextView splashTextView;

    Handler handler = new Handler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        final Message message = new Message();
        Bundle messageBundle = new Bundle();
        messageBundle.putString("key", "Hello there. . . . .");
        message.setData(messageBundle);



        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                splashTextView.setVisibility(View.GONE);
                handler.sendMessage(message);
            }
        }, 3000);

        mainTextView.setText("Hello MainThread!");

        Thread newThread = new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    for (int i = 6; i >= 1; i--) {
                        Thread.sleep(1000);
                        final int finalI = i;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mainTextView.setText(finalI + " Seconds...");
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        newThread.start();
    }


    @Override
    public boolean handleMessage(@NonNull Message msg) {

        String messageTxt = msg.getData().getString("key");
        Toast.makeText(this, messageTxt, Toast.LENGTH_SHORT).show();

        return true;
    }
}
