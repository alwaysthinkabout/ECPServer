package ECP.handle.common;

import java.io.Serializable;

/**
 * Created by OldLiu on 2017/4/21.
 */

public class Order implements Serializable{
    private static final long serialVersionUID = 8395715433826041704L;
    public String to;
    public String from;
    public String operation;
    public Object data;
    
    public Order(String to, String from, String operation, Object data) {
        this.to = to;
        this.from = from;
        this.operation = operation;
        this.data = data;
    }
    
    public String toString() {
        return "{\'to\':" + this.to + ",\'from\':" + this.from + ",\'operation\':" + this.operation + ",\'data:\'" + this.data + "}";
    }
}