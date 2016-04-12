package photoAlbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Callback;
import photoAlbum.Model.Album;
import photoAlbum.Model.User;

import java.io.*;
import java.util.ArrayList;

/**
 * Used to move Photos from one place to another Album
 *
 * Created by Paulo1 on 4/11/2016.
 */
public class MovePhotoController {
    @FXML private Label lblCaption;
    @FXML private ListView AlbumList;
    @FXML private Button btnSave;
    @FXML private Label lblSrc;
    @FXML private Label lblDest;
    @FXML private ImageView imgView;
    private String userName;
    private ArrayList<User> savedUsers;
    private int userIndex;
    private int src;
    private int pIndex;
    private TilePane TP;


    @FXML
    private void initialize(){
        LoadUserList();
    }

    /**
     * Saves the move
     * @param actionEvent
     */
    public void Save(ActionEvent actionEvent) {
        if(AlbumList.getSelectionModel().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Select an album first");
            alert.setContentText("Album is not selected.");
            alert.showAndWait();
            return;
        }
        int des = AlbumList.getSelectionModel().getSelectedIndex();

        savedUsers.get(userIndex).getAlbumList().get(des).getPhotoList().add(savedUsers.get(userIndex).getAlbumList().get(src).getPhotoList().get(pIndex));
        savedUsers.get(userIndex).getAlbumList().get(src).getPhotoList().remove(savedUsers.get(userIndex).getAlbumList().get(src).getPhotoList().get(pIndex));
        Save(savedUsers);
        TP.getChildren().remove(pIndex);
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    /**
     * Exits View and doesnt Save
     * @param actionEvent
     */
    public void Cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    /**
     * Sets Image
     */
    public void setImageView(){

        lblCaption.setText(savedUsers.get(userIndex).getAlbumList().get(src).getPhotoList().get(pIndex).getCaption());
        Image m = new Image(savedUsers.get(userIndex).getAlbumList().get(src).getPhotoList().get(pIndex).getURL());
        imgView.setImage(m);
        //imgView.setFitHeight(200);
        imgView.getViewport();
    }

    /**
     * sets Album List to move to
     */
    public void setAlbums() {
        LoadUserList();

        ObservableList<Album> ObsUserList = FXCollections.observableList(savedUsers.get(userIndex).getAlbumList());
        AlbumList.setItems(ObsUserList);
        AlbumList.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>() {

            @Override
            public ListCell<Album> call(ListView<Album> param) {
                ListCell<Album> cell = new ListCell<Album>() {
                    @Override
                    protected void updateItem(Album t, boolean bln) {
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


    public void setUsername(String username){
        this.userName = username;

        for(User u : savedUsers){
            if(u.getName().equals(username))
                userIndex = savedUsers.indexOf(u);
        }
    }
    public void setAlbumIndex(int a){src = a;}
    public void setPhotoIndex(int p){ pIndex = p;}

    public void LoadUserList() {

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
    public void Save(ArrayList<User> users) {

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("data/UserList.bin"));
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void setParentTilePane(TilePane TP){this.TP = TP;}

    /**
     * Changes Labels
     * @param event
     */
    public void ChangeLabels(Event event) {
        if (!AlbumList.getSelectionModel().isEmpty()) {
            int des = AlbumList.getSelectionModel().getSelectedIndex();
            lblSrc.setText("Move From:\t" + savedUsers.get(userIndex).getAlbumList().get(src).getName());
            lblDest.setText(" To:\t" + savedUsers.get(userIndex).getAlbumList().get(des).getName());
        }
    }
}
