package photoAlbum.view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import photoAlbum.Model.Album;
import photoAlbum.Model.Photo;
import photoAlbum.Model.User;

public class thumbViewControler {
	
	
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
    @FXML private ImageView selectedImage;
    @FXML private ImageView thumbImage;
    @FXML private TextField imageName;
    
    @FXML private TilePane tilePane = new TilePane();

    public thumbViewControler(){

    }
    
    @FXML
    private void initialize(){
       setPhotos();
    }
    
    public void setPhotos() {
        System.out.println("Setting Photos");
        LoadUserList();
        savedUsers.get(userIndex).getAlbumList().get(albumIndex);
        //if(!currentAlbum.getPhotoList().isEmpty()) {
            System.out.println("Photos are here");
            ObservableList<Photo> obsPhotoList =
                    FXCollections.observableList(savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList());
            drawTiles(obsPhotoList);
        //}
    }
    
    public void setUsername(String username){
        for(User u : savedUsers){
            if(u.getName().equals(username))
                userIndex = savedUsers.indexOf(u);
            	currentUser = u;
        }
    }
    
    public void drawTiles(ObservableList<Photo> olp){
        System.out.println("In Draw Tiles");
    	if (!olp.isEmpty()){
    		for(Photo p : olp){
                System.out.println("Loading Photo");
    			Label title = new Label (p.getCaption());
                Image m = p.getImage();
    			ImageView imageview = new ImageView(m);
    	           TilePane.setAlignment(title, Pos.BOTTOM_RIGHT);
    	           tilePane.getChildren().addAll(title, imageview);
    		}
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
        String m = "http://2.bp.blogspot.com/-ZaYiMvE2ID0/T7-Lp-p0oDI/AAAAAAAAAdk/DiWbG2CtBRA/s1600/theexpansescreenshot.png";
        System.out.println(m.toString());
        Photo p = new Photo(m);
        savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().add(p);
        Save(savedUsers);
        setPhotos();
    }
}
