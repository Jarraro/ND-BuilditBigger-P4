package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.lang.Override;
import java.lang.Runnable;
import java.lang.Thread;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.jarrar.jokebackend.myApi.MyApi;
import java.io.IOException;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    private MyApi myApiService = null;
    public ApplicationTest() {
        super(Application.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (myApiService == null) {
                    MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                            .setRootUrl("https://2-dot-jarrar-jokes.appspot.com/_ah/api/");
                    myApiService = builder.build();
                }
                try {
                    assertTrue("AsyncTask test passed",myApiService.getJoke().execute().getData()!="");
                } catch (IOException e) {
                    assertFalse("AsyncTask test failed", true);
                }

            }
        }).start();
    }
}