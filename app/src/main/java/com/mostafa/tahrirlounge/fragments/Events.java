package com.mostafa.tahrirlounge.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mostafa.tahrirlounge.pojoClasses.EventPojoClass;
import com.mostafa.tahrirlounge.adapters.EventsAdapter;
import com.mostafa.tahrirlounge.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mostafa on 8/28/2017.
 */

public class Events extends Fragment  {
    List<EventPojoClass> eventsList = new ArrayList<>();
    View myView;
    CardView mCardView;
    RecyclerView eventsRecyclerView;
    RequestQueue requestQueue;
    private Context mContext;
    ProgressBar progress;
    Home home;
    // ProgressDialog progressDialog;
   // private SwipeRefreshLayout swipeRefreshLayout;
    private String mJSONURLString = "http://tahrirlounge.net/event/api/events";//TODO : make class for urls

    public Events() {
    }
    public void passData(Home home1){home=home1;}
//TODO :optimize code
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_events, container, false);
        eventsRecyclerView = (RecyclerView) myView.findViewById(R.id.events_recycler_view);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progress = (ProgressBar) myView.findViewById(R.id.progressBar_of_events);
        mCardView = (CardView) myView.findViewById(R.id.card_view_of_events);
        // swipeRefreshLayout =(SwipeRefreshLayout) myView.findViewById(R.id.swipe_to_refresh_of_events);
        //swipeRefreshLayout.setOnRefreshListener(this);
        // Get the application context
        mContext = getActivity().getApplicationContext();
        // Initialize a new RequestQueue instance
        requestQueue = Volley.newRequestQueue(mContext);
        // Initialize a new JsonArrayRequest instance
        if(eventsList.size()==0) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    mJSONURLString,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if(eventsList!=null)
                                eventsList.clear();
                            try {
                                // Loop through the array elements
                                for (int i = 0; i < response.length(); i++) {
                                    // Get current json object
                                    JSONObject eventObject = response.getJSONObject(i);

                                    String eventName = eventObject.getString("eventName");
                                    String instractorName = eventObject.getString("eventInstractor");
                                    String eventDetails = eventObject.getString("eventDetails");
                                    String eventDate = eventObject.getString("eventDate");
                                    String eventImage = eventObject.getString("eventImage");
                                    //POJO class to store
                                    EventPojoClass event = new EventPojoClass();
                                    event.setEventName(eventName);
                                    event.setEventDate(eventDate);
                                    event.setEventDetails(eventDetails);
                                    event.setEventImage(eventImage);
                                    event.setEventInstractor(instractorName);
                                    eventsList.add(event);
                                }
                                EventsAdapter adapter = new EventsAdapter(getActivity(), eventsList);
                                eventsRecyclerView.setAdapter(adapter);
                                if (progress != null)
                                    progress.setVisibility(View.GONE);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (progress != null)
                                    progress.setVisibility(View.GONE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (progress != null)
                                progress.setVisibility(View.GONE);
                            String message = "check your connection and try again";
                             if (error instanceof ServerError) {
                                message = "The server could not be found. Please try again after some time!!";
                            } else if (error instanceof ParseError) {
                                message = "Parsing error! Please try again after some time!!";
                            } else if (error instanceof TimeoutError) {
                                message = "Connection TimeOut! Please check your internet connection.";
                            }
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            FragmentManager fm = getActivity().getFragmentManager();
                                for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                fm.popBackStack();
                            }
                            getFragmentManager().beginTransaction().replace(R.id.content_frame, home,"home").commit();
                        }
                    }
            );
            requestQueue.add(jsonArrayRequest).setTag("tag");
        }
        else{
        EventsAdapter adapter = new EventsAdapter(getActivity(), eventsList);
        eventsRecyclerView.setAdapter(adapter);
            if (progress != null)
                progress.setVisibility(View.GONE);
        }
        return myView;
    }

    @Override
    public void onPause() {
        super.onPause();
        //TODO: find another way to solve the crash in the 4 volley requests
        if(eventsRecyclerView.getAdapter()==null) requestQueue.cancelAll("tag");

    //TODO :load data on create of the application on welcome screen activity
}}

   /* @Override
    public void onRefresh() {
        progressDialog = ProgressDialog.show(getActivity(), null,
                "Loading ...", true);
        eventsList = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                mJSONURLString,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject eventObject = response.getJSONObject(i);

                                String eventName = eventObject.getString("eventName");
                                String instractorName = eventObject.getString("eventInstractor");
                                String eventDetails = eventObject.getString("eventDetails");
                                String eventDate = eventObject.getString("eventDate");
                                String eventImage = eventObject.getString("eventImage");
                                //POJO class to store
                                EventPojoClass event = new EventPojoClass();
                                event.setEventName(eventName);
                                event.setEventDate(eventDate);
                                event.setEventDetails(eventDetails);
                                event.setEventImage(eventImage);
                                event.setEventInstractor(instractorName);
                                eventsList.add(event);
                            }
                            EventsAdapter adapter = new EventsAdapter(getActivity(), eventsList);
                            eventsRecyclerView.setAdapter(adapter);
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (progressDialog != null)
                                progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        Toast.makeText(mContext, "failed to refresh", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
     if(swipeRefreshLayout.isRefreshing())
         swipeRefreshLayout.setRefreshing(false);
    }*/