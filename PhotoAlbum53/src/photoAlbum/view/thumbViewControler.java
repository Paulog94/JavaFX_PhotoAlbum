package photoAlbum.view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import photoAlbum.Model.Album;
import photoAlbum.Model.Photo;
import photoAlbum.Model.User;

public class thumbViewControler {


   // @FXML private AnchorPane ImageGalleryAnchorPane;
    @FXML private ScrollPane imageGalleryField;
    private Album currentAlbum;
	private Photo currentPhoto;
	private User currentUser;
	private String userName;
    private ArrayList<User> savedUsers;
    private int userIndex;
    private int albumIndex;
    
    @FXML private TextField txtAlbumName;
    @FXML private Button btnAddPhoto;
    @FXML private Button btnREmPhoto;
    @FXML private Button btnCapTag;
    @FXML private Button btnSearch;
    @FXML private Button btnMovePhoto;
    @FXML private Button btnLogOut;
    @FXML private Button btnExit;
    @FXML private Button btnOpen;
   // @FXML private ImageView selectedImage;
   // @FXML private ImageView thumbImage;
   // @FXML private TextField imageName;
    
    @FXML private TilePane tilePane = new TilePane();
    static Stage prevStage;

    public thumbViewControler(){

    }
    
    @FXML
    private void initialize(){
    	LoadUserList();
      // setPhotos();
     //  drawTiles();
    }
    
    public void setPhotos() {
        System.out.println("Setting Photos");
       // LoadUserList();
        savedUsers.get(userIndex).getAlbumList().get(albumIndex);
        //if(!currentAlbum.getPhotoList().isEmpty()) {
            System.out.println("Photos are here");
            ObservableList<Photo> obsPhotoList = FXCollections.observableList(savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList());
           // drawTiles();
        //}
    }
    
    public void setUsername(String username){
        this.userName = username;
        for(User u : savedUsers){
            if(u.getName().equals(username))
                userIndex = savedUsers.indexOf(u);
            	currentUser = u;
        }
    }
    
 
    
    public void drawTiles(){
        System.out.println("In ImageGallery");
        imageGalleryField.setContent(tilePane);
        int x = savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().size();
        if(x==0){
            return;
        }
        int pIndex;
        for(Photo p: savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList()){

            System.out.println("New Image ");
            Image m = new Image (p.getURL());
            final ImageView IV = new ImageView(m);
            IV.setFitWidth(100);
            IV.setFitHeight(100);
            //Allows for clickable image view
            IV.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    //Place what needs to happen here
                    System.out.println(IV.getImage().toString());
                    event.consume();
                }
            });
            tilePane.setPadding(new Insets(15,15,15,15));
            tilePane.setVgap(15);
            tilePane.getChildren().add(IV);
        }
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

    public void setAlbumIndex(int a){
        albumIndex = a;
    }

    public void AddPhoto(ActionEvent actionEvent) {

        System.out.println("Adding new Images");
        String m = "https://s-media-cache-ak0.pinimg.com/originals/ed/a2/f4/eda2f478dddfc299b09c54a10baee0a9.gif";
        System.out.println(savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().size());
        Photo p = new Photo(m);
        savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().add(p);
        Save(savedUsers);

        System.out.println("Add Image ");
        //Image m = new Image (p.getURL());
        ImageView IV = new ImageView(m);
        IV.setFitWidth(100);
        IV.setFitHeight(100);
        tilePane.setPadding(new Insets(15,15,15,15));
        tilePane.setVgap(15);
        tilePane.getChildren().add(IV);
        
        //setPhotos();
        //selectedImage = new ImageView(new Image(m));
    }

    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }


    public void ExitAlbum(ActionEvent actionEvent) {
        LaunchUserStage();
    }

    public void LogOut(ActionEvent actionEvent){
        LaunchLoginPage();
    }

    public void LaunchUserStage(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Welcome "+userName);
            Pane myPane;
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("AlbumPage.fxml"));
            myPane = (Pane) myLoader.load();
            albumPageController controller = (albumPageController) myLoader.getController();
            controller.setUsername(userName);
            controller.setPrevStage(prevStage);
            prevStage = (Stage) btnLogOut.getScene().getWindow();
            prevStage.close();

            Scene scene = new Scene(myPane);
            stage.setScene(scene);
            prevStage.close();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LaunchLoginPage(){
        try {
            Stage stage = new Stage();
            stage.setTitle("Login");
            Pane myPane;
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            myPane = (Pane) myLoader.load();
            prevStage = (Stage) btnLogOut.getScene().getWindow();
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
