package veloundry;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import veloundry.Engine.BoEngine;
import veloundry.Engine.Connection;
import veloundry.Engine.DBManagement;
import veloundry.Engine.DateManagement;
import veloundry.Engine.Member;
import veloundry.Engine.Order;

/**
 * FXML Controller class
 *
 * @author Darari
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane apUserMain;
    @FXML
    private AnchorPane apAdminMain;
    @FXML
    private AnchorPane apLoginForm;
    @FXML
    private TextField txtHP;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtUserPass;
    @FXML
    private TextArea txtAlamat;
    @FXML
    private DatePicker dpTglAntar;
    @FXML
    private DatePicker dpTglAmbil;
    @FXML
    private TabPane tabMain;
    @FXML
    private TabPane tabMain1;
    @FXML
    private TabPane tabAbout;
    @FXML
    private Tab TabAmbil;
    @FXML
    private Tab TabAntar;
    @FXML
    private Button btnSendReq;
    @FXML
    private Button btnLogin;
    @FXML
    private Label lblnotifstatus;
    @FXML
    private Label lblLogin;
    @FXML
    private Pane paneAbout;
    @FXML
    private ComboBox cbJenisCucian;
    @FXML
    private ListView<String> lvStatusKategori;
    
            
    private SingleSelectionModel<Tab> selectionModel;
    private SingleSelectionModel<Tab> selectionModel1;
    private SingleSelectionModel<ComboBox> cbSM;
    
    private boolean isAdmin = false;
    private ArrayList userpass = new ArrayList();
    
    private final Connection con = new Connection();
    private final BoEngine useEngine = new BoEngine();
    private final DateManagement dateman = new DateManagement();
    private final DBManagement dbms = new DBManagement();
    private Member member = new Member();
    private final Timer timer = new java.util.Timer();
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //btntest.setContentDisplay(ContentDisplay.TOP);
        txtName.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(32));
        txtHP.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(12));
        
        ObservableList<String> items =FXCollections.observableArrayList (
        "Nama (LID)", "Alamat", "Jenis Cucian", "Berat Cucian", "Tanggal Ambil",
                "Tanggal Antar", "Harga");
        ObservableList<String> jenisCuci = FXCollections.observableArrayList(
        "Cuci Basah", "Cuci Kering", "Cuci Setrika", "Cuci Boneka", 
                "Cuci Gorden/Sprei");
        cbJenisCucian.setItems(jenisCuci);
        lvStatusKategori.setItems(items);
        selectionModel = tabMain.getSelectionModel();
        selectionModel1 = tabMain1.getSelectionModel();
        cbSM = cbJenisCucian.getSelectionModel();
        
        if (this.dbms.CheckFileExist("user.txt") == true){
            this.userpass = this.dbms.BacaFile("user.txt");
        }
        else {
            this.dbms.TulisFile("user.txt", "",true);
        }
        
        timer.schedule(new TimerTask() {
            public void run() {
                 Platform.runLater(new Runnable() {
                    public void run() {
                        con.CekInetConnection("http://albc.ucoz.com");
                        lblnotifstatus.setText(con.getMessage());
                    }
                });
            }
        }, 0, 15000);
    }    
    
    public void About_Click(){
        selectionModel.select(3);
        selectionModel1.select(3);
        paneAbout.setVisible(true);
    }
    public void TabChanged(){
        int a = selectionModel.getSelectedIndex();
        int b = selectionModel1.getSelectedIndex();
        if (a!= 3 || b != 3) {
            paneAbout.setVisible(false);
        } else {
            paneAbout.setVisible(true);
        }
    }
    public void btnSendClick(){
        String nama, alamat, hp, jenis;
        LocalDate ld1, ld2;
        Date date1, date2;
        nama  = txtName.getText();
        alamat = txtAlamat.getText();
        hp = txtHP.getText();
        jenis =(String) cbJenisCucian.getValue();
        ld1 = dpTglAmbil.getValue();
        ld2 = dpTglAntar.getValue();        
        if (nama != null && alamat != null && hp != null && jenis != null){
            if (ld1 != null && ld2 != null){
                date1 = dateman.CreateDateFromLocaDate(ld1);
                date2 = dateman.CreateDateFromLocaDate(ld2);
                boolean a = dateman.CekIsDateNewer(date1, date2);
                if (!a) {
                    ShowDialog("Information", "Tolong periksa lagi tanggal ambil dan antar", AlertType.INFORMATION);
                } else{
                    boolean b;
                    b = useEngine.AddNewOrder(nama, alamat, hp, jenis, dateman.CreateStringFormDate(date1), dateman.CreateStringFormDate(date2));
                    if (b) {
                        ShowDialog("Information", useEngine.getMessage(), AlertType.INFORMATION);
                        ResetForm();
                    } else {
                        ShowDialog("Information", useEngine.getMessage(), AlertType.INFORMATION);
                    }
                }
            }
            else {
                Date zz = new Date();
                Calendar c = Calendar.getInstance(); 
                c.setTime(zz); 
                c.add(Calendar.DATE, 1);
                zz = c.getTime();
                boolean b;
                b = useEngine.AddNewOrder(nama, alamat, hp, jenis, dateman.GetCurrentDate(), dateman.CreateStringFormDate(zz));
                if (b) {
                    ShowDialog("Information", useEngine.getMessage(), AlertType.INFORMATION);
                    ResetForm();
                } else {
                    ShowDialog("Information", useEngine.getMessage(), AlertType.INFORMATION);
                }
            }
        }
        else{
            ShowDialog("Information", "Tolong isi data dengan lengkap", AlertType.INFORMATION);
        }
    }
    public void LoginCLicked(){
        //ShowDialog("aa", userpass.toString(),AlertType.INFORMATION);
        String username, pass;
        username = txtUserName.getText();
        pass = txtUserPass.getText();
        this.isAdmin = member.AdminLogin(username, pass, userpass);
        if (this.isAdmin){
            apAdminMain.setVisible(true);
            apUserMain.setVisible(false);
            apLoginForm.setVisible(false);
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), apAdminMain);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
            lblLogin.setText("Keluar");
            txtUserName.clear();
            txtUserPass.clear();
            TabChanged();
            //ShowDialog("Login Information", member.getMessage(),AlertType.INFORMATION);
        } else{
            ShowDialog("Login Information", member.getMessage(),AlertType.INFORMATION);
        }
    }
    public void ShowLoginForm(){
        String a = lblLogin.getText();
        if(a.equalsIgnoreCase("keluar")){
            this.isAdmin = false;
            lblLogin.setText("Masuk");
            apAdminMain.setVisible(false);
            apUserMain.setVisible(true);
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), apUserMain);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
            apLoginForm.setVisible(false);
        } else{
            paneAbout.setVisible(false);
            apAdminMain.setVisible(false);
            apUserMain.setVisible(false);
            apLoginForm.setVisible(true);
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), apLoginForm);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(1.0);
            fadeTransition.play();
        }
    }
    public void BackFromLoginForm(){
        apUserMain.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), apUserMain);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
        apLoginForm.setVisible(false);
        TabChanged();
    }
    
    public void ResetForm(){
        txtName.clear();
        txtAlamat.clear();
        txtHP.clear();
        cbSM.clearSelection();
        dpTglAmbil.setValue(null);
        dpTglAntar.setValue(null);
    }
    
    public void ShowDialog(String tittle, String message, AlertType a){
        Alert alert = new Alert(a);
        alert.setTitle(tittle);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /* Numeric Validation Limit the  characters to maxLengh AND to ONLY DigitS*/
    public EventHandler<KeyEvent> numeric_Validation(final Integer max_Lengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField txt_TextField = (TextField) e.getSource();                
                if (txt_TextField.getText().length() >= max_Lengh) {                    
                    e.consume();
                }
                if(e.getCharacter().matches("[0-9.]")){ 
                    if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")){
                        e.consume();
                    }else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")){
                        e.consume(); 
                    }
                }else{
                    e.consume();
                }
            }
        };
    }    
    /* Letters Validation Limit the  characters to maxLengh AND to ONLY Letters */
    public EventHandler<KeyEvent> letter_Validation(final Integer max_Lengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField txt_TextField = (TextField) e.getSource();                
                if (txt_TextField.getText().length() >= max_Lengh) {                    
                    e.consume();
                }
                if(e.getCharacter().matches("[A-Za-z]") || e.getCharacter().matches(" ")){ 
                }else{
                    e.consume();
                }
            }
        };
    } 
}
