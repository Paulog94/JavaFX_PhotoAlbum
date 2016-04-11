package photoAlbum.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import photoAlbum.Model.Album;
import photoAlbum.Model.User;
import photoAlbum.Model.tag;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Paulo1 on 4/11/2016.
 */
public class EditCaptionController {

    @FXML private Button btnSave;
    @FXML private TextField txtCaption;
    @FXML private TextField txtType;
    @FXML private TextField txtValue;
    @FXML private Button btnCreateTag;
    @FXML private Button btnDelete;
    @FXML private Button btnCaption;
    @FXML private Label lblCaption;
    @FXML private ListView TagView;
    private String userName;
    private ArrayList<User> savedUsers;
    private int userIndex;
    private int albumIndex;
    private int pIndex;

    @FXML
    private void initialize(){
        LoadUserList();
    }

    public void setTags() {
        lblCaption.setText("Caption: "
                +savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().get(pIndex).getCaption());

        txtCaption.setText(savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().get(pIndex).getCaption());
        ObservableList<tag> ObsUserList = FXCollections.observableList(
                savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().get(
                        pIndex).getTags());

        TagView.setItems(ObsUserList);
        TagView.setCellFactory(new Callback<ListView<tag>, ListCell<tag>>() {

            @Override
            public ListCell<tag> call(ListView<tag> param) {
                ListCell<tag> cell = new ListCell<tag>() {
                    @Override
                    protected void updateItem(tag t, boolean bln) {
                        super.updateItem(t, bln);
                        if (t != null) {
                            setText(t.getType()+": "+t.getValue());
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void CreateTag(ActionEvent actionEvent) {
        if(!txtType.getText().isEmpty() && !txtValue.getText().isEmpty()){
            tag newTag = new tag(txtType.getText(),txtValue.getText());
            savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().get(pIndex).getTags().add(newTag);
            setTags();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot add tag");
            alert.setContentText("Tag Value and Tag Type cannot be empty.");
            alert.showAndWait();
        }
    }

    public void DeleteTag(ActionEvent actionEvent) {
        if(TagView.getSelectionModel().isEmpty()){
            return;
        }
        savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().get(pIndex).
                getTags().remove(TagView.getSelectionModel().getSelectedIndex());

        setTags();
    }

    public void setCaption(ActionEvent actionEvent) {
        savedUsers.get(userIndex).getAlbumList().get(albumIndex).
                getPhotoList().get(pIndex).editCaption(txtCaption.getText());

        lblCaption.setText("Caption: "
                +savedUsers.get(userIndex).getAlbumList().get(albumIndex).getPhotoList().get(pIndex).getCaption());
    }

    public void setUsername(String username){
        this.userName = username;

        for(User u : savedUsers){
            if(u.getName().equals(username))
                userIndex = savedUsers.indexOf(u);
        }
    }
    public void setAlbumIndex(int a){
        albumIndex = a;
    }
    public void setPhotoIndex(int p){
        this.pIndex = p;
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

    public void save(ActionEvent actionEvent) {
        Save(savedUsers);
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
}
