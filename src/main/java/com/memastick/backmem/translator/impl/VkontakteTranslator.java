package com.memastick.backmem.translator.impl;

import com.memastick.backmem.memes.entity.Meme;
import com.memastick.backmem.translator.iface.Translator;
import com.memastick.backmem.translator.util.TranslatorUtil;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.GetResponse;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import com.vk.api.sdk.objects.wall.responses.PostResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class VkontakteTranslator implements Translator {

    private final static Logger log = LoggerFactory.getLogger(VkontakteTranslator.class);

    private final Integer groupId;

    private VkApiClient vk;
    private UserActor actor;

    public VkontakteTranslator(
        @Value("${api.vkontakte.group.id}") Integer groupId,
        @Value("${api.vkontakte.user.id}") Integer userId,
        @Value("${api.vkontakte.token}") String accessToken
    ) {
        this.vk = new VkApiClient(HttpTransportClient.getInstance());
        this.actor = new UserActor(userId, accessToken);
        this.groupId = groupId;
    }

    @Override
    public void translate(Meme meme) {
        File file = TranslatorUtil.downloadFile(meme.getUrl());
        if (file == null) return;

        try {
            PhotoUpload serverResponse = vk
                .photos()
                .getWallUploadServer(actor)
                .groupId(groupId)
                .execute();

            WallUploadResponse uploadResponse = vk
                .upload()
                .photoWall(serverResponse.getUploadUrl(), file)
                .execute();

            List<Photo> photoList = vk.photos()
                .saveWallPhoto(actor, uploadResponse.getPhoto())
                .groupId(groupId)
                .server(uploadResponse.getServer())
                .hash(uploadResponse.getHash())
                .execute();

            Photo photo = photoList.get(0);
            String attachId = "photo" + photo.getOwnerId() + "_" + photo.getId();

            PostResponse response = vk.wall().post(actor)
                .fromGroup(true)
                .ownerId(groupId * -1)
                .message(TranslatorUtil.prepareText(meme))
                .attachments(attachId)
                .execute();

            log.info("Translate VKONTAKTE meme: " + response);
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}
