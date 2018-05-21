package net.clubedocomputador.portinarimirror.data.model;

import android.util.Base64;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FaceRecognition extends BaseModel {

    @Expose
    private String image;

    @Expose
    @SerializedName("subject_id")
    private String nickname;

    @Expose
    @SerializedName("gallery_name")
    private String galleryName;

    public FaceRecognition(byte[] image, String nickname, String galleryName){
        this(image, galleryName);
        this.nickname = nickname;

    }

    public FaceRecognition(byte[] image, String galleryName){
        this.image = Base64.encodeToString(image,
                Base64.NO_WRAP);
        this.galleryName = galleryName;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }
}
