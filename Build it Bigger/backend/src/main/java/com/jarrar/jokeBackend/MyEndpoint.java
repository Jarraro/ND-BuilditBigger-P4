/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.jarrar.jokeBackend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.jokes.Joker;

import javax.inject.Named;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v2",
        namespace = @ApiNamespace(
                ownerDomain = "jokeBackend.jarrar.com",
                ownerName = "jokeBackend.jarrar.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "getJoke")
    public MyJoke getJoke() {
        Joker joker = new Joker();
        MyJoke response = new MyJoke();
        response.setData(joker.getJoke());
        return response;
    }

}
