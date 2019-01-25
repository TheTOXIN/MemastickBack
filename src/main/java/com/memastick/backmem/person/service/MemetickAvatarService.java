package com.memastick.backmem.person.service;

import com.memastick.backmem.errors.consts.ErrorCode;
import com.memastick.backmem.errors.exception.ValidationException;
import com.memastick.backmem.main.util.ImageUtil;
import com.memastick.backmem.person.entity.Memetick;
import com.memastick.backmem.person.entity.MemetickAvatar;
import com.memastick.backmem.person.repository.MemetickAvatarRepository;
import com.memastick.backmem.security.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Service
public class MemetickAvatarService {

    private final static long MIN_SIZE_BYTE = (long) Math.pow(2, 10);
    private final static long MAX_SIZE_BYTE = (long) Math.pow(2, 20);

    private final static int PHOTO_SIZE_PIX = 128;

    private final static String FORMAT = "jpg";

    private final Set<String> validContent = new HashSet<>(Arrays.asList(
        "image/jpg",
        "image/jpeg",
        "image/png"
    ));

    private final SecurityService securityService;
    private final MemetickAvatarRepository memetickAvatarRepository;

    @Autowired
    public MemetickAvatarService(
        SecurityService securityService,
        MemetickAvatarRepository memetickAvatarRepository
    ) {

        this.securityService = securityService;
        this.memetickAvatarRepository = memetickAvatarRepository;
    }

    @Transactional
    public byte[] download(UUID id) {
        return memetickAvatarRepository.findByMemetickId(id).getAvatar();
    }

    @Transactional
    public void upload(MultipartFile image) throws IOException {
        validateImage(image);

        BufferedImage bufferedImage = ImageIO.read(image.getInputStream());

        byte[] photoBytes = optimizeImage(bufferedImage);

        Memetick memetick = securityService.getCurrentMemetick();
        MemetickAvatar memetickAvatar = memetickAvatarRepository.findByMemetickId(memetick.getId());

        memetickAvatar.setAvatar(photoBytes);

        memetickAvatarRepository.save(memetickAvatar);
    }

    private void validateImage(MultipartFile image) {
        if (image == null || image.getContentType() == null) {
            throw new ValidationException(
                ErrorCode.IMAGE_FORMAT,
                "Image == NULL"
            );
        }
        if (!validContent.contains(image.getContentType())) {
            throw new ValidationException(
                ErrorCode.IMAGE_FORMAT,
                "Only PNG || JPG format"
            );
        }

        if (image.getSize() <= MIN_SIZE_BYTE || image.getSize() >= MAX_SIZE_BYTE) {
            throw new ValidationException(
                ErrorCode.IMAGE_FORMAT,
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
