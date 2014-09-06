/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imageservice.wicket;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.imageservice.services.ImageEntry;

/**
 *
 * @author aidos
 */
public class AppSession extends WebSession {

    private List<ImageEntry> images = new ArrayList<ImageEntry>();

    protected AppSession(Request request) {
        super(request);
    }

    public List<ImageEntry> getImages() {
        return images;
    }

    public void setImages(List<ImageEntry> images) {
        this.images = images;
    }

}
