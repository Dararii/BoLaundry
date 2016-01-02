package veloundry.Engine;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**@author Darari*/
public class Member {
    private SimpleStringProperty name;
    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleIntegerProperty id;
    private SimpleStringProperty alamat;
    private SimpleStringProperty hp;

    public SimpleStringProperty getName() {
        return name;
    }

    public void setName(SimpleStringProperty name) {
        this.name = name;
    }

    public SimpleStringProperty getUsername() {
        return username;
    }

    public void setUsername(SimpleStringProperty username) {
        this.username = username;
    }

    public SimpleStringProperty getPassword() {
        return password;
    }

    public void setPassword(SimpleStringProperty password) {
        this.password = password;
    }

    public SimpleIntegerProperty getId() {
        return id;
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public SimpleStringProperty getAlamat() {
        return alamat;
    }

    public void setAlamat(SimpleStringProperty alamat) {
        this.alamat = alamat;
    }

    public SimpleStringProperty getHp() {
        return hp;
    }

    public void setHp(SimpleStringProperty hp) {
        this.hp = hp;
    }

}
