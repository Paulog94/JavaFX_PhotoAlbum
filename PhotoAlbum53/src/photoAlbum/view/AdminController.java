package photoAlbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
    @FXML private Button btnLogout;
    @FXML private ListView<User> UserList = new ListView<User>();

    /**
     * Controls Admin Page
     */
    public AdminController(){
    }

    @FXML
    private void initialize(){
        setUsers();
    }

    /**
     * Sets the list of Users
     */
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

    /**
     * Allows the user to add a new username
     * @param actionEvent
     */
    public void AddUser(ActionEvent actionEvent){

        if(isValidUser(txtAddUser.getText())) {
            User newUser = new User(txtAddUser.getText());
            savedUsers.add(newUser);

            Save(savedUsers);
            setUsers();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Add User");
            alert.setContentText("Username cannot Already exist and it cannot be empty");
            alert.showAndWait();
        }

    }

    /**
     * Deletes selected User
     * @param actionEvent
     */
    public void DeleteUser(ActionEvent actionEvent){

        savedUsers.remove(UserList.getSelectionModel().getSelectedItem());
        Save(savedUsers);
        setUsers();

    }

    /**
     * Saves the User List to disk
     * @param users
     */
    public void Save(ArrayList<User> users){

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("data/UserList.bin"));
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Loads the UserList to modify
     */
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

    /**
     * Checks if the there are other UserNames
     * @param username
     * @return
     */
    public boolean isValidUser(String username){

        for(User u : savedUsers){
            if(u.getName().equals(username))
                return false;
        }
        if(username.equals("") || username.isEmpty())
            return false;

        return true;
    }

    /**
     * Goes back to login page
     * @param actionEvent
     */
    public void LogOut(ActionEvent actionEvent){
        LaunchLoginPage();
    }

    /**
     * Launches Login Page
     */
    public void LaunchLoginPage(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Login");
            Pane myPane;
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            myPane = (Pane) myLoader.load();
            Stage prevStage = (Stage) btnLogout.getScene().getWindow();
            prevStage.close();

            Scene scene = new Scene(myPane);
            stage.setScene(scene);
            prevStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
