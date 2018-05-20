package com.miget.hxb.wx.custommsg;

import com.miget.hxb.wx.custommsg.content.MediaContent;

public class CustomImageMessage {
	
    private String touser;
    private String msgtype;
    private MediaContent image;
    
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
    public MediaContent getImage() {
        return image;
    }
    public void setImage(MediaContent image) {
        this.image = image;
    }
}
