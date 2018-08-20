package ECP.servlet.common.wechat.util.pojo;

/**
 * 封装菜单
 * 菜单对象包含多个菜单项（最多只能有3个）
 * 这些菜单项即可以是子菜单项（不含二级菜单的一级菜单），也可以是父菜单项（包含二级菜单的菜单项）
 * Created by  on 2017/8/27.
 */
public class Menu {
    private Button[] button;
    public Button[] getButton(){
        return button;
    }
    public void setButton(Button[] button){
        this.button=button;
    }
}
