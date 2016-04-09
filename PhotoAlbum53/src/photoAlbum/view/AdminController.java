package photoAlbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import photoAlbum.Model.User;

import java.io.*;
import java.util.ArrayList;


/**
 * Created by Paulo1 on 4/7/2016.
 */
public class AdminController {
    private ArrayList<User> savedUsers;
    @FXML private TextField txtAddUser;
    @FXML private Button btnAddUser;
    @FXML private Button btnDeleteUser;
    @FXML private ListView<User> UserList = new ListView<User>();

    public AdminController(){
    }

    @FXML
    private void initialize(){
        setUsers();
    }

    public void setUsers(){
        LoadUserList();
        ObservableList<User> ObsUserList = FXCollections.observableList(savedUsers);
        UserList.setItems(ObsUserList);
        UserList.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {

            @Override
            public ListCell<User> call(ListView<User> param) {
                ListCell<User> cell = new ListCell<User>() {
                    @Override
                    protected void updateItem(User t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getName());
                        }
                    }
                };
                return cell;
            }
        });
    }


    public void AddUser(ActionEvent actionEvent){

        //System.out.println("Button Was clicked");

        //btnAddUser.getOnMouseClicked();
        if(!txtAddUser.equals("")) {
            //System.out.println(txtAddUser.getText());
            User newUser = new User(txtAddUser.getText());

            //System.out.println("Empty UserList");
            savedUsers.add(newUser);

            Save(savedUsers);
            setUsers();
        }

    }


    public void DeleteUser(ActionEvent actionEvent){

        savedUsers.remove(UserList.getSelectionModel().getSelectedItem());
        Save(savedUsers);
        setUsers();

    }

    public void Save(ArrayList<User> users){

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("data/UserList.bin"));
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void LoadUserList(){

        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("data/UserList.bin"));


            ArrayList<User> UL;
            try {
                UL = (ArrayList<User>) ois.readObject();
                savedUsers = UL;
            } catch (ClassNotFoundException e) {
                savedUsers = new ArrayList<User>();
            }

        } catch (IOException e) {
            savedUsers = new ArrayList<User>();
            return;
        }
    }

}
