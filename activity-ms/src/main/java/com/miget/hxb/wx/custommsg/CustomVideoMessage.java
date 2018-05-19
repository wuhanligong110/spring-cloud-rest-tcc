package com.miget.hxb.wx.custommsg;

import com.miget.hxb.wx.custommsg.content.VideoContent;

public class CustomVideoMessage {
	
    private String touser;
    private String msgtype;
    private VideoContent video;
    
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
	public VideoContent getVideo() {
		return video;
	}
	public void setVideo(VideoContent video) {
		this.video = video;
	}

}
