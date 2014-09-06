/*
 * WicketExamplePage.java
 *
 * Created on 12 Сентябрь 2012 г., 10:25
 */
 
package org.imageservice.wicket;           

import org.apache.wicket.markup.html.WebPage;

/** 
 *
 * @author Aidos
 * @version 
 */

public abstract class BasePage extends WebPage {

    public BasePage() { 
        super(); 
    } 

    public AppSession getAppSession() {
        return (AppSession) getSession();
    }
}
