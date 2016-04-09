package photoAlbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import photoAlbum.Model.Album;
import photoAlbum.Model.Photo;
import photoAlbum.Model.User;

import java.io.*;
import java.util.ArrayList;

public class albumPageController {
    private ArrayList<User> savedUsers;
    private String username;
    private int index;

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


    public void setUsername(String username){
        this.username = username;
        for(User u : savedUsers){
            if(u.getName().equals(username))
                index = savedUsers.indexOf(u);
        }
        setAlbums();
    }

    public void addAlbum(ActionEvent actionEvent) {

        //System.out.println("Button Was clicked");

        //btnAddUser.getOnMouseClicked();
        if (!txtAddAlbum.equals("")) {
            //System.out.println(txtAddUser.getText());
            //System.out.println("Empty UserList");
            savedUsers.get(index).addAlbum(txtAddAlbum.getText());

            Save(savedUsers);
            setAlbums();
        }
        txtAddAlbum.clear();

    }
    
    public void deleteAlbum(ActionEvent actionEvent){
        savedUsers.get(index).getAlbumList().remove(AlbumList.getSelectionModel().getSelectedItem());
        Save(savedUsers);
        setAlbums();

    }

    public void SearchPhoto(ActionEvent actionEvent){}

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

    public void editAlbum(ActionEvent actionEvent) {
        AlbumList.getSelectionModel().getSelectedItem().setName(txtEdit.getText());
        Save(savedUsers);
        setAlbums();
        txtEdit.clear();
    }

    public void ViewAlbum(ActionEvent actionEvent) {
    }

    public void SelectedAlbum(Event event) {
        txtEdit.setText(AlbumList.getSelectionModel().getSelectedItem().getName());
    }
}