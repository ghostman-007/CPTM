package com.ghostman.systemsettings;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;

public class PolicyManager {

    private Context context;
    private DevicePolicyManager DPM;
    private ComponentName componentName;

    public PolicyManager(Context context)
    {
        this.context = context;
        DPM = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName =  new ComponentName(context.getPackageName(),context.getPackageName() + ".A_R");
    }

    public boolean isAdminActive()
    {
        return  DPM.isAdminActive(componentName);
    }

    public void DisableActiveAdmin() {
        DPM.removeActiveAdmin(componentName);
    }

    public ComponentName getComponentName()
    {
        return componentName;
    }
}
