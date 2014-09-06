/*
 * MyApplication.java
 *
 * Created on 12 Сентябрь 2012 г., 10:25
 */
 
package org.imageservice.wicket;           

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
/** 
 *
 * @author Aidos
 * @version 
 */

public class MyApplication extends WebApplication {

    @Override
    public Class getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        super.init();
        mountPage("/home", HomePage.class);
    }
    
    public static MyApplication get() {
        return (MyApplication)Application.get();
    }

    @Override
    public Session newSession(Request request, Response response) {
        return new AppSession(request);
    }

}
