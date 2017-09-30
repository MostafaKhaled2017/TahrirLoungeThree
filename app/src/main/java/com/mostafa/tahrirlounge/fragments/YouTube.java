package com.mostafa.tahrirlounge.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mostafa.tahrirlounge.R;


/**
 * Created by Mostafa on 8/24/2017.
 */
public class YouTube extends Fragment {

    WebView webView;
    ProgressDialog progress;
    private Bundle webViewBundle;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView =inflater.inflate(R.layout.fragment_youtube, container, false);
        webView = (WebView)mainView.findViewById(R.id.webview_youtube);
        webView.getSettings().setJavaScriptEnabled(true);
        progress = ProgressDialog.show(getActivity(), null,
                "Loading ..." , true);
        if (webViewBundle != null)
        {
            webView.restoreState(webViewBundle);
        }
        else{
            webView.loadUrl("https://www.youtube.com/user/Tahrirlounge");
        }
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                if (progress != null)
                    progress.dismiss();
            }
        });
        return mainView;
    }
    public void onPause() {
        super.onPause();
        webViewBundle = new Bundle();
        webView.saveState(webViewBundle);
    }}