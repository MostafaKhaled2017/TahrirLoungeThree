package com.mostafa.tahrirlounge.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.mostafa.tahrirlounge.R;
import com.mostafa.tahrirlounge.activities.MainActivity;


public class FacebookPage extends Fragment {

    public static WebView webView;
    ProgressDialog progress;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = (View) inflater.inflate(R.layout.fragment_facebook_page, container, false);//CHANGE
        WebView webView = (WebView)mainView.findViewById(R.id.webview);//change
        webView.getSettings().setJavaScriptEnabled(true);
       /* progress = ProgressDialog.show(getActivity(), null,
                "Loading ...", true);*/
       progress=new ProgressDialog(getActivity()){
           @Override
           public void onBackPressed() {
               progress.dismiss();
               Toast.makeText(getActivity(), "You Canceled Loading Data", Toast.LENGTH_SHORT).show();
               getFragmentManager().beginTransaction().replace(R.id.content_frame, new Home()).commit();
           }
       };
       progress.setMessage("Loading...");
        progress.show();
        progress.setCancelable(true);
        webView.loadUrl("https://www.facebook.com/TahrirLounge/");//CHANGE
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                if (progress != null)
                    progress.dismiss();
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                progress.setCanceledOnTouchOutside(true);
            }

        });
        if(Build.VERSION.SDK_INT >=19){
            webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        }
        else{
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        }
        return mainView;
    }

}
//TODO Extract resources for remains xml files
// TODO Handle disconnected to internet network
//TODO caching

