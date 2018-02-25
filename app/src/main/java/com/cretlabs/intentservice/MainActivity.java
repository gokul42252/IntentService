package com.cretlabs.intentservice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static com.cretlabs.intentservice.WorkerIntentService.ACTION_DOWNLOAD;
import static com.cretlabs.intentservice.WorkerIntentService.RECEIVER;
import static com.cretlabs.intentservice.WorkerIntentService.SHOW_RESULT;

public class MainActivity extends AppCompatActivity implements ResultReceiver.Receiver {
    ResultReceiver mResultReceiver;
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultReceiver = new ResultReceiver(new Handler());
        mResultReceiver.setReceiver(this);
        mTextView = findViewById(R.id.tv_data);
        showDataFromBackground(MainActivity.this, mResultReceiver);
    }

    private void showDataFromBackground(Context context , ResultReceiver mResultReceiver) {
            Intent intent = new Intent( context, WorkerIntentService.class);
            intent.putExtra(RECEIVER, mResultReceiver);
            intent.setAction(ACTION_DOWNLOAD);
            startService(intent);
    }

    public void showData(String data) {
        mTextView.setText(String.format("%s\n%s", mTextView.getText(), data));
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case SHOW_RESULT:
                if (resultData != null) {
                    showData(resultData.getString("data"));
                }
                break;
        }
    }
}
