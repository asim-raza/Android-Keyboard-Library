package com.contactbackup.cloud.keyboard.keyboard;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

public class AlertDialogActivity extends AppCompatActivity {

    /* renamed from: arabic.AlertDialogActivity$1 */
    class C03841 implements OnClickListener {
        C03841() {
        }

        public void onClick(DialogInterface dialog, int id) {
            AlertDialogActivity.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = getLayoutInflater();
        Builder builder = new Builder(this);
        builder.setMessage("Our keyboard applications do not use any personal data, passwords or any credit card information. Thanks for using our keyboard.").setTitle("Attention!!!").setCancelable(false).setPositiveButton("OK", new C03841());
        builder.create().show();
    }
}
