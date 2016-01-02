package veloundry.Engine;

import java.util.ArrayList;
import java.util.ListIterator;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**@author Darari*/
public class Member {
    private SimpleStringProperty name;
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleIntegerProperty id;
    private SimpleStringProperty alamat;
    private SimpleStringProperty hp;

    private String Message;
    private String UserLogined;
    
    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
    String tmpUser= null, tmpPass=null, admName=null, admPass=null;
    String[] usernama = new String[1000];
    String[] userpass = new String[1000];
    private DBManagement dbms = new DBManagement();
    
    public SimpleStringProperty getName() {
        return name;
    }

    public void setName(SimpleStringProperty name) {
        this.name = name;
    }

    public SimpleStringProperty getUsername() {
        return username;
    }

    public void setUsername(SimpleStringProperty username) {
        this.username = username;
    }

    public SimpleStringProperty getPassword() {
        return password;
    }

    public void setPassword(SimpleStringProperty password) {
        this.password = password;
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public SimpleStringProperty getAlamat() {
        return alamat;
    }

    public void setAlamat(SimpleStringProperty alamat) {
        this.alamat = alamat;
    }

    public SimpleStringProperty getHp() {
        return hp;
    }

    public void setHp(SimpleStringProperty hp) {
        this.hp = hp;
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
    private void LoadData(ArrayList dbMember){
        int i = 0;
        String tmp;
        String[] tmpuserpass;
        ListIterator iter = dbMember.listIterator();
        while(iter.hasNext()){
            tmp = iter.next().toString();
            tmpuserpass = dbms.SplitString(tmp);
            this.usernama[i] = tmpuserpass[0];
            this.userpass[i] = tmpuserpass[1];
            i++;
        }
    }
}
