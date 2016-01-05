package veloundry.Engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author Darari
 */
public class Connection {
    private String message;

    public boolean CekInetConnection(String urlHOST){
        boolean state;
        try {
            URL url = new URL(urlHOST);
            //setMessage("Connecting to " + url.getHost());
            HttpURLConnection con = (HttpURLConnection) url
                            .openConnection();
            con.connect();
            if (con.getResponseCode() == 200){
                setMessage("Status : Connected with Server");
            }
            state = true;
        } catch (Exception exception) {
            setMessage("Status : Disconnected from Server");
            state = false;
        }
        return state;
    }
    
    //FTP base upload file
    public boolean UploadFile(String filename, String dirnamewithSlash, String host, String username, String pass){
        boolean state = false;
        String ftpUrl = "ftp://%s:%s@%s/%s;type=i";
        String filePath = filename;
        String uploadPath = "/" + dirnamewithSlash + filename;

        ftpUrl = String.format(ftpUrl, username, pass, host, uploadPath);
        setMessage("Upload URL: " + ftpUrl);

        try {
            URL url = new URL(ftpUrl);
            URLConnection conn = url.openConnection();
            OutputStream outputStream = conn.getOutputStream();
            FileInputStream inputStream = new FileInputStream(filePath);

            byte[] buffer = new byte[256];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();

            setMessage("Sinkronasi sukses !");
            state = true;
        } 
        catch (IOException ex) {
            ex.printStackTrace();
            setMessage(ex.getMessage());
        }
        return state;
    }
    
    public boolean DownloadFile(String url, String filepath){
        FileOutputStream fos = null;
        boolean state = false;
        try {
            URL website = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            fos = new FileOutputStream(filepath);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            state = true;
            setMessage("File Downloaded");
            state = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            setMessage("File Not Found");
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            setMessage("IO Exception");
        }
        return state;
    }
    
    public boolean CreateDirOnServer(String dirname, String host, String username, String pass){
        String server = host;
        int port = 21;
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            //showServerReply(ftpClient);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                setMessage("Operation failed. Server reply code: " + replyCode);
            }
            boolean success = ftpClient.login(username, pass);
            //showServerReply(ftpClient);
            if (!success) {
                setMessage("Could not login to the server");
            }
            // Creates a directory
            String dirToCreate = "/" + dirname;
            success = ftpClient.makeDirectory(dirToCreate);
            //showServerReply(ftpClient);
            if (success) {
                setMessage("Successfully created directory: " + dirToCreate);
            } else {
                setMessage("Failed to create directory. See server's reply.");
            }
            // logs out
            ftpClient.logout();
            ftpClient.disconnect();
            return true;
        } catch (IOException ex) {
            setMessage("Oops! Something wrong happened\n" + ex.getMessage());
            return false;
        }
    }
    
    private String[] showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            setMessage("Null");
        }
        return replies;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
