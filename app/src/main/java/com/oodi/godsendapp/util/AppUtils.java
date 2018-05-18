package com.oodi.godsendapp.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by pc on 6/27/17.
 */

public class AppUtils {

    Activity mContext;
    ProgressDialog dialog;
    public String success = "" ;

    public AppUtils(Activity mContext) {
        this.mContext = mContext;
        dialog  = new ProgressDialog(mContext);
        dialog.setMessage("loading...");
    }

    public boolean isOnLine() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public  void showToast(int msg){

        Toast.makeText(mContext , msg , Toast.LENGTH_LONG).show();

    }

    public void showProgressBarLoading(){

        if (dialog != null){

            if (!dialog.isShowing()) {
                dialog.show();
                dialog.setCancelable(false);
            }
        }
    }

    public void dismissProgressBar(){
        if(dialog!=null)
        {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public void setLocale(String lang) {

        Locale myLocale = new Locale(lang);
        Resources res = mContext.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setStatusBarColor(int color){

        Window window = mContext.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(mContext, color));

    }

}
