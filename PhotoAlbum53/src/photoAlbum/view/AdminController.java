package photoAlbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import photoAlbum.Model.User;

import java.util.ArrayList;

/**
 * Created by Paulo1 on 4/7/2016.
 */
public class AdminController {
    @FXML private TextField txtAddUser;
    @FXML private Button btnAddUser;
    @FXML private Button btnDeleteUser;
    @FXML private ListView<User> UserList;




    public void initialize(){


    }

    public void setUsers(ArrayList<User> users){
        ObservableList<User> ObsUserList = FXCollections.observableList(users);
        UserList.setItems(ObsUserList);
    }


    @FXML
    public void AddUser(ActionEvent actionEvent){

    }

    @FXML
    public void DeleteUser(ActionEvent actionEvent){

    }
}
