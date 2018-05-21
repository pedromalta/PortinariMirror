package net.clubedocomputador.portinarimirror.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User extends BaseModel{

    @Expose
    private String nickname;

    @Expose
    private String name;

    @Expose
    private byte[] face;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFace() {
        return face;
    }

    public void setFace(byte[] face) {
        this.face = face;
    }
}
