package veloundry.Engine;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Random;

/**@author Darari*/
public class BoEngine {
    private final ArrayList<Order> data = new ArrayList();
    private DBManagement dbms = new DBManagement();
    private DateManagement dateman = new DateManagement();
    private Connection con = new Connection();
    private Order data_order;
    private String Message;
    private String currentFileDate;
    private boolean currentState;
    
    public String getCurrentFileDate() {
        return currentFileDate;
    }

    public void setCurrentFileDate(String currentFileDate) {
        this.currentFileDate = currentFileDate;
    }

    public boolean isCurrentState() {
        return currentState;
    }

    public void setCurrentState(boolean currentState) {
        this.currentState = currentState;
    }

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
    
    public ArrayList<Order> GetNewOrder(){
        ArrayList<Order> data = new ArrayList();
        try{
            ArrayList tmp = new ArrayList();
            String tmp2;
            tmp = dbms.BacaFile("Order.txt");
            ListIterator iter = tmp.listIterator();
            int i = 0;
            while(iter.hasNext()){
                if (i != 0){
                    tmp2 = tmp.get(i).toString();
                    String[] thedata = dbms.SplitString(tmp2);
                    int a = Integer.parseInt(thedata[0]);
                    Order order = new Order(a,thedata[1],thedata[2],thedata[3],thedata[4],thedata[5],thedata[6]);
                    data.add(order);
                }
                i++;
            }
            this.currentState = true;
        } catch(Exception e){
            this.currentState = false;
        }
        return data;
    }
    public ArrayList<Order> GetConfirmedOrder(){
        ArrayList<Order> data = new ArrayList();
        try{
            ArrayList tmp = new ArrayList();
            String tmp2;
            tmp = dbms.BacaFile("order_acc.txt");
            ListIterator iter = tmp.listIterator();
            int i = 0;
            while(iter.hasNext()){
                if (i != 0){
                    tmp2 = tmp.get(i).toString();
                    String[] thedata = dbms.SplitString(tmp2);
                    int lid = Integer.parseInt(thedata[0]);
                    int harga = Integer.parseInt(thedata[6]);
                    Order order = new Order(lid,thedata[1],thedata[2],thedata[3],thedata[4],thedata[5],harga);
                    data.add(order);
                }
                i++;
            }
            this.currentState = true;
        } catch(Exception e){
            this.currentState = false;
            e.printStackTrace();
            setMessage(e.getMessage());
        }
        return data;
    }
    
    public int GenerateLID(){
        Random rand = new Random();
        int randomNum = rand.nextInt((1000 - 1) + 1) + 1;
        return randomNum;
    }
}
