package com.udacity.gradle.builditbigger.free;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.jarrar.jokebackend.myApi.MyApi;
import com.udacity.gradle.builditbigger.R;

import java.io.IOException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    Button tellJokeBtn;
    ProgressBar progressBar;
    AdView mAdView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        tellJokeBtn = (Button) root.findViewById(R.id.tell_joke_button);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        tellJokeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new EndpointsAsyncTask().execute();
            }
        });
        mAdView = (AdView) root.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    public class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
        private MyApi myApiService = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            if (myApiService == null) {
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://2-dot-jarrar-jokes.appspot.com/_ah/api/");
                myApiService = builder.build();
            }
            try {
                return myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            LaunchAndroidLibrary(s);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public void LaunchAndroidLibrary(String jokeText) {
        Intent intent = new Intent(getActivity().getApplicationContext(), com.jarrar.jokeactivity.JokeMainActivity.class);
        intent.putExtra("jokeString", jokeText);
        getActivity().startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        mAdView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdView.resume();
    }
}