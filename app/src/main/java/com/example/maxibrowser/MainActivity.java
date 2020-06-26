package com.example.maxibrowser;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView wv1;
    private Button search_btn;
    private EditText text_btn;


    private   String s_url = "https://www.google.com";
    String TAG="Mytag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setS_url();//to get the url
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wv1=(WebView)findViewById(R.id.webview);//geting the webview id
        wv1.setWebViewClient(new MyWebViewClient());//creating webview
        wv1.getSettings().setLoadsImagesAutomatically(true);//to load the images in web view
        wv1.getSettings().setJavaScriptEnabled(true);//to enable javascript in the browser
        wv1.loadUrl(s_url);//to load the url
        Intent intent = new Intent(MainActivity.this, BackgroundService.class);// creating instace for intent
        startService(intent);//starting the service class
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("destory");//for debuging purpose
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(s_url);
            return true;
        }
    }
    //----setS url method --> it will set s_url variable with the link if it is present in the clipboard
    public void setS_url()
    {
        final ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        final ClipData.Item[] item = new ClipData.Item[1];
        ClipboardManager c = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        item[0] = clipboard.getPrimaryClip().getItemAt(0);
        String url=item[0].getText().toString();
        boolean url_flag = URLUtil.isValidUrl(url);
        if (item[0].getText() != null && url_flag)
            s_url=url;

    }

}
