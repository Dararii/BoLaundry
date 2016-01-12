package veloundry;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
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
    private AnchorPane apLoading;
    @FXML
    private AnchorPane apChooseLaundry;
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
    private TextField txtPesan;
    @FXML
    private PasswordField txtUserPass;
    @FXML
    private TextField txtAlamat;
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
    private ProgressIndicator pbLoad;
    @FXML
    private ProgressIndicator pbSegarkan;
    @FXML
    private Label lblLoadStat;
    @FXML
    private Label lblAlamatLau;
    @FXML
    private Label lblHPLaundry;
    @FXML
    private Label lblGantiLaundry;
    @FXML
    private ComboBox cbPilihLaundry;
    @FXML
    private Label lblTentang;
    @FXML
    private Rectangle rectTentang;
    
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
    private TableView<Order> TabelCompletedOrder;
    @FXML
    private TableColumn<Order, Integer> tcolid;
    @FXML
    private TableColumn<Order, String> tcohp;
    @FXML
    private TableColumn<Order, String> tcotglAmbil;
    @FXML
    private TableColumn<Order, String> tcotglAntar;
    @FXML
    private TableColumn<Order, Integer> tcoharga;
    
    @FXML
    private TextField txtHargaBasah;
    @FXML
    private TextField txtHargaKering;
    @FXML
    private TextField txtHargaSetrika;
    @FXML
    private TextField txtHargaBoneka;
    @FXML
    private Button btnSetHarga;
    @FXML
    private Label lblNamaLaundry;
    @FXML
    private Label lblAlamatLaundry;
    @FXML
    private Label lblhcount;
    @FXML
    private Label lblhsaldo;
    
    @FXML
    private Label lblhargakering;
    @FXML
    private Label lblhargabasah;
    @FXML
    private Label lblhargasetrika;
    @FXML
    private Label lblhargaboneka;
    @FXML
    private Label lblhargaseprai;
    @FXML
    private Label lblStatus;
    @FXML
    private Label lblPartner;
    @FXML
    private ProgressIndicator pbCekStat;
    @FXML
    private ProgressIndicator pbPilihAnim;
    @FXML
    private ListView<String> lvStatus;
    @FXML
    private TextField idLaundry;
    
    private ObservableList<String> datauserview;
    private ArrayList dataview;
    
    private SingleSelectionModel<Tab> selectionModel;
    private SingleSelectionModel<Tab> selectionModel1;
    private SingleSelectionModel<ComboBox> cbSM;
    private SingleSelectionModel<ComboBox> cbSM1;
    private ArrayList arraydatamentah;
    private ArrayList arraydata;
    private ArrayList arraydatakonfirm;
    private ObservableList<Order> data;
    private ObservableList<Order> datakonfirm;
    private ArrayList arraydatacomplete;
    private ObservableList<Order> datacomplete;
    private ObservableList<String> temp;
    
    private SimpleDoubleProperty prop = new SimpleDoubleProperty(0);
    private boolean isAdmin = false;
    private String adminName;
    private String laundryName;
    private int laundryIndex = -1;
    private boolean isConnected = false;
    private boolean test1 = false;
    private boolean test2 = false;
    private ArrayList userpass = new ArrayList();
    
    private final Connection con = new Connection();
    private final BoEngine useEngine = new BoEngine();
    private final DateManagement dateman = new DateManagement();
    private final DBManagement dbms = new DBManagement();
    private final Member member = new Member();
    private final Timer timer = new java.util.Timer();
    
    private String[] alamat;
    private String[] hp;
    private String[] nama;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblGantiLaundry.setVisible(false);
        lblLogin.setVisible(false);
        lblTentang.setVisible(false);
        rectTentang.setVisible(false);
        txtName.addEventFilter(KeyEvent.KEY_TYPED, letter_Validation(32));
        txtHP.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(12));
        txtHarga.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(7));
        txtBerat.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(2));
        this.txtHargaBasah.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(7));
        this.txtHargaKering.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(7));
        this.txtHargaSetrika.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(7));
        this.txtHargaBoneka.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(7));
        this.idLaundry.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(3));
        
        ObservableList<String> jenisCuci = FXCollections.observableArrayList(
        "Cuci Basah", "Cuci Kering", "Cuci Setrika", "Cuci Boneka", 
                "Cuci Gorden/Sprei");
        cbJenisCucian.setItems(jenisCuci);
        
        ApplicationPrepare();
        
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
    
    public void ApplicationPrepare(){
        final Task task;
        this.apLoading.setVisible(true);
        task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try{
                    updateProgress(0.2, 1);
                    updateMessage("Building UI");
                    selectionModel = tabMain.getSelectionModel();
                    selectionModel1 = tabMain1.getSelectionModel();
                    cbSM = cbJenisCucian.getSelectionModel();
                    Thread.sleep(4000);
                    updateProgress(0.5, 1);
                    updateMessage("Checking File Environment");
                    CekFileAtStart();
                    Thread.sleep(1000);
                    updateProgress(0.8, 1);
                    updateMessage("Building Database");
                    alamat = member.getUseralamat();
                    hp = member.getUserhp();
                    nama = member.getUserlaundryname();
                    ArrayList tmp = new ArrayList();
                    int index = 0;
                    for (int i = 0; i < nama.length;i++){
                        if (nama[i] != null){
                            tmp.add(nama[i]);
                        }
                    }
                    temp = FXCollections.observableArrayList(tmp);
                    cbPilihLaundry.setItems(temp);
                    Thread.sleep(1000);
                    updateProgress(1, 1);
                    updateMessage("Application Ready");
                    Thread.sleep(2000);
                    apLoading.setVisible(false);
                    apUserMain.setVisible(false);
                    apAdminMain.setVisible(false);
                    apLoginForm.setVisible(false);
                    apChooseLaundry.setVisible(true);
                    FadeInAnimation(apChooseLaundry);
                }
                catch(Exception e){
                    e.printStackTrace();
                    updateMessage("Exception Error");
                    return null;
                }
                return null;
            }
        };
        this.pbLoad.progressProperty().bind(task.progressProperty());
        this.lblLoadStat.textProperty().bind(task.messageProperty());
        this.cbPilihLaundry.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> temp, String oldValue, String newValue) {
                for (int i = 0; i< nama.length;i++){
                    if (nama[i] != null){
                        if (newValue.equalsIgnoreCase(nama[i])){
                            lblHPLaundry.setText(hp[i]);
                            lblAlamatLau.setText(alamat[i]);
                            laundryIndex = i;
                        }
                    }
                }
            }
        });
        new Thread(task).start();
    }
    
    public void ChooseLaundry(){
        final Task task;
        if (this.laundryIndex != -1){
            task = new Task<Void>(){
                @Override
                protected Void call() throws Exception {
                    try{
                        pbPilihAnim.setVisible(true);
                        laundryName = nama[laundryIndex];
                        updateMessage(laundryName);
                        adminName = member.getUsernama(laundryIndex);
                        GetDBindividu();
                        apLoading.setVisible(false);
                        apAdminMain.setVisible(false);
                        apLoginForm.setVisible(false);
                        paneAbout.setVisible(false);
                        apChooseLaundry.setVisible(false);
                        pbPilihAnim.setVisible(false);
                        lvStatus.getSelectionModel().clearSelection();
                        lvStatus.getItems().clear();
                        apUserMain.setVisible(true);
                        FadeInAnimation(apUserMain);
                        lblGantiLaundry.setVisible(true);
                        lblLogin.setVisible(true);
                        lblTentang.setVisible(true);
                        rectTentang.setVisible(true);
                    } catch(Exception e){
                        
                    }
                    return null;
                }
            };
            this.lblPartner.textProperty().bind(task.messageProperty());
            new Thread(task).start();
        }else {
            ShowDialog("Info", "Tolong pilih layanan laundry dahulu", AlertType.INFORMATION);
        }
    }
    
    public void BackToChooseLaundry(){
        apLoading.setVisible(false);
        apUserMain.setVisible(false);
        apAdminMain.setVisible(false);
        apLoginForm.setVisible(false);
        paneAbout.setVisible(false);
        apChooseLaundry.setVisible(true);
        lblGantiLaundry.setVisible(false);
        lblLogin.setVisible(false);
        lblTentang.setVisible(false);
        rectTentang.setVisible(false);
        FadeInAnimation(apChooseLaundry);
    }
    
    public void Register(){
        ShowDialog("Information Registration", "Segera daftarkan usaha laundry Anda untuk didukung oleh"
                + " layanan BO Laundry !\nKirimkan data diri Anda serta rincian nama usaha Anda ke :\n"
                + "-cs@essensift.com\n-Datang langsung ke Kantor kami di BME F-199 Surabaya.", AlertType.INFORMATION);
    }
    
    public void CekFileAtStart(){
        
        this.con.DownloadFile("http://albc.ucoz.com/user.txt", "user.txt");
        if (this.dbms.CheckFileExist("user.txt") == true){
            this.userpass = this.dbms.BacaFile("user.txt");
            member.LoadData(this.userpass);
        }
        else {
            this.dbms.TulisFile("user.txt", "Darari|indi|Bo Landry|Kunir No. 2|085749492249",true);
        }
    }
    
    public void RefreshListAdmin(){
        final Task task;
        task = new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                try{
                    pbSegarkan.setVisible(true);
                    GetDBindividu();
                    TabChanged();
                    pbSegarkan.setVisible(false);
                } catch(Exception e) {
                    e.printStackTrace(System.out);
                }
                return null;
            }
        };
        new Thread(task).start();
    }
    
    private void GetDBindividu(){
        int z = this.useEngine.GetFileFromServer("Order.txt", this.adminName);
        if (z != 2){
            if (this.dbms.CheckFileExist("Order.txt") == true){

            }
            else {
                String a = dateman.GetCurrentDate();
                this.dbms.TulisFile("Order.txt", a,true);
            }
        }
        
        z = this.useEngine.GetFileFromServer("order_acc.txt", this.adminName);
        if (z != 2){
            if (this.dbms.CheckFileExist("order_acc.txt") == true){

            }
            else {
                String a = dateman.GetCurrentDate();
                this.dbms.TulisFile("order_acc.txt", a,true);
            }
        }
        
        boolean xx = this.con.DownloadFile("http://albc.ucoz.com/" + this.adminName + "/harga.txt", "harga.txt");
        if (!xx){
            ArrayList a = new ArrayList();
            a.add("Rp 2000/Kg");
            a.add("Rp 2500/Kg");
            a.add("Rp 3000/Kg");
            a.add("Rp 10000/Item");
            this.dbms.TulisFile("harga.txt", a,true);
            member.LoadHarga();
        } else {
            member.LoadHarga();
        }
    }
    
    public void AddPromptUpdateHarga(){
        txtHargaBasah.setPromptText(member.getHargabasah());
        txtHargaKering.setPromptText(member.getHargakering());
        txtHargaSetrika.setPromptText(member.getHargasetrika());
        txtHargaBoneka.setPromptText(member.getHargaboneka());
        lblhargakering.setText(member.getHargakering());
        lblhargabasah.setText(member.getHargabasah());
        lblhargasetrika.setText(member.getHargasetrika());
        lblhargaboneka.setText(member.getHargaboneka());
        lblhargaseprai.setText(member.getHargaboneka());
    }
    
    public void UpdatePatokanHarga(){
        String a, b, c, d, a1, a2, a3, a4;
        a = member.getHargabasah();
        b = member.getHargakering();
        c = member.getHargasetrika();
        d = member.getHargaboneka();
        a1 = txtHargaBasah.getText();
        a2 = txtHargaKering.getText();
        a3 = txtHargaSetrika.getText();
        a4 = txtHargaBoneka.getText();
        if (!txtHargaBasah.getText().isEmpty()) a = "Rp " + a1 + "/Kg";
        if (!txtHargaKering.getText().isEmpty()) b = "Rp " + a2 + "/Kg";
        if (!txtHargaSetrika.getText().isEmpty()) c = "Rp " + a3 + "/Kg";
        if (!txtHargaBoneka.getText().isEmpty()) d = "Rp " + a4 + "/Item";
        member.setHargabasah(a);
        member.setHargakering(b);
        member.setHargasetrika(c);
        member.setHargaboneka(d);
        ArrayList v = new ArrayList();
        v.add(a);
        v.add(b);
        v.add(c);
        v.add(d);
        dbms.TulisFile("harga.txt", v, true);
        AddPromptUpdateHarga();
        txtHargaBasah.clear();
        txtHargaKering.clear();
        txtHargaSetrika.clear();
        txtHargaBoneka.clear();
        final Task task;
        task = new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                try{
                    con.UploadFile("harga.txt", adminName + "/", "albc.ucoz.com", "dalbc", "darari15");
                } catch(Exception e){
                    ShowDialog("Error", "Error when Sync : " + e.getMessage(), AlertType.ERROR);
                }
                return null;
            }           
        };
        new Thread(task).start();
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
        if (a == 2){
            AddPromptUpdateHarga();
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
            
            this.arraydatacomplete = useEngine.GetCompletedOrder();
            this.datacomplete = FXCollections.observableArrayList(this.arraydatacomplete);
        }else if (b == 2){
            this.arraydatacomplete = useEngine.GetCompletedOrder();
            this.datacomplete = FXCollections.observableArrayList(arraydatacomplete);
            UpdateTableComplete(this.TabelCompletedOrder, this.datacomplete);
            this.lblhcount.setText(Integer.toString(this.datacomplete.size()));
            int saldo = 0;
            for (Order datacomplete1 : this.datacomplete) {
                saldo += datacomplete1.getHarga();
            }
            lblNamaLaundry.setText(laundryName);
            lblAlamatLaundry.setText(alamat[laundryIndex]);
            this.lblhsaldo.setText(Integer.toString(saldo));
            AddPromptUpdateHarga();
        }
    }
    
    public void CekMyLaundry(){
        final Task task;
        lvStatus.getItems().clear();
        lvStatus.getSelectionModel().clearSelection();
        if ((this.idLaundry.getText() == null || this.idLaundry.getText().trim().isEmpty())){
            ShowDialog("Info", "Tolong Masukkan Code Laundry Anda !", AlertType.INFORMATION);
            pbCekStat.setProgress(0);
            lblStatus.setText("Nothing");
        } else{
            task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try{
                        boolean state = false;
                        String datanya = "";
                        int lcode = Integer.parseInt(idLaundry.getText());
                        int dblcode;
                        updateProgress(0.1, 1);
                        updateMessage("Cek Koneksi");
                        boolean a = con.CekInetConnection("http://albc.ucoz.com");
                        Thread.sleep(1000);
                        if (a){
                            updateProgress(0.2, 1);
                            updateMessage("Connected !!");
                            Thread.sleep(500);
                            updateProgress(0.3, 1);
                            updateMessage("Sinkronasi..");
                            Thread.sleep(900);
                            int b = useEngine.GetFileFromServer("order_acc.txt", adminName);
                            if (b==2){
                                Thread.sleep(1000);
                                updateProgress(0.4, 1);
                                updateMessage("Reading file..");
                                dataview = dbms.BacaFile("order_acc.txt");
                                ListIterator iter = dataview.listIterator();
                                int i = 0;
                                while(iter.hasNext()){
                                    if (i != 0){
                                        String z = iter.next().toString();
                                        String[] tmp = dbms.SplitString(z);
                                        dblcode = Integer.parseInt(tmp[0]);
                                        if (dblcode == lcode) {
                                            datanya = z;
                                            state = true;
                                            Thread.sleep(1000);
                                            updateProgress(0.7, 1);
                                            updateMessage("data found");
                                            break;
                                        }
                                    }
                                    else{
                                        iter.next();
                                    }
                                    i++;
                                }
                                if (state){
                                    Thread.sleep(1000);
                                    updateProgress(0.85, 1);
                                    updateMessage("Parsing..");
                                    ObservableList<String> items =FXCollections.observableArrayList (
                                        "LID", "Nama", "Alamat","Nomor HP", "Jenis Cucian", "Tanggal Ambil",
                                        "Tanggal Antar", "Harga", "Berat Cucian", "Catatan", "Status");
                                    String[] tmp = dbms.SplitString(datanya);
                                    ArrayList x = new ArrayList();
                                    for(int j = 0;j<tmp.length;j++){
                                        x.add(items.get(j) + " : " + tmp[j]);
                                    }
                                    Thread.sleep(1000);
                                    updateProgress(0.95, 1);
                                    updateMessage("Building data..");
                                    datauserview = FXCollections.observableArrayList(x);
                                    Thread.sleep(1000);
                                    updateProgress(1, 1);
                                    updateMessage("Done");
                                    lvStatus.setItems(datauserview);
                                    idLaundry.clear();
                                }
                                else{
                                    Thread.sleep(500);
                                    updateProgress(1, 1);
                                    updateMessage("Laundry ID Not Found");
                                    idLaundry.clear();
                                    Thread.sleep(1000);
                                }
                            } else{
                                updateMessage("Error when sync");
                            }
                        } else{
                            updateMessage("Error");
                        }

                    }
                    catch(Exception e){
                        e.printStackTrace();
                        updateMessage("Exception Error");
                        return null;
                    }

                    return null;
                }
            };
            pbCekStat.progressProperty().bind(task.progressProperty());
            lblStatus.textProperty().bind(task.messageProperty());
            new Thread(task).start();
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
                    b = useEngine.AddNewOrder(nama, alamat, hp, jenis, dateman.CreateStringFormDate(date1), dateman.CreateStringFormDate(date2), this.adminName);
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
                boolean b= false;
                b = useEngine.AddNewOrder(nama, alamat, hp, jenis, dateman.GetCurrentDate(), dateman.CreateStringFormDate(zz), this.adminName);
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
        if (this.adminName.equalsIgnoreCase(username)){
            this.isAdmin = member.AdminLogin(username, pass, userpass);
            if (this.isAdmin){
                apAdminMain.setVisible(true);
                apUserMain.setVisible(false);
                apLoginForm.setVisible(false);
                FadeInAnimation(apAdminMain);
                lblLogin.setText("Keluar");
                lblGantiLaundry.setVisible(false);
                txtUserName.clear();
                txtUserPass.clear();
                selectionModel1.select(0);
                TabChanged();
            } else{
                ShowDialog("Login Information", member.getMessage(),AlertType.INFORMATION);
            }
        } else {
            ShowDialog("Login Information", "Username tidak sesuai dengan layanan laundry yang dipilih\n"
                    + "Layanan Laundry saat ini : " + this.laundryName,AlertType.INFORMATION);
        }
    }
    public void ShowLoginForm(){
        String a = lblLogin.getText();
        if(a.equalsIgnoreCase("keluar")){
            this.isAdmin = false;
            lblLogin.setText("Masuk");
            lblGantiLaundry.setVisible(true);
            apAdminMain.setVisible(false);
            apChooseLaundry.setVisible(false);
            apLoading.setVisible(false);
            apUserMain.setVisible(true);
            FadeInAnimation(apUserMain);
            apLoginForm.setVisible(false);
        } else{
            paneAbout.setVisible(false);
            apChooseLaundry.setVisible(false);
            apLoading.setVisible(false);
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
    
    public void EksportForUserView(ObservableList<Order> data){
        
    }
    
    public void KonfirmOrder(){
        final Task task;
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
            String strStatus = "Dikonfirmasi";
            if(!txtPesan.getText().isEmpty()){
                strPesan = txtPesan.getText();
            }
            try{
                int p0 = useEngine.GetFileFromServer("Order.txt", this.adminName);
                int p1 = useEngine.GetFileFromServer("order_acc.txt", this.adminName);
                if (p0 == 2 && p1 == 2){
                    arraydatakonfirm = useEngine.GetConfirmedOrder();
                    datakonfirm = FXCollections.observableArrayList(arraydatakonfirm);
                    datakonfirm.add(new Order(intlid, strName, strAlamat, strHP, strJenis,strTglAmbil, strTglAntar, 0, 0, strPesan, strStatus));
                    arraydatakonfirm.add(new Order(intlid, strName, strAlamat, strHP, strJenis,strTglAmbil, strTglAntar, 0, 0, strPesan, strStatus));
                    arraydata.remove(TabelNewOrder.getSelectionModel().getSelectedIndex());
                    arraydatamentah.remove(0);
                    arraydatamentah.remove(TabelNewOrder.getSelectionModel().getSelectedIndex());
                    data.remove(TabelNewOrder.getSelectionModel().getSelectedIndex());
                    UpdateTableKonfirm(TabelKonfirmedOrder, datakonfirm);
                    UpdateTable(TabelNewOrder, data);
                    dbms.TulisFile("order_acc.txt", dateman.GetCurrentDate(), true);
                    dbms.TulisFile("order_acc.txt", datakonfirm, false);
                    boolean p4 = dbms.TulisFile("order_acc.txt", datacomplete, false);
                    dbms.TulisFile("Order.txt", dateman.GetCurrentDate(), true);
                    dbms.TulisFile("Order.txt", arraydatamentah, false);
                    test1 = test2 = false;
                    task = new Task<Void>(){
                        @Override
                        protected Void call() throws Exception {
                            test1 = useEngine.PutFileToServer("order_acc.txt", adminName);
                            test2 = useEngine.PutFileToServer("Order.txt", adminName);
                            return null;
                        }
                    };
                    new Thread(task).start();
                }
                else if (p0 != 2 || p1 != 2){
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
        final Task task;
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
            String strStatus = "Sedang Dicuci";
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
                    datakonfirm.set(indexi, new Order(intlid, strName, strAlamat, strHP, strJenis,strTglAmbil, strTglAntar, intharga, intberat, strPesan, strStatus));
                    arraydatakonfirm.set(indexi, new Order(intlid, strName, strAlamat, strHP, strJenis,strTglAmbil, strTglAntar, intharga, intberat, strPesan, strStatus));
                    UpdateTableKonfirm(TabelKonfirmedOrder, datakonfirm);
                    boolean p0 = dbms.TulisFile("order_acc.txt", dateman.GetCurrentDate(), true);
                    boolean p1 = dbms.TulisFile("order_acc.txt", datakonfirm, false);
                    boolean p4 = dbms.TulisFile("order_acc.txt", datacomplete, false);
                    test1 = test2 = false;
                    task = new Task<Void>(){
                        @Override
                        protected Void call() throws Exception {
                            test1 = useEngine.PutFileToServer("order_acc.txt", adminName);
                            test2 = useEngine.PutFileToServer("Order.txt", adminName);
                            return null;
                        }
                    };
                    new Thread(task).start();
                    if (!p0 || !p1 || !p4){
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
    
    public void ProsesPengantaran(){
        final Task task;
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
            String strStatus = "Selesai - Proses Pengantaran";
            int intharga = datakonfirm.get(TabelKonfirmedOrder.getSelectionModel().getSelectedIndex()).getHarga();
            int intberat = datakonfirm.get(TabelKonfirmedOrder.getSelectionModel().getSelectedIndex()).getBerat();
            try{
                if (intharga == 0 || intberat == 0){
                    ShowDialog("Information", "Tolong isi Harga dan berat terlebih dahulu", AlertType.INFORMATION);
                }
                else{
                    this.arraydatacomplete = useEngine.GetCompletedOrder();
                    this.datacomplete = FXCollections.observableArrayList(arraydatacomplete);
                    int indexi = TabelKonfirmedOrder.getSelectionModel().getSelectedIndex();
                    datacomplete.add(new Order(intlid, strName, strAlamat, strHP, strJenis,strTglAmbil, strTglAntar, intharga, intberat, strPesan, strStatus));
                    arraydatacomplete.add(new Order(intlid, strName, strAlamat, strHP, strJenis,strTglAmbil, strTglAntar, intharga, intberat, strPesan, strStatus));
                    datakonfirm.remove(indexi);
                    arraydatakonfirm.remove(indexi);
                    UpdateTableKonfirm(TabelKonfirmedOrder, datakonfirm);
                    boolean p0 = dbms.TulisFile("order_acc.txt", dateman.GetCurrentDate(), true);
                    boolean p1 = dbms.TulisFile("order_acc.txt", datakonfirm, false);
                    boolean p4 = dbms.TulisFile("order_acc.txt", datacomplete, false);
                    test1 = test2 = false;
                    task = new Task<Void>(){
                        @Override
                        protected Void call() throws Exception {
                            test1 = useEngine.PutFileToServer("order_acc.txt", adminName);
                            test2 = useEngine.PutFileToServer("Order.txt", adminName);
                            return null;
                        }
                    };
                    new Thread(task).start();
                    if (!p0 || !p1 || !p4){
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
    public void UpdateTableComplete(TableView table, ObservableList<Order> list){
        tcolid.setCellValueFactory(new PropertyValueFactory<Order, Integer>("lid"));
        tcohp.setCellValueFactory(new PropertyValueFactory<Order, String>("nomorHP"));
        tcotglAmbil.setCellValueFactory(new PropertyValueFactory<Order, String>("tglAmbil"));
        tcotglAntar.setCellValueFactory(new PropertyValueFactory<Order, String>("tglAntar"));
        tcoharga.setCellValueFactory(new PropertyValueFactory<Order, Integer>("harga"));
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
                if(e.getCharacter().matches("[A-Za-z]") || e.getCharacter().matches(" ") ||
                        e.getCharacter().matches("'") || e.getCharacter().matches(",")){ 
                }else{
                    e.consume();
                }
            }
        };
    }
}
