package ECP.servlet.common.wechat.util.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ECP.servlet.common.wechat.WeixinUtil;

import ECP.servlet.common.wechat.util.pojo.*;

/**
 * Created by  on 2017/8/27.
 */
public class MenuManager {
    private static Logger log= LoggerFactory.getLogger(MenuManager.class);

    /**
     * 组装菜单数据
     * @return
     */
//    private static String project = "http://ecpserver.tunnel.echomod.cn/ECPServer/wechat/";
    private static String project = "http://www.cutestpiggy.cn/ECPServer/wechat/";

    private static Menu getMenu() {
        ViewButton btn11 = new ViewButton();
        btn11.setName("华南理工");
        btn11.setType("view");
        btn11.setUrl("http://www.scut.edu.cn");

        ViewButton btn12 = new ViewButton();
        btn12.setName("搜搜");
        btn12.setType("view");
        btn12.setUrl("http://www.soso.com/");

        CommonButton btn13 = new CommonButton();
        btn13.setName("联系方式");
        btn13.setType("click");
        btn13.setKey("13");

        CommonButton btn14 = new CommonButton();
        btn14.setName("关于我们");
        btn14.setType("click");
        btn14.setKey("14");

        ViewButton btn21 = new ViewButton();
        btn21.setName("注册账号");
        btn21.setType("view");
        btn21.setUrl(project+"weChatRegister.jsp");

        CommonButton btn22 = new CommonButton();
        btn22.setName("管理账号");
        btn22.setType("click");
        btn22.setKey("22");


        ViewButton btn31 = new ViewButton();
        btn31.setName("学生资料");
        btn31.setType("view");
        btn31.setUrl(project+"login.jsp?1");

        ViewButton btn33 = new ViewButton();
        btn33.setName("企业资料");
        btn33.setType("view");
        btn33.setUrl(project+"login.jsp?2");

        ViewButton btn34 = new ViewButton();
        btn34.setName("学校资料");
        btn34.setType("view");
        btn34.setUrl(project+"login.jsp?3");


        ViewButton btn32 = new ViewButton();
        btn32.setName("APP下载");
        btn32.setType("view");
        btn32.setUrl(project + "JoiningDownload.html");

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("关于我们");
        mainBtn1.setSub_button(new Button[] { btn11, btn12, btn13, btn14 });

        ComplexButton mainBtn2 = new ComplexButton();
        mainBtn2.setName("相关注册");
        mainBtn2.setSub_button(new Button[] { btn21, btn22});

        ComplexButton mainBtn3 = new ComplexButton();
        mainBtn3.setName("资料登陆");
        mainBtn3.setSub_button(new Button[] { btn31, btn33, btn34, btn32 });

        /**
         * 这是公众号目前的菜单结构，每个一级菜单都有二级菜单项<br>
         *
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */
        Menu menu = new Menu();
        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

        return menu;
    }

    public static void main(String[] args){
        //第三方用户唯一凭证
        String appId="wx81724713ea8f8d80";
        //第三方用户唯一密钥
        String appSecret="fa3ab70edefa10f95c15cbba9c7e9e54";
        //获取AccessToken
        AccessToken at= WeixinUtil.getAccessToken(appId,appSecret);

        if(null!=at) {
            //调用接口创建菜单
            int result=WeixinUtil.createMenu(getMenu(), at.getToken());
            if(0==result){
                log.info("success to create menu");
                System.out.println("success");
            }else{
                log.info("fail to create menu, err code: "+result);
                System.out.println("can't"+result);
            }
        }
    }



}
