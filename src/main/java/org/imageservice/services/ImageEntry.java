/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imageservice.services;

import java.io.Serializable;

/**
 *
 * @author aishmanov
 */
public class ImageEntry implements Serializable {
    
    private String name;
    private String contentType;
    private String thumbName;
    private byte[] imageBody;

    public ImageEntry(String name, String contentType, byte[] imageBody) {
        this.name = name;
        this.contentType = contentType;
        this.imageBody = imageBody;
    }

    public ImageEntry(String name, String contentType, String thumbName, byte[] imageBody) {
        this.name = name;
        this.contentType = contentType;
        this.thumbName = thumbName;
        this.imageBody = imageBody;
    }

    public byte[] getImageBody() {
        return imageBody;
    }

    public void setImageBody(byte[] imageBody) {
        this.imageBody = imageBody;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getThumbName() {
        return thumbName;
    }

    public void setThumbName(String thumbName) {
        this.thumbName = thumbName;
    }
    
}
