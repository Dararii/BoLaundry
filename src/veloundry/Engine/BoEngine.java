package veloundry.Engine;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.control.Alert;

/**@author Darari*/
public class BoEngine {
    private final ArrayList<Order> data = new ArrayList();
    private DBManagement dbms = new DBManagement();
    private Connection con = new Connection();
    private Order data_order;
    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
    
    public ArrayList<Order> getData() {
        return this.data;
    }
    
    public boolean AddNewOrder(String name, String alamat, String hp, String JenisCucian, String date1, String date2){
        boolean state;
        int LID = GenerateLID(); 
        try{
            String konten = Integer.toString(LID) + "|" + name + "|" + alamat + "|" + hp + "|" + JenisCucian + "|" + date1 + "|" + date2;
            boolean b = dbms.TulisFile("Order.txt", konten, false);
            if (b) {
                setMessage("Order dikirimkan\nLID Anda : " + Integer.toString(LID) + "\nSimpan kode Anda untuk memantau Loundry Anda.");
            } else {
                setMessage("Terjadi Error");
            }
            state = true;
        }
        catch(Exception e){
            state = false;
            setMessage(e.getMessage());
        }
        return state;
    }
    
    public int GenerateLID(){
        Random rand = new Random();
        int randomNum = rand.nextInt((1000 - 1) + 1) + 1;
        return randomNum;
    }
}
