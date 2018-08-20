package ECP.servlet.common.wechat.util.pojo;

/**
 * 调用凭证接口后，微信服务器返回json格式数据 {"access_token":"ACCESS_TOKEN","expires_in":7200}
 * 封装成AccessToken对象，对象有二个属性：token和expiresIn
 * Created by  on 2017/8/26.
 */
public class AccessToken {
    // 获取到的凭证
    private String token;
    // 凭证有效时间，单位：秒
    private int expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
