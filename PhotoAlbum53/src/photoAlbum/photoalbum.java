package photoAlbum;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import photoAlbum.Model.Album;
import photoAlbum.Model.User;
import photoAlbum.view.LoginController;

import java.io.*;
import java.util.ArrayList;

/**
 * Launches the Login View
 */
public class photoalbum extends Application implements Serializable {

    private static ArrayList<User> UserList = new ArrayList<User>();
    private static User currentUser;
    private static Album currentAlbum;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        LoadUserList();
        primaryStage.setTitle("Login");

        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("view\\LoginView.fxml"));

        Pane myPane = myLoader.load();

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
            UserList = new ArrayList<User>();
        }

        } catch (IOException e) {
            UserList = new ArrayList<User>();
            return;
        }
    }

	/**
	 * @return the currentUser
	 */
	public static User getCurrentUser() {
		return currentUser;
	}

	/**
	 * @param currentUser the currentUser to set
	 */
	public static void setCurrentUser(User currentUser) {
		photoalbum.currentUser = currentUser;
	}

	/**
	 * @return the currentAlbum
	 */
	public static Album getCurrentAlbum() {
		return currentAlbum;
	}

	/**
	 * @param currentAlbum the currentAlbum to set
	 */
	public static void setCurrentAlbum(Album currentAlbum) {
		photoalbum.currentAlbum = currentAlbum;
	}

}
