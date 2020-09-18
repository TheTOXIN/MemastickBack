package com.memastick.backmem.main.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {

    public static BufferedImage resizeImage(BufferedImage originalImage, int size) {
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();

        if (height == size && width == size) return originalImage;

        BufferedImage resizedImage = new BufferedImage(size, size, originalImage.getType());
        Graphics2D graphics = resizedImage.createGraphics();

        Image scaled = originalImage.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        graphics.drawImage(scaled, 0, 0, null);
        graphics.dispose();

        return resizedImage;
    }

    public static BufferedImage cropImage(BufferedImage originalImage){
        int height = originalImage.getHeight();
        int width = originalImage.getWidth();

        if (height == width) return originalImage;

        int squareSize = height > width ? width : height;

        int xc = width / 2;
        int yc = height / 2;

        return originalImage.getSubimage(
            xc - (squareSize / 2),
            yc - (squareSize / 2),
            squareSize,
            squareSize
        );
    }
}
