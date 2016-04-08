package photoAlbum;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import photoAlbum.Model.User;

import java.io.*;
import java.util.ArrayList;


public class photoalbum extends Application implements Serializable {

    private static Stage Login;
    private static Stage Admin;
    private static ArrayList<User> UserList;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        LoadUserList();
        Parent root = FXMLLoader.load(getClass().getResource("view\\LoginView.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Save();


    }

    public static void Save() throws IOException {

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/UserList.bin"));
        oos.writeObject(UserList);

    }


    public static void LoadUserList(){

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("data/UserList"));


        ArrayList<User> UL;
        try {
            UL = (ArrayList<User>) ois.readObject();
            UserList = UL;
        } catch (ClassNotFoundException e) {
            UserList = new ArrayList<User>();
        }

        } catch (IOException e) {
            UserList = new ArrayList<User>();
            return;
        }
    }
}
