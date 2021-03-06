package com.memastick.backmem.translator.impl;

import com.memastick.backmem.translator.dto.TranslatorDTO;
import com.memastick.backmem.translator.iface.Translator;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import com.vk.api.sdk.objects.wall.responses.PostResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    public void translate(TranslatorDTO dto) {
        try {
            PhotoUpload serverResponse = vk
                .photos()
                .getWallUploadServer(actor)
                .groupId(groupId)
                .execute();

            WallUploadResponse uploadResponse = vk
                .upload()
                .photoWall(serverResponse.getUploadUrl(), dto.getFile())
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
                .message(dto.getText())
                .attachments(attachId)
                .execute();

            log.info("Translate VKONTAKTE meme: " + response);
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}
