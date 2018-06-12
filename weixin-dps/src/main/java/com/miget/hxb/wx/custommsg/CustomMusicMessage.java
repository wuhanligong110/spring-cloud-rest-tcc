package com.miget.hxb.wx.custommsg;

import com.miget.hxb.wx.custommsg.content.MusicContent;

public class CustomMusicMessage {
	
    private String touser;
    private String msgtype;
    private MusicContent music;

    public String getTouser() {
        return touser;
    }
    public void setTouser(String touser) {
        this.touser = touser;
    }
    public String getMsgtype() {
        return msgtype;
    }
    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
    public MusicContent getMusic() {
        return music;
    }
    public void setMusic(MusicContent music) {
        this.music = music;
    }
}
