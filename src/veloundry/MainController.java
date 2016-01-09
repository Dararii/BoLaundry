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
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TextField txtHarga;
    @FXML
    private TextField txtBerat;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextArea txtPesan;
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
    private ProgressIndicator pbOrder;
    
    @FXML
    private TableView<Order> TabelNewOrder;
    @FXML
    private TableColumn<Order, Integer> tnolid;
    @FXML
    private TableColumn<Order, String> tnonama;
    @FXML
    private TableColumn<Order, String> tnoalamat;
    @FXML
    private TableColumn<Order, String> tnohp;
    @FXML
    private TableColumn<Order, String> tnoJenis;
    @FXML
    private TableColumn<Order, String> tnotglAmbil;
    @FXML
    private TableColumn<Order, String> tnotglAntar;
   
    @FXML
    private TableView<Order> TabelKonfirmedOrder;
    @FXML
    private TableColumn<Order, Integer> tkolid;
    @FXML
    private TableColumn<Order, String> tkonama;
    @FXML
    private TableColumn<Order, String> tkoalamat;
    @FXML
    private TableColumn<Order, String> tkohp;
    @FXML
    private TableColumn<Order, String> tkojenis;
    @FXML
    private TableColumn<Order, String> tkotglAntar;
    @FXML
    private TableColumn<Order, Integer> tkoharga;
    
    @FXML
    private ListView<String> lvStatusKategori;
            
    private SingleSelectionModel<Tab> selectionModel;
    private SingleSelectionModel<Tab> selectionModel1;
    private SingleSelectionModel<ComboBox> cbSM;
    private ArrayList arraydatamentah;
    private ArrayList arraydata;
    private ArrayList arraydatakonfirm;
    private ObservableList<Order> data;
    private ObservableList<Order> datakonfirm;
    private ArrayList arraydatacomplete;
    private ObservableList<Order> datacomplete;
    
    private SimpleDoubleProperty prop = new SimpleDoubleProperty(0);
    private boolean isAdmin = false;
    private boolean isConnected = false;
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
        txtHarga.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(7));
        txtBerat.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(2));
        
        ObservableList<String> items =FXCollections.observableArrayList (
        "Nama (LID)", "Alamat", "Jenis Cucian", "Tanggal Ambil",
                "Tanggal Antar", "Berat Cucian", "Harga", "Catatan");
        ObservableList<String> jenisCuci = FXCollections.observableArrayList(
        "Cuci Basah", "Cuci Kering", "Cuci Setrika", "Cuci Boneka", 
                "Cuci Gorden/Sprei");
        cbJenisCucian.setItems(jenisCuci);
        lvStatusKategori.setItems(items);
        selectionModel = tabMain.getSelectionModel();
        selectionModel1 = tabMain1.getSelectionModel();
        cbSM = cbJenisCucian.getSelectionModel();
        pbOrder.progressProperty().bind(prop);
        CekFileAtStart();
        
        timer.schedule(new TimerTask() {
            public void run() {
                 Platform.runLater(new Runnable() {
                    public void run() {
                        isConnected = con.CekInetConnection("http://albc.ucoz.com");
                        lblnotifstatus.setText(con.getMessage());
                    }
                });
            }
        }, 0, 15000);
    }    
    
    public void CekFileAtStart(){
        if (this.dbms.CheckFileExist("user.txt") == true){
            this.userpass = this.dbms.BacaFile("user.txt");
        }
        else {
            this.dbms.TulisFile("user.txt", "",true);
        }
        
        int z = this.useEngine.GetFileFromServer("Order.txt", "Darari");
        if (z != 2){
            if (this.dbms.CheckFileExist("Order.txt") == true){
                //this.userpass = this.dbms.BacaFile("order_acc.txt");
            }
            else {
                String a = dateman.GetCurrentDate();
                this.dbms.TulisFile("Order.txt", a,true);
            }
        }
        
        z = this.useEngine.GetFileFromServer("order_acc.txt", "Darari");
        if (z != 2){
            if (this.dbms.CheckFileExist("order_acc.txt") == true){
                //this.userpass = this.dbms.BacaFile("order_acc.txt");
            }
            else {
                String a = dateman.GetCurrentDate();
                this.dbms.TulisFile("order_acc.txt", a,true);
            }
        }
    }
    
    public void About_Click(){
        if (!apLoginForm.isVisible()){
            selectionModel.select(3);
            selectionModel1.select(3);
            paneAbout.setVisible(true);
        }
    }
    public void TabChanged(){
        int a = selectionModel.getSelectedIndex();
        int b = selectionModel1.getSelectedIndex();
        if (a!= 3 || b != 3) {
            paneAbout.setVisible(false);
        } else {
            paneAbout.setVisible(true);
        }
        if (b == 0){
            arraydatamentah = this.dbms.BacaFile("Order.txt");
            arraydata = useEngine.GetNewOrder();
            data = FXCollections.observableArrayList(arraydata);
            UpdateTable(TabelNewOrder, data);
        } else if (b == 1){
            arraydatakonfirm = useEngine.GetConfirmedOrder();
            datakonfirm = FXCollections.observableArrayList(arraydatakonfirm);
            UpdateTableKonfirm(this.TabelKonfirmedOrder, datakonfirm);
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
        String username, pass;
        username = txtUserName.getText();
        pass = txtUserPass.getText();
        this.isAdmin = member.AdminLogin(username, pass, userpass);
        if (this.isAdmin){
            apAdminMain.setVisible(true);
            apUserMain.setVisible(false);
            apLoginForm.setVisible(false);
            FadeInAnimation(apAdminMain);
            lblLogin.setText("Keluar");
            txtUserName.clear();
            txtUserPass.clear();
            selectionModel1.select(0);
            TabChanged();
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
            FadeInAnimation(apUserMain);
            apLoginForm.setVisible(false);
        } else{
            paneAbout.setVisible(false);
            apAdminMain.setVisible(false);
            apUserMain.setVisible(false);
            apLoginForm.setVisible(true);
            FadeInAnimation(apLoginForm);
        }
    }
    public void BackFromLoginForm(){
        apUserMain.setVisible(true);
        FadeInAnimation(apUserMain);
        apLoginForm.setVisible(false);
        TabChanged();
    }
    
    public void KonfirmOrder(){
        if(TabelNewOrder.getSelectionModel().isEmpty()){
            ShowDialog("Information", "Tolong pilih salah satu order", AlertType.INFORMATION);
        }else{
            int intlid = data.get(TabelNewOrder.getSelectionModel().getSelectedIndex()).getLid();
            String strName = data.get(TabelNewOrder.getSelectionModel().getSelectedIndex()).getNama();
            String strAlamat = data.get(TabelNewOrder.getSelectionModel().getSelectedIndex()).getAlamat();
            String strHP = data.get(TabelNewOrder.getSelectionModel().getSelectedIndex()).getNomorHP();
            String strTglAntar = data.get(TabelNewOrder.getSelectionModel().getSelectedIndex()).getTglAntar();
            String strTglAmbil = data.get(TabelNewOrder.getSelectionModel().getSelectedIndex()).getTglAmbil();
            String strJenis = data.get(TabelNewOrder.getSelectionModel().getSelectedIndex()).getJenisCucian();
            int intharga;
            String strPesan = "";
            if(!txtPesan.getText().isEmpty()){
                strPesan = txtPesan.getText();
                String toWrite = Integer.toString(intlid) + "|" + strPesan;
                //dbms.TulisFile("pesan", toWrite, false);
            }
            try{
                int p0 = useEngine.GetFileFromServer("Order.txt", "Darari");
                int p1 = useEngine.GetFileFromServer("order_acc.txt", "Darari");
                arraydatakonfirm = useEngine.GetConfirmedOrder();
                datakonfirm = FXCollections.observableArrayList(arraydatakonfirm);
                datakonfirm.add(new Order(intlid, strName, strAlamat, strHP, strJenis,strTglAmbil, strTglAntar, 0, 0, strPesan));
                arraydatakonfirm.add(new Order(intlid, strName, strAlamat, strHP, strJenis,strTglAmbil, strTglAntar, 0, 0, strPesan));
                arraydata.remove(TabelNewOrder.getSelectionModel().getSelectedIndex());
                arraydatamentah.remove(0);
                arraydatamentah.remove(TabelNewOrder.getSelectionModel().getSelectedIndex());
                data.remove(TabelNewOrder.getSelectionModel().getSelectedIndex());
                UpdateTableKonfirm(TabelKonfirmedOrder, datakonfirm);
                UpdateTable(TabelNewOrder, data);
                dbms.TulisFile("order_acc.txt", dateman.GetCurrentDate(), true);
                dbms.TulisFile("order_acc.txt", datakonfirm, false);
                dbms.TulisFile("Order.txt", dateman.GetCurrentDate(), true);
                dbms.TulisFile("Order.txt", arraydatamentah, false);
                boolean p2 = useEngine.PutFileToServer("order_acc.txt", "Darari");
                boolean p3 = useEngine.PutFileToServer("Order.txt", "Darari");
                if (p0 != 2 || p1 != 2 || !p2 || !p3){
                    ShowDialog("Error", "Oopss !! Something Wrong in Connection : " + this.useEngine.getMessage(),AlertType.ERROR);
                }
                if (!this.useEngine.isCurrentState()) {
                    ShowDialog("Error", "Error Message : " + this.useEngine.getMessage(),AlertType.ERROR);
                }
            } catch(Exception e){
                ShowDialog("Error", "Error : " + e.getMessage(),AlertType.ERROR);
            }
        }
    }
    
    public void UpdateHarga(){
        if(TabelKonfirmedOrder.getSelectionModel().isEmpty()){
            ShowDialog("Information", "Tolong pilih salah satu order", AlertType.INFORMATION);
        }else{
            int intlid = datakonfirm.get(TabelKonfirmedOrder.getSelectionModel().getSelectedIndex()).getLid();
            String strName = datakonfirm.get(TabelKonfirmedOrder.getSelectionModel().getSelectedIndex()).getNama();
            String strAlamat = datakonfirm.get(TabelKonfirmedOrder.getSelectionModel().getSelectedIndex()).getAlamat();
            String strHP = datakonfirm.get(TabelKonfirmedOrder.getSelectionModel().getSelectedIndex()).getNomorHP();
            String strTglAntar = datakonfirm.get(TabelKonfirmedOrder.getSelectionModel().getSelectedIndex()).getTglAntar();
            String strJenis = datakonfirm.get(TabelKonfirmedOrder.getSelectionModel().getSelectedIndex()).getJenisCucian();
            String strTglAmbil = datakonfirm.get(TabelKonfirmedOrder.getSelectionModel().getSelectedIndex()).getTglAmbil();
            String strPesan = datakonfirm.get(TabelKonfirmedOrder.getSelectionModel().getSelectedIndex()).getPesan();
            int intharga;
            int intberat;
            try{
                if ((this.txtHarga.getText() == null || this.txtHarga.getText().trim().isEmpty()) || 
                        (this.txtBerat.getText() == null || this.txtBerat.getText().trim().isEmpty())){
                    ShowDialog("Information", "Tolong isi Harga dan berat untuk update harga", AlertType.INFORMATION);
                }
                else{
                    intharga = Integer.parseInt(this.txtHarga.getText());
                    intberat = Integer.parseInt(this.txtBerat.getText());
                    int indexi = TabelKonfirmedOrder.getSelectionModel().getSelectedIndex();
                    datakonfirm.set(indexi, new Order(intlid, strName, strAlamat, strHP, strJenis,strTglAmbil, strTglAntar, intharga, intberat, strPesan));
                    arraydatakonfirm.set(indexi, new Order(intlid, strName, strAlamat, strHP, strJenis,strTglAmbil, strTglAntar, intharga, intberat, strPesan));
                    UpdateTableKonfirm(TabelKonfirmedOrder, datakonfirm);
                    boolean p0 = dbms.TulisFile("order_acc.txt", dateman.GetCurrentDate(), true);
                    boolean p1 = dbms.TulisFile("order_acc.txt", datakonfirm, false);
                    boolean p2 = useEngine.PutFileToServer("order_acc.txt", "Darari");
                    boolean p3 = useEngine.PutFileToServer("Order.txt", "Darari");
                    if (!p0 || !p1 || !p2 || !p3){
                        ShowDialog("Error", "Oopss !! Something Wrong in Connection or File Write : " + this.useEngine.getMessage(),AlertType.ERROR);
                    }
                    if (!this.useEngine.isCurrentState()) {
                        ShowDialog("Error", "Error Message : " + this.useEngine.getMessage(),AlertType.ERROR);
                    }
                }
            } catch(Exception e){
                ShowDialog("Error", "Error : " + e.getMessage(),AlertType.ERROR);
            }
        }
    }
    
    public void UpdateTable(TableView table, ObservableList<Order> list){
        tnolid.setCellValueFactory(new PropertyValueFactory<Order, Integer>("lid"));
        tnonama.setCellValueFactory(new PropertyValueFactory<Order, String>("nama"));
        tnoalamat.setCellValueFactory(new PropertyValueFactory<Order, String>("alamat"));
        tnohp.setCellValueFactory(new PropertyValueFactory<Order, String>("nomorHP"));
        tnoJenis.setCellValueFactory(new PropertyValueFactory<Order, String>("jenisCucian"));
        tnotglAmbil.setCellValueFactory(new PropertyValueFactory<Order, String>("tglAmbil"));
        tnotglAntar.setCellValueFactory(new PropertyValueFactory<Order, String>("tglAntar"));
        table.setItems(list);
    }
    public void UpdateTableKonfirm(TableView table, ObservableList<Order> list){
        tkolid.setCellValueFactory(new PropertyValueFactory<Order, Integer>("lid"));
        tkonama.setCellValueFactory(new PropertyValueFactory<Order, String>("nama"));
        tkoalamat.setCellValueFactory(new PropertyValueFactory<Order, String>("alamat"));
        tkohp.setCellValueFactory(new PropertyValueFactory<Order, String>("nomorHP"));
        tkojenis.setCellValueFactory(new PropertyValueFactory<Order, String>("jenisCucian"));
        tkotglAntar.setCellValueFactory(new PropertyValueFactory<Order, String>("tglAntar"));
        tkoharga.setCellValueFactory(new PropertyValueFactory<Order, Integer>("harga"));
        table.setItems(list);
    }
    
    public void FadeInAnimation(AnchorPane obj){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), obj);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
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
