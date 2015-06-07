package oshop.services.impl;

import org.springframework.stereotype.Component;
import oshop.services.ImageConverterService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component("imageConverterService")
public class ImageConverterServiceImpl implements ImageConverterService {

    public byte[] deflate(byte[] imageData, int newWidth, String fileType) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
        int width = image.getWidth();

        if (width <= newWidth) {
            return imageData;
        }

        int newHeight = Math.round((1.0f * newWidth / width) * image.getHeight());

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(image, 0, 0, newWidth, newHeight, null);
        graphics.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, fileType, baos);
        return baos.toByteArray();
    }
}
