package com.miget.hxb.wx.custommsg;

import com.miget.hxb.wx.custommsg.content.Articles;

public class CustomNewsMessage {
	
    private String touser;
    private String msgtype;
    private Articles news;

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
    public Articles getNews() {
        return news;
    }
    public void setNews(Articles news) {
        this.news = news;
    }
}
