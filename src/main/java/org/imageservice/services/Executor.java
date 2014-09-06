/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imageservice.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aishmanov
 */
public class Executor {
    public static void main(String args[]) throws IOException {
        FileService service = new FileServiceImpl();
        try {
            Path path = Paths.get("mkyong.jpg");
            byte[] data = Files.readAllBytes(path);
            service.savePhotoWithThumbs(126L, data, path.getFileName().toString());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Executor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
