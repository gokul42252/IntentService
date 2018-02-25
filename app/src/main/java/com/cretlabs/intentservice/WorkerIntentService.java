package com.cretlabs.intentservice;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Gokul on 2/25/2018.
 */

public class WorkerIntentService extends IntentService {
    private static final String TAG = "WorkerIntentService";
    public static final String RECEIVER = "receiver";
    public static final int SHOW_RESULT = 123;
    /**
     * Result receiver object to send results
     */
    private android.support.v4.os.ResultReceiver mResultReceiver;

    /**
     * Actions download
     */
    public static final String ACTION_DOWNLOAD = "action.DOWNLOAD_DATA";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public WorkerIntentService() {
        super(TAG);
    }



    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_DOWNLOAD:
                    mResultReceiver = intent.getParcelableExtra(RECEIVER);
                    for(int i=0;i<10;i++){
                        try {
                            Thread.sleep(1000);
                            Bundle bundle = new Bundle();
                            bundle.putString("data",String.format("Showing From Intent Service %d", i));
                            mResultReceiver.send(SHOW_RESULT, bundle);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }
}
