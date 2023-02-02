package com.maxcodestudio.keyboard.lib.receiver;

import static android.content.Intent.FLAG_RECEIVER_FOREGROUND;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.contactbackup.cloud.keyboard.keyboard.AlertDialogActivity;

public class DialogReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {

        Intent startIntent = new Intent(context, AlertDialogActivity.class);
        startIntent.addFlags(FLAG_RECEIVER_FOREGROUND);
        context.startActivity(startIntent);

    }
}
