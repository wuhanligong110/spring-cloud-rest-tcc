package com.miget.hxb.wx.message.resp;

/**
 * ClassName: ImageMessage
 * @Description: 图片消息
 */
public class ImageMessage extends BaseMessage{

    private Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }

}