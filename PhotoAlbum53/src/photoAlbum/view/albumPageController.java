package photoAlbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import photoAlbum.photoalbum;
import photoAlbum.Model.Album;
import photoAlbum.Model.Photo;
import photoAlbum.Model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;

public class albumPageController {

    private ArrayList<User> savedUsers;
    private String username;
    private int index;
    static Stage prevStage;
    //private Album currentAlbum;

    @FXML
    private Label lblName;
    @FXML
    private Label lblNumofP;
    @FXML
    private Label lblOldestDate;
    @FXML
    private Label lblDateRange;
    @FXML
    private TextField txtSearch;
    @FXML
    private Button btnSearch;
    @FXML
    private TextField txtAddAlbum;
    @FXML
    private Button btnaddAlbum;
    @FXML
    private TextField txtEdit;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnViewAlbum;

    @FXML
    private ListView<Album> AlbumList = new ListView<Album>();


    @FXML
    private void initialize() {
        setAlbums();
    }

    public void setAlbums() {
        LoadUserList();

        ObservableList<Album> ObsUserList = FXCollections.observableList(savedUsers.get(index).getAlbumList());
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


    public void setUsername(String username) {
        this.username = username;
        for (User u : savedUsers) {
            if (u.getName().equals(username))
                index = savedUsers.indexOf(u);
            photoalbum.setCurrentUser(u);
        }
        setAlbums();
    }

    public void addAlbum(ActionEvent actionEvent) {

        if (isValidAlbum(txtAddAlbum.getText())) {
            savedUsers.get(index).addAlbum(txtAddAlbum.getText());
            Save(savedUsers);
            setAlbums();
            txtAddAlbum.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot add Album");
            alert.setContentText("Album cannot Already exist and it cannot be empty");
            alert.showAndWait();
        }


    }

    public void deleteAlbum(ActionEvent actionEvent) {
        savedUsers.get(index).getAlbumList().remove(AlbumList.getSelectionModel().getSelectedItem());
        Save(savedUsers);
        setAlbums();

    }

    public void SearchPhoto(ActionEvent actionEvent) {
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

    public void editAlbum(ActionEvent actionEvent) {
        if (!AlbumList.getSelectionModel().isEmpty() && isValidAlbum(txtEdit.getText())) {
            AlbumList.getSelectionModel().getSelectedItem().setName(txtEdit.getText());
            Save(savedUsers);
            setAlbums();
            txtEdit.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot Edit Album");
            alert.setContentText("Album cannot Already exist and it cannot be empty");
            alert.showAndWait();
        }
    }

    public void setPrevStage(Stage stage) {
        this.prevStage = stage;
    }

    public void ViewAlbum(ActionEvent actionEvent) {
        if (AlbumList.getSelectionModel().isEmpty()) {
            return;
        }
        try {
            Stage stage = new Stage();
            stage.setTitle(AlbumList.getSelectionModel().getSelectedItem().getName());
            Pane myPane;
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("ThumbView.fxml"));
            myPane = (Pane) myLoader.load();
            thumbViewControler controller = (thumbViewControler) myLoader.getController();
            controller.setAlbumIndex(AlbumList.getSelectionModel().getSelectedIndex());
            System.out.println("Album Index: "+AlbumList.getSelectionModel().getSelectedIndex());
            controller.setUsername(username);
            controller.setAlbumIndex(AlbumList.getSelectionModel().getSelectedIndex());
            //controller.setPrevStage(prevStage);



            Scene scene = new Scene(myPane);
            stage.setScene(scene);
            prevStage = (Stage) btnViewAlbum.getScene().getWindow();
            prevStage.close();

            stage.show();
            controller.drawTiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SelectedAlbum(Event event) {
        if (AlbumList.getSelectionModel().isEmpty()) {
            return;
        }
        LoadUserList();
        txtEdit.setText(AlbumList.getSelectionModel().getSelectedItem().getName());
        photoalbum.setCurrentAlbum(AlbumList.getSelectionModel().getSelectedItem());
        setLabels();
    }

    public boolean isValidAlbum(String album) {

        for (Album a : savedUsers.get(index).getAlbumList()) {
            if (a.getName().equals(album))
                return false;
        }
        if (album.equals("") || album.isEmpty())
            return false;

        return true;
    }

    public void setLabels(){
        String name = AlbumList.getSelectionModel().getSelectedItem().getName();
        int NumOfPhotos = AlbumList.getSelectionModel().getSelectedItem().getPhotoList().size();
        if(NumOfPhotos==0){
            lblOldestDate.setText("Oldest Date: Empty");
            lblDateRange.setText("No Range");
        }
        else{

            Calendar oldest = AlbumList.getSelectionModel().getSelectedItem().getPhotoList().get(0).getDate();
            Calendar newest = AlbumList.getSelectionModel().getSelectedItem().getPhotoList().get(0).getDate();

            for(Photo p : AlbumList.getSelectionModel().getSelectedItem().getPhotoList()){
                if(oldest.compareTo(p.getDate())<0){
                    oldest = p.getDate();
                }
                if(newest.compareTo(p.getDate())>0){
                    newest = p.getDate();
                }
            }
            lblOldestDate.setText("Oldest Photo Date: "+ oldest.getTime().toString());
            lblDateRange.setText(oldest.getTime().toString()+ " - "+newest.getTime().toString());

        }
        Album a = AlbumList.getSelectionModel().getSelectedItem();
        lblName.setText("Name: " + name);
        lblNumofP.setText("Number of Photos: " + NumOfPhotos);

    }
}