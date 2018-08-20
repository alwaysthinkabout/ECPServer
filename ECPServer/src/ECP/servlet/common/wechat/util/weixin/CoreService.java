package ECP.servlet.common.wechat.util.weixin;

import ECP.servlet.common.wechat.MessageUtil;
import ECP.servlet.common.wechat.util.respMessage.TextMessage;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Map;


/**
 * Created by  on 2017/8/28.
 */

public class CoreService {
    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public static String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml请求解析
            //调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            //消息内容
            String content=requestMap.get("Content");

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            //41-45 要返回的文本消息对象的组合块
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);


            //接收微信发送的各类型的消息，根据MsgType判断属于哪种类型的消息
            // 文本消息。根据关注菜单判断是否为问询类型，否则返回文本内容
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                if("1".equals(content)){
                    respContent=MessageUtil.firstMenu();
                }else if("2".equals(content)){
                    respContent=MessageUtil.secondMenu();
                }else if("?".equals(content)||"？".equals(content)) {
                    respContent = MessageUtil.menu();
                }else{respContent = "您发送的是文本消息: "+content;}
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "您发送的是地理位置消息！";
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
            }
            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是音频消息！";
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = MessageUtil.menu();
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    String eventKey = requestMap.get("EventKey");
                    if (eventKey.equals("11")) {

                    } else if (eventKey.equals("12")) {

                    } else if (eventKey.equals("13")) {
                        respContent = "联系方式：b3-335";
                    } else if (eventKey.equals("14")) {
                        respContent = "about us！";
                    } else if (eventKey.equals("21")) {
                        respContent = "注册账户菜单项被点击！";
                    } else if (eventKey.equals("22")) {
                        respContent = "管理账号菜单项被点击！";
                    } else if (eventKey.equals("31")) {
                        respContent = "学生资料菜单项被点击！";
                    } else if (eventKey.equals("33")) {
                        respContent = "企业资料菜单项被点击！";
                    } else if(eventKey.equals("34")){
                        respContent = "学校资料菜单项被点击！";
                    }
                }
            }
            //组装组合块，返回文本消息对象
            textMessage.setContent(respContent);
            //调用消息工具类MessageUtil将要返回的文本消息对象TextMessage转化成xml格式的字符串；
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }
}
