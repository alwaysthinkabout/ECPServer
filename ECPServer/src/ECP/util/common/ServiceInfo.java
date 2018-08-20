package ECP.util.common;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by OldLiu on 2017/4/21.
 */

public class ServiceInfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WT_JOINT = 0;
    public static final int WT_INDEPENDENT = 1;
    private String account;
    private String nickname;
    private String image;
    private String tag;
    private String objectType;
    private int workType;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public int getWorkType() {
        return workType;
    }

    public void setWorkType(int workType) {
        this.workType = workType;
    }

    public Object getWorkTypeInfo() {
        return workTypeInfo;
    }

    public void setWorkTypeInfo(Object workTypeInfo) {
        this.workTypeInfo = workTypeInfo;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    private Object workTypeInfo;
    private String introduce;

    public ServiceInfo(String account, String nickname, String image, String tag, String objectType, int workType, Object workTypeInfo, String introduce) {
        this.account = account;
        this.nickname = nickname;
        this.image = image;
        this.tag = tag;
        this.objectType = objectType;
        this.workType = workType;
        this.workTypeInfo = workTypeInfo;
        this.introduce = introduce;
    }
    public ServiceInfo(){

    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("account", account);
        result.put("nickname", nickname);
        result.put("image", image);
        result.put("tag", tag);
        result.put("objectType", objectType);
        result.put("workType", workType);
        result.put("workTypeInfo", workTypeInfo);
        result.put("introduce", introduce);
        return result;
    }

    public ServiceInfo(JSONObject data) throws JSONException {
        this.account = data.getString("account");
        this.nickname = data.getString("nickname");
        this.image = data.getString("image");
        this.tag = data.getString("tag");
        this.objectType = data.getString("objectType");
        this.workType = 0;
        this.workTypeInfo = "";
        this.introduce = data.getString("introduce");
    }
}