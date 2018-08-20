package ECP.servlet.common.wechat.util.message;

/**
 * Created by  on 2017/8/30.
 */
public class ImageMessage extends BaseMessage{
    // 图片链接
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}
