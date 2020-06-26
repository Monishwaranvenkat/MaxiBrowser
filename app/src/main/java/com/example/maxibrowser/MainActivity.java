package com.example.maxibrowser;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView browser;
    private ImageButton search_btn;
    private ImageButton back_btn;
    private EditText text_url;


    private   String s_url = "https://www.google.com";
    String TAG="Mytag";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setS_url();//to get the url
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        browser=(WebView)findViewById(R.id.webview);//getting the webview id
        browser.setWebViewClient(new MyWebViewClient());//creating webview
        browser.getSettings().setLoadsImagesAutomatically(true);//to load the images in web view
        browser.getSettings().setJavaScriptEnabled(true);//to enable javascript in the browser
        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        browser.canGoBack();
        browser.loadUrl(s_url);//to load the url

        search_btn=(ImageButton) findViewById(R.id.search_btn);
        back_btn=(ImageButton) findViewById(R.id.return_btn);
        text_url=(EditText)findViewById(R.id.url);
        //listener for search button
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = text_url.getText().toString();
                browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                browser.loadUrl(url);
            }
        });
        //Listener for back button
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browser.goBack();
            }
        });

        Intent intent = new Intent(MainActivity.this, BackgroundService.class);// creating instace for intent
        startService(intent);//starting the service class
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("destroy");//for debuging purpose
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
