package ECP.servlet.common.wechat.util.pojo;

/**
 * 这类菜单项包含有二个属性：name和sub_button，而sub_button以是一个子菜单项数组。
 * 父菜单项的封装代码如下：
 * Created by  on 2017/8/27.
 */
public class ComplexButton extends Button {
    private Button[] sub_button;
    public Button[] getSub_button(){
        return sub_button;
    }
    public void setSub_button(Button[] sub_button){
        this.sub_button=sub_button;
    }
}
