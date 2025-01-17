package ECP.servlet.common.wechat;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import ECP.servlet.common.wechat.util.respMessage.Article;
import ECP.servlet.common.wechat.util.respMessage.MusicMessage;
import ECP.servlet.common.wechat.util.respMessage.NewsMessage;
import ECP.servlet.common.wechat.util.respMessage.TextMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by  on 2017/7/17.
 */
public class MessageUtil {
    /**
     * 返回消息类型：文本
     */
    public static final String RESP_MESSAGE_TYPE_TEXT = "text";
    /**
     * 返回消息类型：音乐
     */
    public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

    /**
     * 返回消息类型：图文
     */
    public static final String RESP_MESSAGE_TYPE_NEWS = "news";

    /**
     * 请求消息类型：文本
     */
    public static final String REQ_MESSAGE_TYPE_TEXT = "text";
    /**
     * 请求消息类型：图片
     */
    public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
    /**
     * 请求消息类型：链接
     */
    public static final String REQ_MESSAGE_TYPE_LINK = "link";
    /**
     * 请求消息类型：地理位置
     */
    public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
    /**
     * 请求消息类型：音频
     */
    public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
    /**
     * 请求消息类型：推送
     */
    public static final String REQ_MESSAGE_TYPE_EVENT = "event";

    /**
     * 事件类型：subscribe(订阅)
     */
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    /**
     * 事件类型：unsubscribe(取消订阅)
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    /**
     * 事件类型：CLICK(自定义菜单点击事件)
     */
    public static final String EVENT_TYPE_CLICK = "CLICK";
    /**
     * 事件类型：VIEW（）
     */
    public static final String EVENT_TYPE_VIEW="VIEW";

    /**
     * 解析微信发来的请求 XML
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<String, String>();

        // 从request中取得输入流
        InputStream inputStream = request.getInputStream();
        // 读取输入流
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        // 得到xml根元素
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList)
            map.put(e.getName(), e.getText());

        // 释放资源
        inputStream.close();
        inputStream = null;
        return map;
    }


    /**
     * 将响应文本消息对象转换为xml
     * @param textMessage 文本消息
     * @return xml
     */
    public static String textMessageToXml(TextMessage textMessage){
        //将xml的根节点内容转换为<xml>
        xstream.alias("xml",textMessage.getClass());
        return xstream.toXML(textMessage);
    }
    /**
     * 音乐消息对象转换成xml
     *
     * @param musicMessage 音乐消息对象
     * @return xml
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }
    /**
     * 图文消息对象转换成xml
     * @param newsMessage 图文消息对象
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * 扩展xstream，使其支持CDATA块
     * 40~62行代码是对xtream做了扩展，使其支持在生成xml各元素值时添加CDATA块。
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记
                boolean cdata = true;

                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * XML转换为map集合
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws Exception{
        Map<String, String> map= new HashMap<String,String>();
        SAXReader reader= new SAXReader();

        //从request中获取输出流
        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);

        Element root = doc.getRootElement();
        List<Element> list=root.elements();

        for(Element e:list){
            map.put(e.getName(),e.getText());
        }
        ins.close();
        return map;
    }


    public static String likeText(){
        StringBuffer sb=new StringBuffer();
        sb.append("thank you! We'll be better!");
        return sb.toString();
    }


    /**
     * 关注弹出菜单
     * @return
     */
    public static String menu(){
        StringBuffer sb=new StringBuffer();
        sb.append("欢迎关注公众号！,请按照提示操作\n\n");
        sb.append("1.关于我们\n");
        sb.append("2.联系方式\n");
        sb.append("回复\"?\"获取此菜单");
        return sb.toString();
    }
    public static String firstMenu(){
        StringBuffer sb=new StringBuffer();
        sb.append("1.about us");
        return sb.toString();
    }
    public static String secondMenu(){
        StringBuffer sb=new StringBuffer();
        sb.append("2.contact us");
        return sb.toString();
    }
}
