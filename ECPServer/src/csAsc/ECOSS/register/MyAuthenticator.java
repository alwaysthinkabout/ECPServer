package csAsc.ECOSS.register;
 
 
import javax.mail.Authenticator;  
import javax.mail.PasswordAuthentication;  
  
  
public class MyAuthenticator extends Authenticator {  
    public String userName;  
    public String password;  
  
    public MyAuthenticator(String userName, String password){  
        this.userName = userName;  
        this.password = password;  
    }  
  
    @Override  
    protected PasswordAuthentication getPasswordAuthentication() {  
        return new PasswordAuthentication(userName, password);  
    }  
}  
