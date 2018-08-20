package ECP.servlet.common.wechat.util.respMessage;

/**
 * Created by  on 2017/8/31.
 */
public class MusicMessage extends BaseMessage {
    // 回复的消息内容
    // 音乐
    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music music) {
        Music = music;
    }
}
