package veloundry.Engine;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
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
    public String CreateStringFormDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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
            return true;
        }
        else if (date1.after(date2)){
            return false;
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
    
    public Date CreateDateFromLocaDate(LocalDate ld1){
        Date date1;
        Instant instant = ld1.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        date1 = Date.from(instant);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentdate =  sdf.format(cal.getTime());
        int hh = Integer.parseInt(currentdate.split(":")[0]);
        int mm = Integer.parseInt(currentdate.split(":")[1]);
        calendar .add(Calendar.HOUR_OF_DAY, hh);
        calendar .add(Calendar.MINUTE, mm);
        date1 = calendar.getTime();
        return date1;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
