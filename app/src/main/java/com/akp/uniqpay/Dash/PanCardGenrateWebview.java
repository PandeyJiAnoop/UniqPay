package com.akp.uniqpay.Dash;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.akp.uniqpay.R;

public class PanCardGenrateWebview extends AppCompatActivity {
    String Getput_encdata,Getput_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_card_genrate_webview);
        Getput_encdata=getIntent().getStringExtra("put_encdata");
        Getput_url=getIntent().getStringExtra("put_url");

        String dataFromAndroid = Getput_encdata; // Replace this with your actual data

        String htmlContent = "<html><body>" +
                "<form id=\"myForm\" method=\"post\" action=" +Getput_url+">"+
                "<textarea name=\"encdata\" style=\"display:none\">" + escapeHtml(dataFromAndroid) + "</textarea>" +
                "<input type=\"submit\" value=\"Submit\">" +
                "</form>" +
                "</body></html>";

        Log.d("response",htmlContent);


        // Load HTML content into a WebView
        WebView webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }

    private String escapeHtml(String input) {
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}