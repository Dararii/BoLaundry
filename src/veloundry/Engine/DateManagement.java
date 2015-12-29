package veloundry.Engine;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**@author Darari
 */
public class DateManagement {
    DBManagement dbms = new DBManagement();
    public String Message;
    
    public String GetCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        return (dateFormat.format(date)); //2014/08/06 15:59:48
    }
    public String GetMostRecentTime(String date1, String date2){
        String a = date1;
        boolean flag = false;
        String[] tmp1 = dbms.SplitString(date1, "\\ ");
        String[] tgl1 = dbms.SplitString(tmp1[0], "\\/");
        String[] time1 = dbms.SplitString(tmp1[1], "\\:");
        String[] tmp2 = dbms.SplitString(date2, "\\ ");
        String[] tgl2 = dbms.SplitString(tmp2[0], "\\/");
        String[] time2 = dbms.SplitString(tmp2[1], "\\:");
        if (Integer.parseInt(tgl2[2]) <= Integer.parseInt(tgl1[2])) {
            if (Integer.parseInt(tgl2[1]) <= Integer.parseInt(tgl1[1])) {
                if (Integer.parseInt(tgl2[0]) <= Integer.parseInt(tgl1[0])) {
                    flag = true;
                }
            }
        }
        if (flag){
           if (Integer.parseInt(time2[0]) <= Integer.parseInt(time1[0])) {
                if (Integer.parseInt(time2[1]) <= Integer.parseInt(time1[1])) {
                    a = date2;
                }
            }
        }
        return a;
   }
    
    public boolean CekIsDateNewer(Date date1, Date date2){
        if(date1.before(date2)){
            return false;
        }
        else if (date1.after(date2)){
            return true;
        }
        else {
            return false;
        }
    }
    
    public Date CreateDateFormString(String date){
        Date tgl = new Date();
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ENGLISH);
            tgl = format.parse(date);            
        } catch (ParseException ex) {
            Logger.getLogger(DateManagement.class.getName()).log(Level.SEVERE, null, ex);
            setMessage("Error in Parsing");
        }
        finally{
            return tgl;
        }
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
