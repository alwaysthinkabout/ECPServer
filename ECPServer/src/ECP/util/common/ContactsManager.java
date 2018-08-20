package ECP.util.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContactsManager {
	private static final Map<String,Set<String>> storage = new HashMap<>();

    public static List<String> getContact(String account){
        Set<String> contact = storage.get(account);
        List<String> result = new ArrayList<>();
        if(contact != null){
            result.addAll(contact);
        }else{
        	result.add("104");
            result.add("105");
            result.add("106");
            result.add("90000");
        }
        return result;
    }

    public static void add(String account,String friend){
        Set<String> contact = storage.get(account);
        if(contact != null){
            contact.add(friend);
        }else{
            contact = new HashSet<>();
            contact.add(friend);
            contact.add("104");
            contact.add("105");
            contact.add("90000");
            storage.put(account,contact);
        }
    }

    public static void remove(String account,String friend){
        Set<String> contact = storage.get(account);
        if(contact != null){
            contact.remove(friend);
        }
    }
}
