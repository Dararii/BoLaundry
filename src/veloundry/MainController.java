package veloundry;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Darari
 */
public class MainController implements Initializable {

    @FXML
    private TextField txtHP;
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtAlamat;
    @FXML
    private DatePicker dpTglAntar;
    @FXML
    private DatePicker dpTglAmbil;
    @FXML
    private TabPane tabMain;
    @FXML
    private TabPane tabAbout;
    @FXML
    private Tab TabAmbil;
    @FXML
    private Tab TabAntar;
    @FXML
    private Button btnSendReq;
    @FXML
    private Pane paneAbout;
    @FXML
    private ComboBox cbJenisCucian;
    @FXML
    private ListView<String> lvStatusKategori;
    
            
    private SingleSelectionModel<Tab> selectionModel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //btntest.setContentDisplay(ContentDisplay.TOP);
        selectionModel = tabMain.getSelectionModel();
        
        ObservableList<String> items =FXCollections.observableArrayList (
        "Nama (LID)", "Alamat", "Jenis Cucian", "Berat Cucian", "Tanggal Ambil",
                "Tanggal Antar", "Harga");
        ObservableList<String> jenisCuci = FXCollections.observableArrayList(
        "Cuci Basah", "Cuci Kering", "Cuci Setrika", "Cuci Boneka", 
                "Cuci Gorden/Sprei");
        cbJenisCucian.setItems(jenisCuci);
        lvStatusKategori.setItems(items);
    }    
    
    public void About_Click(){
        selectionModel.select(3);
        paneAbout.setVisible(true);
    }
    public void TabChanged(){
        int a  = selectionModel.getSelectedIndex();
        if (a!= 3) {
            paneAbout.setVisible(false);
        } else {
            paneAbout.setVisible(true);
        }
    }
    public void btnSendClick(){
        //tabMain.setVisible(false);
        //txtName.getText()
    }
    
}
