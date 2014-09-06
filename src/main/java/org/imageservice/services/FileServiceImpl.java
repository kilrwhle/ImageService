/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imageservice.services;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An implementation of ImageService.
 *
 * @author aishmanov
 */
public class FileServiceImpl implements FileService, Serializable {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public boolean checkOrCreateDestination(String destination) {
        boolean result = true;
        try {
            Files.createDirectory(Paths.get(destination));
        } catch (FileAlreadyExistsException fae) {
            logger.error(destination + " already exists.");
        } catch (IOException ex) {
            logger.error("Error creating directory, path: " + destination, ex);
            result = false;
        }
        return result;
    }

    @Override
    public String getPhotoFilename(Long goodId, String originalFilename, int type) {
        String prefix = "";
        switch (type) {
            case 1:
                prefix = LARGE;
                break;
            case 2:
                prefix = SMALL;
                break;
            default:
                prefix = ORIGINAL;
                break;
        }
        if (originalFilename == null || originalFilename.isEmpty()) {
            originalFilename = "photo";
        }
        return goodId.toString() + "/" + goodId.toString() + "-" + prefix + "-" + originalFilename;
    }

    @Override
    public void saveOriginalPhoto(Long goodId, byte[] data, String filename) {
        //check files/photos/goodId
        if (checkOrCreateDestination(DIR_PHOTOS + goodId.toString())) {
            filename = getPhotoFilename(goodId, filename, 0);
            try {
                Files.write(Paths.get(DIR_PHOTOS + filename), data);
            } catch (IOException ex) {
                logger.error("Saving photo: can't create file: " + filename);
            }
        }
    }

    @Override
    public void savePhotoWithThumbs(Long goodId, byte[] data, String filename) {
        String temp;
        //check files/photos/goodId
        if (checkOrCreateDestination(DIR_PHOTOS + goodId.toString())) {
            temp = getPhotoFilename(goodId, filename, 0);
            try {
                Files.write(Paths.get(DIR_PHOTOS + temp), data);
            } catch (IOException ex) {
                logger.error("Saving photo: can't create file: " + filename);
            }
            temp = getPhotoFilename(goodId, filename, 1);
            byte[] dataLarge = ImageResizer.resizeImage(data, 1);
            //process image
            try {
                Files.write(Paths.get(DIR_PHOTOS + temp), dataLarge);
            } catch (IOException ex) {
                logger.error("Saving photo: can't create file: " + filename);
            }
            temp = getPhotoFilename(goodId, filename, 2);
            byte[] dataSmall = ImageResizer.resizeImage(data, 2);
            //process image
            try {
                Files.write(Paths.get(DIR_PHOTOS + temp), dataSmall);
            } catch (IOException ex) {
                logger.error("Saving photo: can't create file: " + filename);
            }
        }
    }

    @Override
    public void deleteFile(String destination) {
        try {
            Files.delete(Paths.get(destination));
        } catch (NoSuchFileException x) {
            logger.error("File deletion: no such element: " + destination);
        } catch (DirectoryNotEmptyException x) {
            logger.error("File deletion: dir is not empty: " + destination);
        } catch (IOException x) {
            logger.error("File deletion: permission problems: " + destination);
        }
    }

}
