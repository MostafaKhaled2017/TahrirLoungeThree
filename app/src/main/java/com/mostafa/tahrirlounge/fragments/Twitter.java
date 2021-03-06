package com.mostafa.tahrirlounge.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.mostafa.tahrirlounge.R;

/**
 * Created by Mostafa on 8/24/2017.
 */
public class Twitter extends Fragment {

    WebView webView;
    ProgressDialog progress;
    Home home;
    public void passData(Home home1){home=home1;}
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = (View) inflater.inflate(R.layout.fragment_twitter, container, false);//CHANGE
        webView = (WebView)mainView.findViewById(R.id.webview_twitter);//change
        webView.getSettings().setJavaScriptEnabled(true);
        progress=new ProgressDialog(getActivity()){
            @Override
            public void onBackPressed() {
                progress.dismiss();
                Toast.makeText(getActivity(), "You Canceled Loading Data", Toast.LENGTH_SHORT).show();
                FragmentManager fm = getActivity().getFragmentManager();
                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                    fm.popBackStack();
                }
                getFragmentManager().beginTransaction().replace(R.id.content_frame, home,"home").commit();
            }
        };
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();
        webView.loadUrl("https://twitter.com/tahrirlounge");//CHANGE

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                if (progress != null)
                    progress.dismiss();

            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                progress.setCanceledOnTouchOutside(true);
            }
        });
        webView.setOnKeyListener( new View.OnKeyListener() {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event ) {
                if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                    webView.goBack();
                    return true;
                }

                return false;
            }
        });
        return mainView;
    }
}
