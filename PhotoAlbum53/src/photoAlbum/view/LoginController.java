package photoAlbum.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import photoAlbum.Model.User;
import photoAlbum.photoalbum;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by Paulo1 on 4/7/2016.
 */
public class LoginController {

    @FXML private Label lblLogin;
    @FXML private TextField txtUname;
    @FXML private Button btnLogin;
    private Stage prevStage;
    private photoalbum palbum;
    private ArrayList<User> savedUsers;

    @FXML
    private void initialize(){

        LoadUserList();

    }
    public void setPhotoAlbum(photoalbum p){this.palbum = p;}

    public void setPrevStage(Stage stage){
        this.prevStage = stage;
    }

    /**
     * Launches Either Admin or User Page
     * Based on input
     * @param actionEvent
     */
    public void Login(ActionEvent actionEvent){

        btnLogin.getOnMouseClicked();
        if(txtUname.getText().equals("Admin")){
            //lblLogin.setText("You are The Admin");
            LaunchAdminStage();
        }
        else if(validUser(txtUname.getText())){
            //lblLogin.setText("Welcome "+txtUname.getText());
            LaunchUserStage();
        }
        else{
            lblLogin.setText("Try again");
        }

    }

    /**
     * Launches Admin Page
     */
    public void LaunchAdminStage(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Welcome Admin");
            Pane myPane;
            myPane = FXMLLoader.load(getClass().getResource("AdminView.fxml"));
            Scene scene = new Scene(myPane);
            stage.setScene(scene);
            prevStage = (Stage) btnLogin.getScene().getWindow();
            prevStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Launches User Stage where you can view Albums
     */
    public void LaunchUserStage(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Welcome "+txtUname.getText());
            Pane myPane;
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("AlbumPage.fxml"));
            myPane = (Pane) myLoader.load();
            albumPageController controller = (albumPageController) myLoader.getController();
            controller.setUsername(txtUname.getText());
            controller.setPrevStage(prevStage);
            prevStage = (Stage) btnLogin.getScene().getWindow();
            prevStage.close();

            Scene scene = new Scene(myPane);
            stage.setScene(scene);
            prevStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the valid User Names
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
     * Checks if the Username input is Valid
     * @param name
     * @return
     */
    public boolean validUser(String name){

        for(User u: savedUsers){
            if(u.getName().equals(name))
                return true;
        }
        return false;
    }
}
