package com.mostafa.tahrirlounge.fragments;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mostafa.tahrirlounge.R;


public class AboutUs extends Fragment {
    View myView;
    TextView mTextView;
    //ProgressDialog mprogress;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView=inflater.inflate(R.layout.fragment_about_us,container,false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
       // mprogress=ProgressDialog.show(getActivity(),"Loading","Please wait...",true);
     //   myView.getRootView().setBackgroundColor(Color.rgb(214, 255,254));
     //   myView.getRootView().setBackgroundColor(Color.rgb(244,227,228));
        mTextView= (TextView) myView.findViewById(R.id.about_us_text_view);
        Typeface custom_font = Typeface.createFromAsset(getActivity().getAssets(), "fonts/JF Flat regular.otf");
        mTextView.setTypeface(custom_font);
        return myView;
    }
}

