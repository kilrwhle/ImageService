/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imageservice.services;

/**
 * Service for operating with files and especially photos.
 *
 * @author aishmanov
 */
public interface FileService {
    
    public static final String DIR_PHOTOS = "/home/kilrwhle/Desktop/files/";
    //prefixes
    public static final String ORIGINAL = "original";
    public static final String LARGE = "large";
    public static final String SMALL = "small";
            
    /**
     * Checks if directory exists, creates if not.
     * 
     * @param destination
     * @return created or failed
     */
    public boolean checkOrCreateDestination(String destination);
    
    /**
     * Creates a folder + filename for a photo of a good.
     * Syntax: goodId/goodId-original-filename
     * 
     * @param goodId
     * @param originalFilename
     * @param type original - 0, large - 1, small - 2
     * @return 
     */
    public String getPhotoFilename(Long goodId, String originalFilename, int type);
    
    /**
     * Saves original photo file to DIR_PHOTOS
     * 
     * @param goodId
     * @param data
     * @param filename 
     */
    public void saveOriginalPhoto(Long goodId, byte[] data, String filename);
    
    /**
     * Creates thumbs and saves to DIR_PHOTOS
     * 
     * @param goodId
     * @param data
     * @param filename 
     */
    public void savePhotoWithThumbs(Long goodId, byte[] data, String filename);
    
    /**
     * Deletes file/folder in specified destination
     * 
     * @param destination 
     */
    public void deleteFile(String destination);
    
}
