package ECP.servlet.common.wechat.util.pojo;

/**
 * 采用面向对象的编程方式，最终提交的json格式菜单数据就应该是由对象直接转换得到，而不是在程序代码中拼一大堆json数据
 * 首先是菜单项的基类，所有一级菜单、二级菜单都共有一个相同的属性，那就是name
 * json菜单
 * {
        "button":[
        {
            "type":"click",
            "name":"今日歌曲",
            "key":"V1001_TODAY_MUSIC"
        },
        {
            "type":"click",
            "name":"歌手简介",
            "key":"V1001_TODAY_SINGER"
        },
        {
            "name":"菜单",
            "sub_button":[
            {
                "type":"click",
                "name":"hello word",
                "key":"V1001_HELLO_WORLD"
            },
            {
                "type":"click",
                "name":"赞一下我们",
                "key":"V1001_GOOD"
            }]
        }]
    }
 * Created by CutePiggy on 2017/8/26.
 */
public class Button {
    private String name;
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
}
