package com.example.maxibrowser;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.webkit.URLUtil;

import androidx.annotation.Nullable;

public class BackgroundService extends Service {
    private static final String TAG = "MyService";//teg for log

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {

        Log.i(TAG, "BackgroundService started...");//for debugging
        final ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);//loading the clipboard manager
        final ClipData.Item[] item = new ClipData.Item[1];//intialize ClipDAta array
        ClipboardManager c = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);// getting the object of clipboard service

        //--------------- below code is setting Clipboard listener to check whether the content of clipboard is changed or not-----

        c.addPrimaryClipChangedListener(
                new ClipboardManager.OnPrimaryClipChangedListener() {
                    @Override
                    public void onPrimaryClipChanged() {
                        item[0] = clipboard.getPrimaryClip().getItemAt(0);//getting value from clipboard
                        String url=item[0].getText().toString();//converting to string
                        boolean url_flag = URLUtil.isValidUrl(url);//check whether the string is valid url or not
                        if (item[0].getText() != null && url_flag) {
                            Log.i(TAG, url);//for debuging purpose is used this
                            callBack();//this method will e restart the app when the url is copied

                        }
                    }
                });

        return  START_STICKY;
    }
    public void callBack()
    {
        Intent i = new Intent();//creating new intenet
        i.setAction(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
        i.setComponent(new ComponentName(getApplicationContext().getPackageName(), MainActivity.class.getName()));
        startActivity(i);//restarting the app
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
