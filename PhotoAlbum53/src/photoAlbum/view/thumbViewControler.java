package photoAlbum.view;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import photoAlbum.Model.Album;
import photoAlbum.Model.Photo;
import photoAlbum.Model.User;

public class thumbViewControler {
	
	
	private Album currentAlbum;
	private Photo currentPhoto;
	private User currentUser;
    private ArrayList<User> savedUsers;
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
    
   // @FXML private ListView<User> UserList = new ListView<User>();

    public thumbViewControler(Album ca){
    	this.currentAlbum = ca;
    }
    
    @FXML
    private void initialize(){
       // setUsers();
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
