package ECP.servlet.common.wechat;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ECP.servlet.common.wechat.util.pojo.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

/**
 * 公用平台通用接口类
 * Created by  on 2017/8/25.
 */
public class WeixinUtil {
    private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

    /**
     * 发起https请求并获取结果
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET，POST）
     * @param outputStr 提交数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod,String outputStr ){
        JSONObject jsonObject=null;
        StringBuffer stringBuffer= new StringBuffer();
        try{
            //35-45建立https连接
            //创建SSLContext对象，并使用我们指定的信任管理器MyX509TrustManager初始化
            TrustManager[] tm={new MyX509TrustManager()};
            //上面提到SunJSSE，JSSE（Java Secure Socket Extension）是实现Internet安全通信的一系列包的集合。
            // 它是一个SSL和TLS的纯Java实现，可以透明地提供数据加密、服务器认证、信息完整性等功能，
            SSLContext sslContext=SSLContext.getInstance("SSL","SunJSSE");
            sslContext.init(null,tm,new java.security.SecureRandom());
            //从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf=sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            // 设置请求方式（GET/POST）
            //GET 用于信息获取，Get是向服务器发索取数据的一种请求
            //POST 可能修改服务器资源的请求，Post是向服务器提交数据的一种请求
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str=null;
            while((str=bufferedReader.readLine())!=null){
                stringBuffer.append(str);
            }
            bufferedReader.close();
            inputStream.close();

            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(stringBuffer.toString());

        }catch(ConnectException e){
            log.error("wechat server connection timed out");
        }catch(Exception e){
            log.error("https request errors:{}",e);
        }
        return jsonObject;
    }

    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 凭证access_token获取方法
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret){
        AccessToken accessToken=null;
        String requestUrl=access_token_url.replace("APPID",appid).replace("APPSECRET",appsecret);
        JSONObject jsonObject=httpRequest(requestUrl,"GET",null);
        //请求成功
        if(null!=jsonObject){
            try{
                accessToken=new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            }catch (JSONException e) {
                accessToken = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }
        return accessToken;
    }
    //创建自定义菜单
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    /**
     * 创建菜单
     * @param menu 菜单实例
     * @param accessToken 有效的accessToken
     * @return 0表示成功，其余失败
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;

        // 拼装创建菜单的url
        String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.fromObject(menu).toString();
        // 调用接口创建菜单
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return result;
    }
}