package com.memastick.backmem.person.service;

import com.memastick.backmem.errors.exception.MemetickAvatarException;
import com.memastick.backmem.main.util.ImageUtil;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.person.repository.MemetickRepository;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;


@Service
public class MemetickAvatarService {

    private final static long MIN_SIZE_BYTE = (long) Math.pow(2, 10);
    private final static long MAX_SIZE_BYTE = (long) Math.pow(2, 20);

    private final static int PHOTO_SIZE_PIX = 128;

    private final static String FORMAT = "png";

    private final MemetickRepository memetickRepository;
    private final MemetickService memetickService;
    private final SecurityService securityService;

    @Autowired
    public MemetickAvatarService(
        MemetickRepository memetickRepository,
        MemetickService memetickService,
        SecurityService securityService
    ) {
        this.memetickRepository = memetickRepository;
        this.memetickService = memetickService;
        this.securityService = securityService;
    }

    @Transactional
    public byte[] download(UUID memetickId) {
        return memetickService.findById(memetickId).getAvatar();
    }

    @Transactional
    public void upload(MultipartFile image) throws IOException {
        validateImage(image);

        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());

        byte[] photoBytes = optimizeImage(bufferedImage);

        Memetick memetick = securityService.getCurrentUser().getMemetick();
        memetick.setAvatar(photoBytes);
        memetickRepository.save(memetick);
    }

    private void validateImage(MultipartFile image) {
        if (image == null || image.getContentType() == null) {
            throw new MemetickAvatarException(
                "Image == NULL"
            );
        }
        if (!image.getContentType().split("/")[0].equals(FORMAT)) {
            throw new MemetickAvatarException(
                "Only PNG format"
            );
        }

        if (image.getSize() <= MIN_SIZE_BYTE || image.getSize() >= MAX_SIZE_BYTE) {
            throw new MemetickAvatarException(
                "Size: max = 1mb, min = 1kb"
            );
        }
    }

    private byte[] optimizeImage(BufferedImage image) throws IOException {
        image = ImageUtil.cropImage(image);
        image = ImageUtil.resizeImage(image, PHOTO_SIZE_PIX);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, FORMAT, baos);

        return baos.toByteArray();
    }

}
