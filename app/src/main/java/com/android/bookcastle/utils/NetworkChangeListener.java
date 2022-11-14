package com.android.bookcastle.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import com.android.bookcastle.R;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Common.isConnectedToInternet(context)){
            final Dialog dialog1 = new Dialog(context, R.style.df_dialog);
            dialog1.setContentView(R.layout.no_internet_dialog);
            dialog1.setCancelable(false);
            dialog1.setCanceledOnTouchOutside(false);
            dialog1.getWindow().setGravity(Gravity.CENTER);
            dialog1.findViewById(R.id.btn_retry).setOnClickListener(v -> {
                    dialog1.dismiss();
                onReceive(context,intent);
            });
            dialog1.findViewById(R.id.settings_tv).setOnClickListener(v -> {
                context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
            });
            dialog1.show();
        }
    }
}