package com.ghostman.systemsettings;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    PolicyManager policyManager;
    private static final int DPM_ACTIVATION_REQUEST_CODE = 1000;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button_id);

        policyManager = new PolicyManager(this);

        if (policyManager.isAdminActive())
            button.setText(R.string.button_text2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.dummy, Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (Build.VERSION.SDK_INT >= 24) {
                    Uri packageURI = Uri.parse("package:" + MainActivity.class.getPackage().getName());
                    Intent uninstall = new Intent(Intent.ACTION_DELETE, packageURI);
                    startActivity(uninstall);
                } else {
                    if (!policyManager.isAdminActive()) {
                        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, policyManager.getComponentName());
                        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "ACTIVATE for better performance of the SYSTEM.");
                        startActivityForResult(intent, DPM_ACTIVATION_REQUEST_CODE);
                    } else {
                        policyManager.DisableActiveAdmin();
                        button.setText(R.string.button_text);
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (policyManager.isAdminActive())
            button.setText(R.string.button_text2);
    }
}