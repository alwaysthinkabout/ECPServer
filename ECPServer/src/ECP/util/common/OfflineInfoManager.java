package ECP.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

public class OfflineInfoManager {
	private static final Map<String, Set<JSONObject>> storage = new HashMap<>();
	
	public static List<JSONObject> getOfflineInfo(String account){
		Set<JSONObject> offlineInfo = storage.get(account);
		List<JSONObject> result = new ArrayList<>();
		if(offlineInfo != null){
			result.addAll(offlineInfo);
		}
		return result;
	}
	
	public static void add(String account,JSONObject data){
        Set<JSONObject> offlineInfo = storage.get(account);
        if(offlineInfo != null){
            offlineInfo.add(data);
        }else{
        	offlineInfo = new HashSet<>();
        	offlineInfo.add(data);
            storage.put(account,offlineInfo);
        }
    }

    public static void remove(String account,JSONObject data){
        Set<JSONObject> offlineInfo = storage.get(account);
        if(offlineInfo != null){
            offlineInfo.remove(data);
        }
    }
    
    public static void remove(String account){
    	Set<JSONObject> offlineInfo = storage.get(account);
    	if(offlineInfo != null){
    		offlineInfo.clear();
    	}
    }
}
