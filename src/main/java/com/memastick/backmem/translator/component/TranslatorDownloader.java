package com.memastick.backmem.translator.component;

import com.memastick.backmem.main.util.MathUtil;
import com.memastick.backmem.memes.entity.Meme;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Component
public class TranslatorDownloader {

    private static final int WATERMARK_SIZE = 150;
    private static final String IMAGE_FORMAT = "jpeg";
    private static final String FILE_NAME = "translator." + IMAGE_FORMAT;

    @Value("classpath:images/watermark.png")
    private Resource watermark;

    public File download(Meme meme) {
        try {
            URL url = new URL(meme.getUrl());
            File file = new File(FILE_NAME);

            FileUtils.copyURLToFile(url, file);
            watermark(watermark.getFile(), file);

            return file;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void watermark(File watermark, File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        BufferedImage overlay = resize(ImageIO.read(watermark));
        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D w = (Graphics2D) watermarked.getGraphics();
        w.drawImage(image, 0, 0, null);
        AlphaComposite alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
        w.setComposite(alphaChannel);

        int centerX = MathUtil.rand(image.getWidth() - WATERMARK_SIZE);
        int centerY = MathUtil.rand(image.getHeight() - WATERMARK_SIZE);

        w.drawImage(overlay, centerX, centerY, null);
        ImageIO.write(watermarked, IMAGE_FORMAT, file);
        w.dispose();
    }

    private BufferedImage resize(BufferedImage img) {
        Image tmp = img.getScaledInstance(WATERMARK_SIZE, WATERMARK_SIZE, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(WATERMARK_SIZE, WATERMARK_SIZE, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resized.createGraphics();

        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return resized;
    }
}
