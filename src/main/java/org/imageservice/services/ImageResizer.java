/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.imageservice.services;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Image resizer utility for thumbs
 *
 * @author aishmanov
 */
public class ImageResizer {

    private static final Logger logger = LoggerFactory.getLogger(ImageResizer.class);

    //photo
    public static final int LARGE_WIDTH = 600;
    public static final int LARGE_HEIGHT = 600;
    //thumb
    public static final int SMALL_WIDTH = 200;
    public static final int SMALL_HEIGHT = 200;

    /**
     * Creates thumb for image.
     *
     * @param originalData original image in byte array
     * @param type original - 0, large - 1, small - 2
     * @return resized image in byte array
     */
    public static byte[] resizeImage(byte[] originalData, int type) {
        //if original flag, then return original
        if (type == 0) {
            return originalData;
        }

        BufferedImage originalImage = null;
        BufferedImage resizedImage = null;
        byte[] result = null;
        //convert bytes to BufferedImage
        try (InputStream in = new ByteArrayInputStream(originalData)) {
            originalImage = ImageIO.read(in);
        } catch (IOException ex) {
            logger.error("Cannot convert byte array to BufferedImage!", ex);
        }
        //get original size
        int scaledHeight = originalImage.getHeight();
        int scaledWidth = originalImage.getWidth();

        switch (type) {
            case 1:
                scaledWidth = LARGE_WIDTH;
                scaledHeight = LARGE_HEIGHT;
                break;
            case 2:
                scaledWidth = SMALL_WIDTH;
                scaledHeight = SMALL_HEIGHT;
                break;
            default:
                break;
        }
        //calculate aspect ratio
        float ratio = 1.0F * originalImage.getWidth() / originalImage.getHeight();
        if (ratio > 1.0F) {
            scaledHeight = (int) (scaledHeight / ratio);
        } else {
            scaledWidth = (int) (scaledWidth * ratio);
        }
        //resize with hints
        resizedImage = new BufferedImage(
                scaledWidth,
                scaledHeight,
                originalImage.getType() == 0
                ? BufferedImage.TYPE_INT_ARGB
                : originalImage.getType()
        );

        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //convert BufferedImage to bytes
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(resizedImage, "png", baos);
            baos.flush();
            result = baos.toByteArray();
        } catch (IOException ex) {
            logger.error("Cannot convert BufferedImage to byte array!", ex);
        }
        return result;
    }
}
