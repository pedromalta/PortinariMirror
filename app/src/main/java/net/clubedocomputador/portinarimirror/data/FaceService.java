package net.clubedocomputador.portinarimirror.data;


import android.util.Base64;

import net.clubedocomputador.portinarimirror.data.model.FaceRecognition;
import net.clubedocomputador.portinarimirror.data.model.User;
import net.clubedocomputador.portinarimirror.data.remote.FaceApi;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;


@Singleton
public class FaceService {

    private static final String GALLERY_NAME = "portinari_mirror";

    private FaceApi faceApi;

    @Inject
    public FaceService(FaceApi faceApi) {
        this.faceApi = faceApi;
    }

    public Single<String> create(User user) {
        FaceRecognition face = new FaceRecognition(user.getFace(), user.getNickname(), GALLERY_NAME);

        return faceApi.enroll(face);
    }

    public Single<String> verify(User user) {
        FaceRecognition face = new FaceRecognition(user.getFace(), user.getNickname(), GALLERY_NAME);

        return faceApi.enroll(face);
    }

    public Single<String> recognize(byte[] image) {
        FaceRecognition face = new FaceRecognition(image, GALLERY_NAME);

        return faceApi.recognize(face);
    }

}
