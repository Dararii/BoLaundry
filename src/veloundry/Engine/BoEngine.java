package veloundry.Engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.ListIterator;
import java.util.Random;
import javafx.beans.property.SimpleDoubleProperty;

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
    private SimpleDoubleProperty prop = new SimpleDoubleProperty(0);

    public double getProp() {
        return prop.get();
    }

    public void setProp(double prop) {
        this.prop = new SimpleDoubleProperty(prop);
    }
    
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
        boolean state = false;
        ArrayList tmp;
        int LID = GenerateLID(); 
        try{
            setProp(0.15);
            String konten = Integer.toString(LID) + "|" + name + "|" + alamat + "|" + hp + "|" + JenisCucian + "|" + date1 + "|" + date2;
            int c = GetFileFromServer("Order.txt", "Darari");
            setProp(0.35);
            if (c == 2){
                tmp = dbms.BacaFile("Order.txt");
                tmp.remove(0);
                tmp.add(0, this.dateman.GetCurrentDate());
                tmp.add(tmp.size(), konten);
                boolean b = dbms.TulisFile("Order.txt", tmp, true);
                setProp(0.65);
                if (b) {
                    boolean d = con.UploadFile("Order.txt", "Darari/", "albc.ucoz.com", "dalbc", "darari15");
                    setProp(0.85);
                    if (d){
                        setProp(1);
                        setMessage("Order dikirimkan\nLID Anda : " + Integer.toString(LID) + "\nSimpan kode Anda untuk memantau Loundry Anda.");
                        state = true;
                    }
                } else {
                    setMessage("Terjadi Error saat menulis data");
                }
            } else {
                setMessage("Tidak dapat mensinkronasi dengan server.");
            }
        }
        catch(Exception e){
            state = false;
            setMessage(e.getMessage());
        }
        return state;
    }
    
    public boolean isServerHasNewerFile(String filename){ //Gak mari, gak ro gawe opo
        boolean state, isServerHas = false;
        ArrayList tmp1, tmp2;
        String dateLoc, dateServer;
        Date date1, date2;
        if (filename.equalsIgnoreCase("Order.txt")){
            if(con.CekInetConnection("http://albc.ucoz.com")){
                tmp1 = this.dbms.BacaFile(filename);
                if(con.DownloadFile("http://albc.ucoz.com/Darari/Order.txt", "Order.db")){
                    tmp2 = this.dbms.BacaFile("Order.db");
                    try{
                        dateLoc  = tmp1.get(0).toString();
                        dateServer = tmp2.get(0).toString();
                        date1 = this.dateman.CreateDateFormString(dateLoc);
                        date2 = this.dateman.CreateDateFormString(dateServer);
                        boolean a = this.dateman.CekIsDateNewer(date1, date2);
                    } catch(Exception e){
                        
                    }
                }
                else{
                    isServerHas = false;
                }
            }
            
        }
        return true;
    }
    
    public int GetFileFromServer(String filename, String username){
        int a = 0;
        //if (filename.equalsIgnoreCase("Order.txt")){
        if(con.CekInetConnection("http://albc.ucoz.com")){
            a = 1;
            if(con.DownloadFile("http://albc.ucoz.com/" + username + "/" + filename, filename)){
                a = 2;
            }
            else{
                if (con.getMessage().equalsIgnoreCase("File Not Found")){
                    if (this.dbms.CheckFileExist(filename) == true){

                    }
                    else {
                        String x = dateman.GetCurrentDate();
                        this.dbms.TulisFile(filename, x,true);
                    }
                    PutFileToServer(filename, username);
                }
            }
        }
        //}
        return a;
    }
    
    public boolean PutFileToServer(String filename, String username){
        boolean state = false;
        if(con.CekInetConnection("http://albc.ucoz.com")){
            if(con.UploadFile(filename, username + "/", "albc.ucoz.com", "dalbc", "darari15")){
                state = true;
            }
            else{
                setMessage("Unable to Sync with server");
            }
            setMessage("No Connection");
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
                if (i == 0){
                    setCurrentFileDate(tmp.get(i).toString());
                } else if (i != 0){
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
        int i = 0;
        try{
            ArrayList tmp = new ArrayList();
            String tmp2;
            tmp = dbms.BacaFile("order_acc.txt");
            ListIterator iter = tmp.listIterator();
            while(i < tmp.size()){
                if (i == 0){
                    setCurrentFileDate(tmp.get(i).toString());
                } else if (i != 0){
                    tmp2 = tmp.get(i).toString();
                    String[] thedata = dbms.SplitString(tmp2);
                    if (!thedata[10].startsWith("Selesai")){
                        int lid = Integer.parseInt(thedata[0]);
                        int harga = Integer.parseInt(thedata[7]);
                        int berat = Integer.parseInt(thedata[8]);
                        Order order = new Order(lid,thedata[1],thedata[2],thedata[3],thedata[4],thedata[5],thedata[6],harga, berat, thedata[9], thedata[10]);
                        data.add(order);
                    }
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
    
    public ArrayList<Order> GetCompletedOrder(){
        ArrayList<Order> data = new ArrayList();
        int i = 0;
        try{
            ArrayList tmp = new ArrayList();
            String tmp2;
            tmp = dbms.BacaFile("order_acc.txt");
            ListIterator iter = tmp.listIterator();
            while(i < tmp.size()){
                if (i == 0){
                    setCurrentFileDate(tmp.get(i).toString());
                } else if (i != 0){
                    tmp2 = tmp.get(i).toString();
                    String[] thedata = dbms.SplitString(tmp2);
                    if (thedata[10].startsWith("Selesai")){
                        int lid = Integer.parseInt(thedata[0]);
                        int harga = Integer.parseInt(thedata[7]);
                        int berat = Integer.parseInt(thedata[8]);
                        Order order = new Order(lid,thedata[1],thedata[2],thedata[3],thedata[4],thedata[5],thedata[6],harga, berat, thedata[9], thedata[10]);
                        data.add(order);
                    }
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
        Date a = new Date();
        int randomNum = rand.nextInt((900 - 1) + 1) + a.getMinutes();
        return randomNum;
    }
}
