package com.codefury.atmswithcash;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codefury.atmswithcash.utils.Constants;
import com.codefury.atmswithcash.utils.GPSTrackerNew;

/**
 * Created by apoorv on 15-11-2016.
 */
public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME = 2000;
    private Prefs mPrefs;
    private GPSTrackerNew gpsTrackerNew;
    private Dialog dialog;

    public static boolean isGPSEnabled(Context mContext) {
        LocationManager lm = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);

        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mPrefs = Prefs.with(this);
        gpsTrackerNew = new GPSTrackerNew(this);
        mPrefs.save(Constants.ISLOCATIONENABLED, true);
        dialog = new Dialog(SplashActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.popup_gps);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                    dialog.dismiss();
                }
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isGPSEnabled(SplashActivity.this)) {
            mPrefs.save(Constants.ISLOCATIONENABLED, false);
            checkForGPS();
        } else getStartedWithApp();
    }

    public void getStartedWithApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME);
    }

    public void checkForGPS() {
        if (!mPrefs.getBoolean(Constants.ISLOCATIONENABLED, false)) {

            TextView tvEnable = dialog.findViewById(R.id.tvEnable);
            tvEnable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                    dialog.dismiss();
                    startActivity(intent);
                }
            });

            if (!dialog.isShowing()) {
                dialog.show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LocationManager lm = (LocationManager)
                SplashActivity.this.getSystemService(Context.LOCATION_SERVICE);
        if (requestCode == RESULT_OK) {
            if (resultCode == RESULT_OK && lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                getStartedWithApp();
                mPrefs.save(Constants.ISLOCATIONENABLED, true);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getBaseContext(), "No GPS device or service enabled", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        }

    }
}
