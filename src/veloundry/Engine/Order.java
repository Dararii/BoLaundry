package veloundry.Engine;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**@author Darari*/
public class Order {
    private SimpleIntegerProperty lid;
    private SimpleStringProperty nama;
    private SimpleStringProperty alamat;
    private SimpleStringProperty nomorHP;
    private SimpleStringProperty jenisCucian;
    private SimpleIntegerProperty harga;
    private SimpleIntegerProperty berat;
    private SimpleStringProperty tglAmbil;
    private SimpleStringProperty tglAntar;

    public Order(){
        
    }
    public Order(int lid, String nama, String alamat, String nomorHP, String jenisCucian, int harga, int berat, String tglAmbil, String tglAntar) {
        this.lid = new SimpleIntegerProperty(lid);
        this.nama = new SimpleStringProperty(nama);
        this.alamat = new SimpleStringProperty(alamat);
        this.nomorHP = new SimpleStringProperty(nomorHP);
        this.jenisCucian = new SimpleStringProperty(jenisCucian);
        this.harga = new SimpleIntegerProperty(harga);
        this.berat = new SimpleIntegerProperty(berat);
        this.tglAmbil = new SimpleStringProperty(tglAmbil);
        this.tglAntar = new SimpleStringProperty(tglAntar);
    }

    public Order(int lid, String nama, String alamat, String nomorHP, String jenisCucian, String tglAntar, int harga) {
        this.lid = new SimpleIntegerProperty(lid);
        this.nama = new SimpleStringProperty(nama);
        this.alamat = new SimpleStringProperty(alamat);
        this.nomorHP = new SimpleStringProperty(nomorHP);
        this.jenisCucian = new SimpleStringProperty(jenisCucian);
        this.tglAntar = new SimpleStringProperty(tglAntar);
        this.harga = new SimpleIntegerProperty(harga);
    }
    public Order(int lid, String nama, String alamat, String nomorHP, String jenisCucian, String tglAmbil, String tglAntar) {
        this.lid = new SimpleIntegerProperty(lid);
        this.nama = new SimpleStringProperty(nama);
        this.alamat = new SimpleStringProperty(alamat);
        this.nomorHP = new SimpleStringProperty(nomorHP);
        this.jenisCucian = new SimpleStringProperty(jenisCucian);
        this.tglAntar = new SimpleStringProperty(tglAntar);
        this.tglAmbil = new SimpleStringProperty(tglAmbil);
    }
    
    public Order(String nama, String alamat, String nomorHP, String jenisCucian) {
        this.nama = new SimpleStringProperty(nama);
        this.alamat = new SimpleStringProperty(alamat);
        this.nomorHP = new SimpleStringProperty(nomorHP);
        this.jenisCucian = new SimpleStringProperty(jenisCucian);
    }

    public int getLid() {
        return lid.get();
    }

    public String getNama() {
        return nama.get();
    }

    public String getAlamat() {
        return alamat.get();
    }

    public String getNomorHP() {
        return nomorHP.get();
    }

    public String getJenisCucian() {
        return jenisCucian.get();
    }

    public int getHarga() {
        return harga.get();
    }

    public int getBerat() {
        return berat.get();
    }

    public String getTglAmbil() {
        return tglAmbil.get();
    }

    public String getTglAntar() {
        return tglAntar.get();
    }
    
}
