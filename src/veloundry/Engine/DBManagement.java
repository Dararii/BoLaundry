package veloundry.Engine;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.ListIterator;
import javafx.collections.ObservableList;
/**@author Darari*/
public class DBManagement {
    public ArrayList BacaFile(String namafile){
        ArrayList tmp = new ArrayList();
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(namafile));
            while ((sCurrentLine = br.readLine()) != null) {
		tmp.add(sCurrentLine);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return tmp;
    }
    public boolean TulisFile(String namafile, ArrayList konten, boolean isCreateNewFile){
        if (isCreateNewFile){
            try{
                PrintWriter writer = new PrintWriter(namafile, "UTF-8");
                ListIterator iter = konten.listIterator();
                while(iter.hasNext()){
                    writer.println(iter.next());
                }
                writer.close();
                return true;
            }
            catch(IOException e){
                e.printStackTrace();
                return false;
            }
        }
        else{
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(namafile, true)))){
                ListIterator iter = konten.listIterator();
                while(iter.hasNext()){
                    writer.println(iter.next());
                }
                writer.close();
                return true;
            }
            catch(IOException e){
                e.printStackTrace();
                return false;
            }
        }
    }
    public boolean TulisFile(String namafile, ObservableList<Order> konten, boolean isCreateNewFile){
        if (isCreateNewFile){
            try{
                PrintWriter writer = new PrintWriter(namafile, "UTF-8");
                for (Order konten1 : konten) {
                    String tmp = Integer.toString(konten1.getLid()) + "|" + konten1.getNama() + "|" +
                            konten1.getAlamat() + "|" + konten1.getNomorHP() + "|" +
                            konten1.getJenisCucian() + "|" + konten1.getTglAmbil()+ "|" + konten1.getTglAntar() +
                            "|" + Integer.toString(konten1.getHarga()) + "|" + Integer.toString(konten1.getBerat()) + "|" +
                            konten1.getPesan()+ "|" + konten1.getStatus();
                    writer.println(tmp);
                }
                writer.close();
                return true;
            }
            catch(IOException e){
                e.printStackTrace();
                return false;
            }
        }
        else{
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(namafile, true)))){
                for (Order konten1 : konten) {
                    String tmp = Integer.toString(konten1.getLid()) + "|" + konten1.getNama() + "|" +
                            konten1.getAlamat() + "|" + konten1.getNomorHP() + "|" +
                            konten1.getJenisCucian() + "|" + konten1.getTglAmbil() + "|" + konten1.getTglAntar() +
                            "|" + Integer.toString(konten1.getHarga()) + "|" + Integer.toString(konten1.getBerat()) + "|" +
                            konten1.getPesan() + "|" + konten1.getStatus();
                    writer.println(tmp);
                }
                writer.close();
                return true;
            }
            catch(IOException e){
                e.printStackTrace();
                return false;
            }
        }
    }
    public boolean TulisFile(String namafile, String konten, boolean isCreateNewFile){
        if (isCreateNewFile){
            try{
                PrintWriter writer = new PrintWriter(namafile, "UTF-8");
                writer.println(konten);
                writer.close();
                return true;
            }
            catch(IOException e){
                e.printStackTrace();
                return false;
            }
        }
        else{
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(namafile, true)))){
                writer.println(konten);
                writer.close();
                return true;
            }
            catch(IOException e){
                e.printStackTrace();
                return false;
            }
        }
    }
    public boolean CheckFileExist(String path){
        File f = new File(path);
        if(f.exists() && !f.isDirectory()) { 
            return true;
        }
        else return false;
    }
    public String[] SplitString(String txt){ // with | demiliter
        String[] data = txt.split("\\|");
        return data;
    }
    public String[] SplitString(String txt, String delimiter){ // with | demiliter
        String[] data = txt.split(delimiter);
        return data;
    }
    public boolean GetFileNewer(String filename1, String filename2){
        boolean state = false;
        ArrayList tmp = new ArrayList();
        ArrayList tmp2 = new ArrayList();
        tmp = BacaFile(filename1);
        String a = tmp.get(0).toString();
        String[] b = SplitString(a,"\\#");
        tmp2 = BacaFile(filename1);
        String a2 = tmp2.get(0).toString();
        String[] b2 = SplitString(a2,"\\#");
        
        return state;
    }
}