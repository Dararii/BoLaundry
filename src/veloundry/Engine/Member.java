package veloundry.Engine;

import java.util.ArrayList;
import java.util.ListIterator;
import javafx.beans.property.SimpleStringProperty;

/**@author Darari*/
public class Member {

    private String Message;
    private String UserLogined;
    
    String tmpUser= null, tmpPass=null, admName=null, admPass=null;
    String[] usernama = new String[1000];
    String[] userpass = new String[1000];
    String[] useralamat = new String[1000];
    String[] userlaundryname = new String[1000];
    String[] userhp = new String[1000];
    private int UserIndex;
    private DBManagement dbms = new DBManagement();

    public int getUserIndex() {
        return UserIndex;
    }

    public String[] getUseralamat() {
        return useralamat;
    }

    public String[] getUserlaundryname() {
        return userlaundryname;
    }

    public String[] getUserhp() {
        return userhp;
    }

    public void setUserIndex(int UserIndex) {
        this.UserIndex = UserIndex;
    }

    public String getUseralamat(int index) {
        return useralamat[index];
    }

    public String getUserlaundryname(int index) {
        return userlaundryname[index];
    }

    public String getUserhp(int index) {
        return userhp[index];
    }
    
    
    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public boolean AdminLogin(String username, String pass, ArrayList data){
        this.tmpUser = username;
        this.tmpPass = pass;
        boolean test;
        test = LoginTest(data);
        return test;
    }
    
    public boolean LoginTest(ArrayList dbMember){ // true for Login Success
        boolean state = false;
        boolean isThereUser = false;
        LoadData(dbMember);
        for (int i = 0; i<this.usernama.length;i++){
            if (usernama[i] != null){
                if (usernama[i].equalsIgnoreCase(this.tmpUser)){
                    isThereUser = true;
                    if (userpass[i].equals(this.tmpPass)){
                        UserLogined = usernama[i];
                        state = true;
                        setUserIndex(i);
                        setMessage("Autentikasi Sukses");
                    } else {
                        setMessage("Password Salah");
                        state = false;
                    }
                    break;
                }
                else {
                    isThereUser = false;
                }
            }
        }     
        if (!isThereUser) {
            setMessage("Username tidak ditemukan !");
        }
        return state;
    }
    public void LoadData(ArrayList dbMember){
        int i = 0;
        String tmp;
        String[] tmpuserpass;
        ListIterator iter = dbMember.listIterator();
        try{
            while(iter.hasNext()){
                tmp = iter.next().toString();
                tmpuserpass = dbms.SplitString(tmp);
                this.usernama[i] = tmpuserpass[0];
                this.userpass[i] = tmpuserpass[1];
                this.userlaundryname[i] = tmpuserpass[2];
                this.useralamat[i] = tmpuserpass[3];
                this.userhp[i] = tmpuserpass[4];
                i++;
            }
        } catch(Exception e){
            setMessage(e.getMessage());
        }
    }
}
