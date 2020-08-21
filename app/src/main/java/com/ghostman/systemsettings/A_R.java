package com.ghostman.systemsettings;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;

public class A_R extends DeviceAdminReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        super.onDisabled(context, intent);
    }
}
