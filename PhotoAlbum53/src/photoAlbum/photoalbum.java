package photoAlbum;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import photoAlbum.Model.User;
import photoAlbum.view.LoginController;

import java.io.*;
import java.util.ArrayList;


public class photoalbum extends Application implements Serializable {

    private static Stage Login;
    private static Stage Admin;
    private static ArrayList<User> UserList = new ArrayList<User>();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        LoadUserList();
        primaryStage.setTitle("Login");

        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("view\\LoginView.fxml"));

        Pane myPane = (Pane)myLoader.load();

        LoginController controller = (LoginController) myLoader.getController();

        controller.setPrevStage(primaryStage);

        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);
        primaryStage.show();
        Save();


    }

    public static void Save() {

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("data/UserList.bin"));
            oos.writeObject(UserList);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void LoadUserList(){

        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new FileInputStream("data/UserList.bin"));
            ArrayList<User> UL;
        try {
            UL = (ArrayList<User>) ois.readObject();
            UserList = UL;
        } catch (ClassNotFoundException e) {
            //System.out.println("Did not work 1");
            UserList = new ArrayList<User>();
        }

        } catch (IOException e) {
            //System.out.println("Did not work 2");
            UserList = new ArrayList<User>();
            return;
        }
    }
}
