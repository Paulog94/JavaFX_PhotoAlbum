package photoAlbum.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;


import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
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

/**
 * Controls The thumbnail View
 */
public class thumbViewControler {


    @FXML private ScrollPane imageGalleryField;
    private Album currentAlbum;
	private Photo currentPhoto;
	private User currentUser;
	private String userName;
    private ArrayList<User> savedUsers;
    private int userIndex;
    private int albumIndex;
    private Stage stage;
    
    @FXML private TextField txtAlbumName;
    @FXML private Button btnAddPhoto;
    @FXML private Button btnREmPhoto;
    @FXML private Button btnCapTag;
    @FXML private Button btnSearch;
    @FXML private Button btnMovePhoto;
    @FXML private Button btnLogOut;
    @FXML private Button btnExit;
    @FXML private Button btnOpen;


    private HashMap<ImageView,Photo> IPM;
    private int selectPhotoIndex;
    private int firstP = 1;

    @FXML private TilePane tilePane = new TilePane();
    static Stage prevStage;

    
    @FXML
    private void initialize(){
    	LoadUserList();
    }

    /**
     * Sets the Photos to be Viewed
     */
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

    /**
     * Sets username to find Specific Album
     * @param username
     */
    public void setUsername(String username){
        this.userName = username;
        for(User u : savedUsers){
            if(u.getName().equals(username))
                userIndex = savedUsers.indexOf(u);
            	currentUser = u;
        }
    }

    /**
     * Draws Images on the TilePanes
     */
    public void drawTiles(){
        System.out.println("In ImageGallery");
        imageGalleryField.setContent(tilePane);
        int x = savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().size();
        IPM = new HashMap<ImageView,Photo>();
        if(x==0){
            return;
        }
        for(Photo p: savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList()){

            System.out.println("New Image ");
            Image m = new Image (p.getURL());
            final ImageView IV = new ImageView(m);
            IPM.put(IV,p);
            IV.setFitWidth(100);
            IV.setFitHeight(100);
            //Allows for clickable image view
            IV.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    //Place what needs to happen here
                    for(Photo p: savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList()){
                        if(IPM.get(IV).equals(p)){

                            selectPhotoIndex = savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().indexOf(p);
                            System.out.println("Selected Photo Index: "+selectPhotoIndex);
                            if(selectPhotoIndex ==0){
                                firstP = 0;
                            }
                            else
                                firstP = 1;
                        }

                    }
                    event.consume();
                }
            });
            tilePane.setPadding(new Insets(15,15,15,15));
            tilePane.setVgap(15);
            tilePane.getChildren().add(IV);
        }
    }

    /**
     * Saves Entire work
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
     * Loads UserList to use
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
     * Sets Album Index to find specific Album
     * @param a
     */
    public void setAlbumIndex(int a){
        albumIndex = a;
    }

    /**
     * Enables User to Add Photo From your Files
     * @param actionEvent
     */
    public void AddPhoto(ActionEvent actionEvent) {

    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Open Resource File");
    	//fileChooser.showOpenDialog(stage);
    	  File file = fileChooser.showOpenDialog(stage);
          if (file != null) {
        	  URI uri = file.toURI();
        	URL fakeURL = null;
			try {
				fakeURL = uri.toURL();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	  
              Photo newPhoto = new Photo(fakeURL.toString(),file.getName());
              savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().add(newPhoto);
              Save(savedUsers);
              //LoadUserList();
              
        	//  URI uri = newPhoto.getURL().toURI();
        	//  URL fakeURL = uri.toURL();
              System.out.println();
              Image m = new Image (newPhoto.getURL());
              final ImageView IV = new ImageView(m);
              IPM.put(IV,newPhoto);

              IV.setFitWidth(100);
              IV.setFitHeight(100);

              IV.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                  @Override
                  public void handle(MouseEvent event) {
                      //Place what needs to happen here
                      for(Photo p: savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList()){
                          if(IPM.get(IV).equals(p)){

                              selectPhotoIndex = savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().indexOf(p);
                              System.out.println("Selected Photo Index: "+selectPhotoIndex);
                              if(selectPhotoIndex ==0){
                                  firstP = 0;
                              }
                              else
                                  firstP = 1;
                          }

                      }
                      event.consume();
                  }
              });

              tilePane.setPadding(new Insets(15,15,15,15));
              tilePane.setVgap(15);
              tilePane.getChildren().add(IV);
          }

          

 /*       System.out.println("Adding new Images");
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
        tilePane.getChildren().add(IV);*/
        
        //setPhotos();
        //selectedImage = new ImageView(new Image(m));
    }

    /**
     * Exits Album and goes back to Album list
     * @param actionEvent
     */
    public void ExitAlbum(ActionEvent actionEvent) {
       // IPM.clear();
        LaunchUserStage();
    }

    /**
     * Logs Out and goes back to login page
     * @param actionEvent
     */
    public void LogOut(ActionEvent actionEvent){
       // IPM.clear();
        LaunchLoginPage();
    }

    /**
     * Launches The Album List View
     */
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


    /**
     * Deletes Specific selected Photo
     * @param actionEvent
     */
    public void DeletePhoto(ActionEvent actionEvent) {
        if (selectPhotoIndex == 0 && firstP == 0 && !tilePane.getChildren().isEmpty()) {
            savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().remove(selectPhotoIndex);
            Save(savedUsers);
            tilePane.getChildren().remove(selectPhotoIndex);

        } else if (selectPhotoIndex != 0) {


            savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().remove(selectPhotoIndex);
            Save(savedUsers);
            tilePane.getChildren().remove(selectPhotoIndex);
        }
    }

    /**
     * Launches Edit Captions for a specific Photo
     *
     * @param actionEvent
     * @throws IOException Used to make sure Scene exists, it will always do
     */
    public void EditCaptionTags(ActionEvent actionEvent) throws IOException {
        if(savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot edit Photo");
            alert.setContentText("Album is empty");
            alert.showAndWait();
            return;
        }
        Stage stage = new Stage();
        stage.setTitle("Welcome "+userName);
        Pane myPane;
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("EditCaptionAndTags.fxml"));
        myPane = (Pane) myLoader.load();
        EditCaptionController controller = (EditCaptionController) myLoader.getController();
        controller.setUsername(userName);
        controller.setAlbumIndex(albumIndex);
        controller.setPhotoIndex(selectPhotoIndex);
        controller.setTags();

        Scene scene = new Scene(myPane);
        stage.setScene(scene);

        stage.showAndWait();
        stage.close();
        //LoadUserList();

    }

    /**
     *
     * @param actionEvent
     * @throws IOException
     */
    public void MovePhoto(ActionEvent actionEvent) throws IOException {

        if (((selectPhotoIndex == 0 && firstP == 0) || (selectPhotoIndex != 0)) && !tilePane.getChildren().isEmpty()) {

            Stage stage = new Stage();
            stage.setTitle("Welcome "+userName+" Please Move Photo");
            Pane myPane;
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("MovePhotoView.fxml"));
            myPane = (Pane) myLoader.load();
            MovePhotoController controller = (MovePhotoController) myLoader.getController();
            controller.setUsername(userName);
            controller.setAlbumIndex(albumIndex);
            controller.setPhotoIndex(selectPhotoIndex);
            controller.setParentTilePane(tilePane);
            controller.setAlbums();
            controller.setImageView();

            Scene scene = new Scene(myPane);
            stage.setScene(scene);

            stage.showAndWait();
            stage.close();


        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Select a Photo First");
            alert.setContentText("Photo is not selected");
            alert.showAndWait();
            return;
        }

    }

    /**
     * Launches View Single Photo
     * @param actionEvent
     * @throws IOException
     */
    public void OpenPhoto(ActionEvent actionEvent) throws IOException {

        if (((selectPhotoIndex == 0 && firstP == 0) || (selectPhotoIndex != 0))&& !tilePane.getChildren().isEmpty()) {

            Stage stage = new Stage();
            stage.setTitle("Welcome " + userName + " Please Move Photo");
            SplitPane myPane;
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("PhotoView.fxml"));
            myPane = (SplitPane) myLoader.load();
            PhotoViewController controller = (PhotoViewController) myLoader.getController();
            controller.setPhoto(savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().get(selectPhotoIndex));

            Scene scene = new Scene(myPane);
            stage.setScene(scene);

            stage.showAndWait();
            stage.close();
        }
    }
}
